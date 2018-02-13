package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class resettwo extends AppCompatActivity {
String kid;
    String stock;
    String op ;
    String d;
    int st=0;
    int o=0;
    int diff=0;
    String select="Selected ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resettwo);
        kid= getIntent().getStringExtra("kid");
        Firebase.setAndroidContext(this);
        final TextView tv4= (TextView)findViewById(R.id.textView4);
tv4.setText("Kiosk: " +kid);
        read();
    }
    public void read()
    {final TextView tv= (TextView)findViewById(R.id.textView);
        final TextView tv2= (TextView)findViewById(R.id.textView2);
        final TextView tv3= (TextView)findViewById(R.id.textView3);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake ord = userSnapshot.getValue(kioskmake.class);
                    String pending = ord.getPass();
                    if (pending.equals(kid))
                    {stock=ord.getStock().toString();
                         op =ord.getOpeningbal().toString();
                        d= ord.getDiff().toString();
                        tv.setText("Current Stock(Units): "+stock);
                        tv2.setText("Curren Cash In Hand(Rs): "+op);
                        tv3.setText("Current Cash Loss(Rs): " +d);
                    }

                }


            }




            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void st(View view)
    {   select= select + " Stock";
        st=1;
        Toast.makeText(this, select,Toast.LENGTH_LONG).show();
    }
    public void logs(View view)
    {
        Intent in = new Intent(this, resetlogs.class);
        startActivity(in);
    }
    public void loss(View view)
    {select= select + " Cash Loss";
        diff=1;
        Toast.makeText(this, select,Toast.LENGTH_LONG).show();
    }
    public void cash(View view)
    {select= select + " Cash In Hand";
        o=1;
        Toast.makeText(this, select,Toast.LENGTH_LONG).show();
    }

    public void con(View view)
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake ord = userSnapshot.getValue(kioskmake.class);
                    String pending = ord.getPass();
                    if (pending.equals(kid))
                    {
                        if (st==1)
                        {
                            userSnapshot.getRef().child("stock").setValue("0");
                        }
                        if (diff==1)
                        {
                            userSnapshot.getRef().child("diff").setValue("0");
                        }
                        if (o==1)
                        {
                            userSnapshot.getRef().child("openingbal").setValue("0");
                        }

                    }

                }
                write();


            }




            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });



    }
    public void write()
    {
        String text="Kiosk: " +kid;

        if (st==1)
        {
         text= text +  "\nStock : " +stock;
        }
        if (diff==1)
        {
            text= text +  "\nCash Loss: " +d;

        }
        if (o==1)
        {
            text= text +  "\nCash In Hand : " +op;

        }


        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/resets/");
        reset person = new reset();
        person.setText(text);
        SimpleDateFormat t = new SimpleDateFormat("hh:mm");

        String time = t.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("EEEE MMM yyyy");

        String date = mon.format(new Date());
        person.setDate(date);
        person.setTime(time);


        ref.push().setValue(person);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Reset Confirmed")
                .setContentText("Added To Records")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent in = new Intent(resettwo.this, homepage.class);
                        startActivity(in);

                    }
                })
                .show();


    }


}
