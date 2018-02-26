package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Stocktakingfirst extends ActionBarActivity {
    String url;
    String kioskid = "yolo";
    String tyme;
    String ti;
    String out;
    int o;
    int dt=0;
    int t;
    String date = "";
    int sum = 0;
    int deposit=0;
    int d2=0;
    int closing=0;
    String opbal;
    int cashsum=0;
    int op=0;
    TextView tv33;
    int yopbal;
    int cl=0;
    int cl2;
  //  int diff=0;
    RotateLoading rl;
    int n2;
    String rate;
    ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktakingfirst);
        Firebase.setAndroidContext(this);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
        SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname", null);
        opbal=prefs3.getString("yopbal",null);
        yopbal=Integer.parseInt(opbal);

        ti=prefs3.getString("time",null);
  //      out=prefs3.getString("outt",null);
        rate= prefs3.getString("rate",null);
        n2=Integer.parseInt(rate);
//        o=Integer.parseInt(out);
        Firebase.setAndroidContext(this);
       // rl = (RotateLoading) findViewById(R.id.rotateloading);
//rl.start();
        ti= ti.replace(":", "");
        TextView tv = (TextView)findViewById(R.id.textView3);
        t=Integer.parseInt(ti);
     //   System.out.println("iiiiiiiiiiiiiiii" +t);
        op=Integer.parseInt(opbal);
        tv33= (TextView) findViewById(R.id.textView33);
     //   tv.setText("Opening Balance was : "+ opbal);
        tyme = time.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mon.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
        date = da + "-" + month + "-" + yea;

     //   btnSignIn = (ActionProcessButton) findViewById(R.id.sign);
       // btnSignIn.setEnabled(false);
        url = "https://kioskfarm.firebaseio.com/SALES/" + kioskid + "/" + yea + "/" + month + "/" + da;
        r();




    }
   public void r()
    {
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

        final String date = mon.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {



                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);
                    String k = sale.getPass();
                    if (k.equals(kioskid))
                    {
                        deposit= Integer.parseInt(sale.getDiff());
                    }
                  //  tost(sum,c,cash,card,paytm);
                }
               // calc();
                read();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void check(View view) {
        cl= (sum*n2) +op -deposit;
        cl2=(cashsum*n2)+op;

        //System.out.println("hiiiiiiiiiiiiiii"+cl + " " + sum + " " + op + " " +deposit);
       // EditText ed = (EditText) findViewById(R.id.closingbalance);
        //String am2 = ed.getText().toString();
       /* if (am2.equals("")) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("You Did Not Enter The Amount")

                    .show();
        } else {
            int am3 = Integer.parseInt(am2);
            if (am3 == cl2) {
                diff=0;
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Successfully Verified Amount")
                        .setContentText("Amount Verified With Server")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                move();

                            }
                        })
                        .show();
            } else {
                diff =cl2-am3;
                Toast.makeText(this, Integer.toString(diff),Toast.LENGTH_LONG).show();
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("The Amount You Entered Does Not Match.")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                move2();

                            }
                        })
                        .show();

            }
        }*/
        move();
    }

    public void calc() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);
        //Value event listener for realtime data update
        SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
        final String mond = mon.format(new Date());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                    String amount = sale.getAmount();
                    // hi(amount);

                    if (sale.getTime().equals(mond)) {
                        closing = Integer.parseInt(amount);
                    }





                    //  series.addPoint(new ValueLinePoint(day, amou));


                }
                // mCubicValueLineChart.addSeries(series);
                read();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void move() {
        Intent in = new Intent(this, errorafterstock.class);
        final kiosksalesdaily order = new kiosksalesdaily();
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);
        SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
        final String mond = mon.format(new Date());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily k = userSnapshot.getValue(kiosksalesdaily.class);
                    String date = k.getTime();
                    if (date.equals(mond)) {
                        userSnapshot.getRef().child("amount").setValue((Integer.toString(sum*n2)));
                        f = 0;
                    }
                }
                if (f == 1) {
                    order.setAmount(Integer.toString(sum*n2));
                    order.setTime(mond);

                    ref.push().setValue(order);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        in.putExtra("diff", cl2);
        startActivity(in);
    }

    int f = 1;

   /* public void move2() {
        Intent in = new Intent(this, Successafterstock.class);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);
        final kiosksalesdaily order = new kiosksalesdaily();

        SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
        final String mond = mon.format(new Date());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily k = userSnapshot.getValue(kiosksalesdaily.class);
                    String date = k.getTime();
                    if (date.equals(mond)) {
                        userSnapshot.getRef().child("amount").setValue(Integer.toString(sum*n2 + closing));
                        f = 0;
                    }
                }
                if (f == 1) {
                    order.setAmount(Integer.toString(sum*n2 + closing));
                    order.setTime(mond);

                    ref.push().setValue(order);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
     //   in.putExtra("diff", Integer.toString(diff));
        startActivity(in);
    }
*/
    public void read() {
        sum=0;
        cashsum=0;
        final Firebase ref = new Firebase(url);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr = "";
                int amount;

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);


                    amstr = sale.getAmount();
                    amount = Integer.parseInt(amstr);


                        sum = sum + amount;
                        if(sale.getPaymentmode().toString().equals("cash")) {
                            cashsum=cashsum+amount;
                        }}
                        // tost(sum);



                cl2=(cashsum*n2)+op-deposit;
               // tv33.setText("Closing Balance: " +cl2);
                //rl.stop();
                move();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


}
