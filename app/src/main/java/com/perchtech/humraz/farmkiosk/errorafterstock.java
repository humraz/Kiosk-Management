package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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


public class errorafterstock extends ActionBarActivity {
    String kioskid="yolo";
    String tyme;
    String date="";
    int sum = 0;
    int dt=0;
    String url="";
    EditText ed;
    String ti;
    int t;
    EditText ed2;
    int op=0;
    int deposit=0;
    String d2;
    int deposit2=0;
    String yopbal;
    ActionProcessButton btnSignIn;
    String rate;
    String stockkk;

    int n2;
    RotateLoading rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errorafterstock);
      //  rl = (RotateLoading) findViewById(R.id.rotateloading);

       // rl.start();

        SharedPreferences prefs3= getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname",null);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
        tyme= time.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        rate= prefs3.getString("rate",null);
        yopbal= prefs3.getString("yopbal",null);
        n2=Integer.parseInt(rate);
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Intent in = getIntent();
        stockkk=in.getStringExtra("st");
        String opbal=prefs3.getString("openbal",null);
        op =Integer.parseInt(opbal);
        //      out=prefs3.getString("outt",null);
//        o=Integer.parseInt(out);
        ti=prefs3.getString("time",null);
        ti= ti.replace(":", "");
        t=Integer.parseInt(ti);
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        date=da+"-"+month+"-"+yea;
        d2=da+"/"+month+"/"+yea;
        Firebase.setAndroidContext(this);
       btnSignIn = (ActionProcessButton) findViewById(R.id.SIG);
        btnSignIn.setEnabled(false);
        url="https://kioskfarm.firebaseio.com/SALES/" + kioskid+"/"+yea+"/"+month+"/"+da;
        r();

    }



    public void move2() {
        Intent in = new Intent(this, Successafterstock.class);
        SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
        final String mond = mon.format(new Date());
        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/opbal&stock/"+kioskid+"/"+mond);
        final kiosksalesdaily order = new kiosksalesdaily();



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake k = userSnapshot.getValue(kioskmake.class);
                    String date = k.getIndate();
                    if (date.equals(d2)) {
                        userSnapshot.getRef().child("openingbal").setValue(Integer.toString((cash*n2)+Integer.parseInt(yopbal)));
                        userSnapshot.getRef().child("stock").setValue(stockkk);                      //  f = 0;
                    }
                }
                btnSignIn.setEnabled(true);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        //   in.putExtra("diff", Integer.toString(diff));
        //   startActivity(in);
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
                read();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void YO(View view)
    {
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }
    int cash=0;
    int amount;
    int c=0;
    int paytm = 0;
    int other=0;
    int card=0;
    int p=0;
    public void read()
    {

        final Firebase ref = new Firebase(url);
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr="";


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);


                    amstr=sale.getAmount();
                    amount=Integer.parseInt(amstr);
                    String tt= sale.getTime();
                    tt=tt.replace(":", "");
                    int ts= Integer.parseInt(tt);

                        sum = sum + amount;
                        c++;

                        switch (sale.getPaymentmode()) {
                            case "paytm":
                                p = Integer.parseInt(sale.getAmount());
                                paytm += p;
                                break;
                            case "cash":
                                p = Integer.parseInt(sale.getAmount());
                                cash += p;
                                break;
                            case "card":
                                p = Integer.parseInt(sale.getAmount());
                                card += p;
                                break;
                            case "other":
                                p=Integer.parseInt(sale.getAmount());
                                other+=p;


                        }
                        tost(sum, c, cash, card, paytm, other);




                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    int n=1;
    int f=1;
    public void no(View view)
    {


        Button bt = (Button)findViewById(R.id.button7);

        if (f==1)
        {
            bt.setText("Now Showing in Rupees");
            n=n2;
            f=0;
        }
        else
        {
            bt.setText("Now Showing in Numbers");
            n=1;
            f=1;
        }

        tost(sum, c, cash, card, paytm, other);
    }

    public void tost(int sum, int cc,int ca, int car, int pay, int other)
    {
       // rl.stop();
       String  y=" No.";

        // setContentView(R.layout.activity_errorafterstock);
        if (n==1)
            y=" No.";
        else
            y=" Rs.";

        TextView tv12=(TextView) findViewById(R.id.tv12);
        TextView tv122=(TextView) findViewById(R.id.tv122);
        TextView tv1=(TextView) findViewById(R.id.tv1);
        TextView tv11=(TextView) findViewById(R.id.tv11);
     //   TextView tv2=(TextView) findViewById(R.id.tv2);
        TextView tv3=(TextView) findViewById(R.id.tv3);
        TextView tv4=(TextView) findViewById(R.id.tv4);
        TextView tv555=(TextView) findViewById(R.id.tv555);
        TextView tv5=(TextView) findViewById(R.id.tv5);
        TextView tv55=(TextView) findViewById(R.id.tv55);
        String a= String.format(Integer.toString(Integer.parseInt(yopbal)));
        //a.replace(" ", "&nbsp");
        tv122.setText((a));
        String b= String.format(Integer.toString(deposit));
        //b.replace(" ", "&nbsp");
        tv12.setText((b));
      //  String c =String.format("%-38s %-5s","Total Coconuts Sold (No. of Nuts):", Integer.toString(sum));
       // c.replace(" ", "&nbsp");
        //tv1.setText(c);
        String d= String.format( Integer.toString(sum *n));
        //d.replace(" ", "&nbsp");
        tv11.setText((d));

       // tv2.setText(String.format("%-38s %-5s","No of Transactions: ",Integer.toString(cc)));
        tv3.setText(String.format( Integer.toString(ca*n)));
        tv4.setText(String.format(Integer.toString(car*n)));
        tv5.setText(String.format( Integer.toString(pay*n)));
        tv555.setText(String.format( Integer.toString(other*n)));
        tv55.setText(String.format(Integer.toString((ca*n2)-deposit+Integer.parseInt(yopbal))));
        move2();

    }

}
