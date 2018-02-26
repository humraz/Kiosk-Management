package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Successafterstock extends ActionBarActivity {
    String us2;
    RotateLoading rl;
    int sum = 0;
    int c = 0;
    float av = 0;
    String state;
    int cashsum=0;
int amou;
    int open=0;
    String openball;
int t=0;
 //   String diff;
    int d1=0;
    int d2=0;
    int d=0;
    String color;
    int n2=0;
    int cl2;
    int yopen;
    String rate;
int deposit=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successafterstock);
        Firebase.setAndroidContext(this);
        rl = (RotateLoading) findViewById(R.id.rotateloading);
       // amou= getIntent().getStringExtra("amount");
        Intent in = getIntent();
        cl2= in.getIntExtra("diff",0);
        d1=0;




        // amount = Integer.parseInt(amou);
        rl.start();
        SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        us2 = prefs3.getString("kname", null);
        String ti=prefs3.getString("time",null);
        rate= prefs3.getString("rate",null);
        n2=Integer.parseInt(rate);
     //   String to= prefs3.getString("dif",null);
      //  d2=Integer.parseInt(to);
        ti= ti.replace(":", "");
        t=Integer.parseInt(ti);
        openball=prefs3.getString("openbal",null);
        yopen= Integer.parseInt(prefs3.getString("yopbal",null));
        open=Integer.parseInt(openball);


        d=d1+d2;
      //  Toast.makeText(this, d,Toast.LENGTH_LONG).show();

      //  setsales();
modify("green");

    }

  /*  public void setsales() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + us2);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                    String amount = sale.getAmount();
                    // hi(amount);
                   amou = Integer.parseInt(amount);

                        sum = sum + amou;
                        c++;


                }
                av = sum / c;

                int r = (int) (0.2 * av);
                int o = (int) (0.8 * av);
                int w = (int) av;
                int g = (int) (1.2 * av);
                if (amou < r)
                    state = "red";
                else if (amou < o)
                    state = "orange";
                else if (amou < w)
                    state="white";
                else if(amou<g)
                    state= "green";
                else
                    state ="darkgreen";
                modify(state);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
*/
  /*  public void read() {
        sum=0;
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mon.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
       // date = da + "-" + month + "-" + yea;
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/SALES/" + us2 + "/" + yea + "/" + month + "/" + da);
        //Value event listener for realtime data update
        final StringBuilder finalTotal = new StringBuilder();
        final StringBuilder finalTotal2 = new StringBuilder();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr = "";
                int amount;

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);

                    finalTotal.append(sale.getAmount());
                    amstr = sale.getAmount();
                    amount = Integer.parseInt(amstr);
                    String tt= sale.getTime();
                    tt=tt.replace(":", "");
                    int ts= Integer.parseInt(tt);
                    if (ts>t) {
                        if(sale.getPaymentmode().toString().equals("cash")) {
                            cashsum=cashsum+amount;
                        }
                        sum = sum + amount;
                    }
                    // tost(sum);


                }
                modify(state);

                //btnSignIn.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }*/
    String stockkk;
    public void modify(final String state) {
        SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
        final String time = t.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
        final String date = mon.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String passs = user1.getPass();
                    if (passs.equals(us2)) {
                        stockkk=user1.getStock();
                        userSnapshot.getRef().child("loggedin").setValue("false");
                        userSnapshot.getRef().child("outtime").setValue(time);
                        userSnapshot.getRef().child("indate").setValue(date);
                        userSnapshot.getRef().child("salestatus").setValue(state);

                        userSnapshot.getRef().child("openingbal").setValue(Integer.toString(cl2));




                    }
                }
                rl.stop();
              move();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void move() {
        System.out.println("yoloy"+ stockkk);
        Intent in = new Intent(this, errorafterstock.class);
        in.putExtra("st",stockkk);
        startActivity(in);
    }


}
