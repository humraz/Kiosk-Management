package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class reportloss extends AppCompatActivity {
EditText ed;
EditText ed2;
    String loss;
    String stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportloss);
        ed= (EditText) findViewById(R.id.editText);
        ed2=(EditText) findViewById(R.id.com);
        Firebase.setAndroidContext(this);
        read();
    }
    public void losslog(View view)
    {
        Intent in = new Intent(this, losslog.class);
        startActivity(in);
    }

    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                    loss = user1.getLoss();
                    stock = user1.getStock();




                    // tost(sum);


                }
                TextView tv= (TextView) findViewById(R.id.textView33);
                TextView tv2= (TextView) findViewById(R.id.textView);
                tv2.setText("Available : " +stock);
                tv.setText("LOSS : " + loss);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public  void reploss(View view)
    {
        String l= ed.getText().toString();
        String c=ed2.getText().toString();
        if(l.equals("")|| c.equals(""))
        {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Blank Fields Permitted!")

                    .show();
        }
        else {
            int n = Integer.parseInt(l);
            int cn = Integer.parseInt(loss);
            int totloss = n + cn;
            int st = Integer.parseInt(stock);
            int totstock = st - n;
            final String nloss = Integer.toString(totloss);
            final String tst = Integer.toString(totstock);
            SimpleDateFormat t = new SimpleDateFormat("hh:mm:ss");

            String time = t.format(new Date());
            SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

            String date = mon.format(new Date());
            final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
            //Value event listener for realtime data update
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        wareclass user1 = userSnapshot.getValue(wareclass.class);


                        userSnapshot.getRef().child("loss").setValue(nloss);
                        userSnapshot.getRef().child("stock").setValue(tst);


                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });


            Firebase ref2 = new Firebase("https://kioskfarm.firebaseio.com/loss-history/");

            //Getting values to store

            //Creating Person object
            wareclass person = new wareclass();

            //Adding values

            person.setLoss(l);
            person.setComment(c);
            person.setDate(date);
            person.setTime(time);
            //Storing values to firebase
            ref2.push().setValue(person);

            Toast.makeText(this, "Loss modified with date " + date + "and time " + time, Toast.LENGTH_LONG).show();
            read();
            ed.setText("");
        }
    }
}
