package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.kiosksalesdaily;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class cashflow extends ActionBarActivity {
    TextView tv6;
    TextView tv66;

    TextView tv3;
    TextView tv5;
    TextView tv4;
    TextView tv2;
    TextView p;
    String time;
    String url;
    String kioskid;
    int sum = 0;
    int other=0;
    int cash=0;
    int paytm=0;
    int card=0;
    ArrayList<String> kiosks =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow);
        Firebase.setAndroidContext(this);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        SimpleDateFormat mon = new SimpleDateFormat("dd MM yyyy");
        String date = mon.format(new Date());
        TextView tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView22);
         tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        tv66 = (TextView) findViewById(R.id.textView66);

        // c = (TextView) findViewById(R.id.c);
       // ca = (TextView) findViewById(R.id.ca);
       // p = (TextView) findViewById(R.id.p);
        tv.setText(date + "   " +dayOfTheWeek);
        checkopenks();

        //date = da + "-" + month + "-" + yea;

        readkiosks();
    }

    public void readkiosks() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                    String k = user1.getPass().toString();
                    if (k.equals("Yu"))
                    {

                    }
                    else
                    {
                        kiosks.add(k);

                    }

                }
                read();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void read() {
        SimpleDateFormat mont = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mont.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
        for(int i=0;i<kiosks.size();i++) {

            System.out.println(kiosks.get(i));
            kioskid=kiosks.get(i);
            url = "https://kioskfarm.firebaseio.com/SALES/" + kioskid + "/" + yea + "/" + month + "/" + da;
            final Firebase ref = new Firebase(url);
            //Value event listener for realtime data update
            final StringBuilder finalTotal = new StringBuilder();
            final StringBuilder finalTotal2 = new StringBuilder();
            System.out.println("checking here" + sum);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    String amstr = "";
                    int amount=0;

                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);

                        finalTotal.append(sale.getAmount());
                        amstr = sale.getAmount();
                        String type= sale.getPaymentmode();
                        amount = Integer.parseInt(amstr);
                        if(type.equals("cash"))
                        {
                            cash=cash+amount;
                        }
                        else if(type.equals("card"))
                        {
                            card+=amount;
                        }
                        else if(type.equals("other"))
                        {
                            other+=amount;
                        }
                      else
                        paytm+=amount;
                        sum = sum + amount;
                      //  System.out.println("aaaaaaaaaaaaaaaaa" + sum);
                        tv3.setText("    Total Revenue(Rs):  "+sum*30+"   ");
                        tv4.setText("    Cash(No. of Nuts):  "+cash+"   ");
                        tv5.setText("    Card(No. of Nuts):  "+card+"   ");
                        tv6.setText("    Paytm(No. of Nuts):  "+paytm+"   ");
                        tv66.setText("    Other(No. of Nuts):  "+other+"   ");

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }

    }
public void see(View view)
{
    Intent in = new Intent(this, kiosklist.class);
    startActivity(in);
}
    public void rep(View view)
    {
        sum=0;
   cash=0;
      paytm=0;
      card=0;
     read();
    }

    public void checkopenks() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        final int[] count = {0};
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);
                    String amount = sale.getLoggedin();
                    // hi(amount);
                  if (amount.equals("true"))
                  {
                      count[0] +=1;
                  }
                }
                tv2.setText(count[0]+" Kiosks Currently Open.");
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


}
