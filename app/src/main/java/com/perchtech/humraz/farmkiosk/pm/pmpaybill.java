package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.bills;
import com.perchtech.humraz.farmkiosk.warehouse.paybill;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;
import com.perchtech.humraz.farmkiosk.warehouse.waremenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class pmpaybill extends AppCompatActivity {
    String supplier;
    String qnty;
    String stock;
    String paymentmethod;
    EditText ed;
    String unique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmpaybill);
        Intent in = getIntent();
        qnty= in.getStringExtra("qnty");
        stock = in.getStringExtra("stock");
        supplier = in.getStringExtra("supplier");
        TextView tv= (TextView) findViewById(R.id.textView1);
        TextView tv2 =(TextView) findViewById(R.id.textView2);
        tv.setText("Supplier id: " +supplier);
        tv2.setText("Quantity: " + qnty);
        unique = UUID.randomUUID().toString();


    }

    public void cash(View view)
    {
        ProcessButton pd= (ProcessButton) findViewById(R.id.reploss);
        pd.setVisibility(View.INVISIBLE);
        paymentmethod ="cash";
        Toast.makeText(this, "You Selected Cash",Toast.LENGTH_LONG).show();
    }

    public void confirm(View view)
    {
        if(paymentmethod.equals(""))
        {
            Toast.makeText(this, "Select A Payment Method" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            ed=(EditText)findViewById(R.id.editText3);
            String amount = ed.getText().toString();
            Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementpaststock");
            bills person = new bills();
            SimpleDateFormat t = new SimpleDateFormat("hh:mm:ss");
            String time = t.format(new Date());
            SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
            String date = mon.format(new Date());
            person.setDate(date);
            person.setTime(time);
            person.setPaymentmethod(paymentmethod);
            person.setQnty(qnty);
            person.setSupplier(supplier);
            person.setAmount(amount);
            person.setId(unique);
            ref.push().setValue(person);

            // Toast.makeText(this, "Stock Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText("Successfully Added Bill To Server")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent in = new Intent(pmpaybill.this, Main2Activity.class);
                            startActivity(in);

                        }
                    })
                    .show();


            modify(stock);
        }
    }


    public void modify(final String newstock) {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementstock/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                    userSnapshot.getRef().child("stock").setValue(newstock);

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void bank(View view)
    {
        ProcessButton pd= (ProcessButton) findViewById(R.id.replosss);
        pd.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "You Selected Bank",Toast.LENGTH_LONG).show();
        paymentmethod ="bank";
    }

}
