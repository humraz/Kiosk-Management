package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class deliveredorders extends AppCompatActivity {
    String id;
    String id2;
    String na;
    String kname;
    String amount;
    String newamount;
    String actam;
    String date;
    String date2;
    String date3;
    String driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveredorders);
        Intent in =getIntent();
        id= in.getStringExtra("id");

       // Toast.makeText(this, na , Toast.LENGTH_LONG).show();
        read();
    }


    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering user1 = userSnapshot.getValue(ordering.class);

                    String idd = user1.getId();
                    if(id.equals(idd))
                    {
                        id2=id;
                        kname = user1.getKioskname();
                        newamount = user1.getNewamount();
                        amount = user1.getAmount();
                        actam=user1.getAmountdelivered();
                        driver= user1.getDriver();
                        date=user1.getDateplaced();
                        date2=user1.getDeliverydategiven();
                        date3=user1.getDatedelivered();
                    }


                    // tost(sum);


                }
                TextView tv1 = (TextView)findViewById(R.id.textView1);
                TextView tv2 = (TextView)findViewById(R.id.textView2);
                TextView tv = (TextView)findViewById(R.id.textView3);
                TextView tv3 = (TextView)findViewById(R.id.textView33);
                TextView tv33 = (TextView)findViewById(R.id.textView333);
                TextView tv4 = (TextView)findViewById(R.id.textView44);
                TextView tv5 = (TextView)findViewById(R.id.textView5);
                TextView tv6 = (TextView)findViewById(R.id.textView6);
                TextView tv7 = (TextView)findViewById(R.id.textView7);

                tv.setText("Order ID: " +id2);
                tv1.setText("Kiosk Name:" + kname);
                tv2.setText("Amount "+kname+" Requested: " +amount);
             /*   if(Integer.parseInt(amount)!= Integer.parseInt(newamount))
                {
                    tv3.setTextColor(getResources().getColor(R.color.warning_stroke_color));
                }
                else
                    tv3.setTextColor(getResources().getColor(R.color.green_complete));*/

                tv3.setText("Amount Warehouse Sent: " +newamount);
               /* if(Integer.parseInt(newamount)!= Integer.parseInt(actam))
                {
                    tv33.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
                }
                else
                    tv33.setTextColor(getResources().getColor(R.color.green_complete));*/
                tv33.setText("Amount " +kname+" Received: " +actam);
                tv4.setText("Date order placed: " +date);
                tv5.setText("Delivery Date Given: " + date2);
                tv6.setText("Actual Delivery Date: " +date3);
                tv7.setText("Driver: "+ driver);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
