package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.dd.processbutton.ProcessButton;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.deposits;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.kiosksalesdaily;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class kwise extends ActionBarActivity implements DatePickerDialogFragment.DatePickerDialogHandler{
TextView tv;
    String kid;
    int sum = 0;
    int cash=0;

    String time;
    int t;

    TextView tv7;
    TextView tv77;

    TextView tv6;
    TextView tv66;
    TextView tv3;
    TextView tv5;
    TextView tv4;
    int dam=0;
    TextView tv45;
String rate;
    String opbal;
    int op=0;
    TextView tv2;
    int deposit=0;
    int d=0;
    int deposit2=0;
    String  url;
    SharedPreferences pref3 =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kwiseoriginal);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        kid= getIntent().getStringExtra("kid");
        //Toast.makeText(this, kid ,Toast.LENGTH_LONG).show();
        SimpleDateFormat mon = new SimpleDateFormat("dd MMM");
        String date = mon.format(new Date());

        SimpleDateFormat monb = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        String month= monb.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        tv = (TextView) findViewById(R.id.textView);
        url="https://kioskfarm.firebaseio.com/SALES/" + kid+"/"+yea+"/"+month+"/"+da;

        pref3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        tv2 = (TextView) findViewById(R.id.textView22);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        tv7 = (TextView) findViewById(R.id.textView7);
        tv77 = (TextView) findViewById(R.id.textView77);
        tv45 = (TextView) findViewById(R.id.textView45);

        tv66 = (TextView) findViewById(R.id.textView66);
        tv.setText(date + "  " +dayOfTheWeek);
        checkopenks();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

              //  r();
                // Toast.makeText(billing.this, "S" , Toast.LENGTH_LONG).show();


            }
        }, 1000);

    }
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        // mResultTextView.setText(getString(R.string.date_picker_result_value, year, monthOfYear, dayOfMonth));
        monthOfYear++;
        String d= Integer.toString(dayOfMonth);
        String mon= Integer.toString(monthOfYear);
        String yea= Integer.toString(year);
        String date;
        date= d+"/"+mon+"/"+yea;
        if(d.length()==1)
        {
            d='0'+d;
        }
        url="https://kioskfarm.firebaseio.com/SALES/" + kid+"/"+yea+"/"+mon+"/"+d;
        Date strDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (new Date().before(strDate))
        {
            Toast.makeText(this,"You Have Selected An Invalid Date, Select A Date Before The Current Date!" ,Toast.LENGTH_LONG).show();
          //  ProcessButton pd = (ProcessButton)findViewById(R.id.confirmorder);
           // pd.setEnabled(false);
        }

        else
        {
            Toast.makeText(this,"Date Selected :"+d+"-"+mon+"-"+yea ,Toast.LENGTH_LONG).show();


            //String dayOfTheWeek = sdf.format(date);
            tv.setText(date);
           // ProcessButton pd = (ProcessButton)findViewById(R.id.confirmorder);
           // pd.setEnabled(true);
            read();
        }




    }
    public void clic(View view) {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment);
        dpb.show();

    }
    public void r()
    {
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
        t=Integer.parseInt(time);
        final String date = mon.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/deposits/"+kid);
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr="";
                int amount;


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    deposits sale = userSnapshot.getValue(deposits.class);
                    String d= sale.getDate();
                    String kis= sale.getKid();
                    if (d.equals(date) && kis.equals(kid)) {
                        String tt= sale.getTime();
                        tt=tt.replace(":", "");
                        int ts= Integer.parseInt(tt);
                        amstr = sale.getAmount();
                        amount = Integer.parseInt(amstr);
                        deposit2=deposit2+amount;
                        if (ts>t) {


                            deposit = deposit + amount;
                        }

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

  /*  public void read() {
        SimpleDateFormat mont = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mont.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());

        String  y=" No.";

        // setContentView(R.layout.activity_errorafterstock);
        if (n==1)
            y=" No. ";
        else
            y=" Rs. ";

            String url = "https://kioskfarm.firebaseio.com/SALES/" + kid + "/" + yea + "/" + month + "/" + da;
            final Firebase ref = new Firebase(url);
            //Value event listener for realtime data update
            final StringBuilder finalTotal = new StringBuilder();
            final StringBuilder finalTotal2 = new StringBuilder();

        final String finalY = y;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    String amstr = "";
                    int amount;

                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);

                        finalTotal.append(sale.getAmount());
                        amstr = sale.getAmount();
                        String type = sale.getPaymentmode();
                        amount = Integer.parseInt(amstr);
                        String tt= sale.getTime();
                        tt=tt.replace(":", "");
                        int ts= Integer.parseInt(tt);

                            if (type.equals("cash")) {
                                cash = cash + amount;
                            } else if (type.equals("card")) {
                                card += amount;
                            }
                            else if (type.equals("other")) {
                                other += amount;
                            }
                            else
                                paytm += amount;
                        if (ts>t) {
                            sum = sum + amount;
                        }
                       // tv0.setText("  Op Bal(Rs) :  " + op);
                        //  System.out.println("aaaaaaaaaaaaaaaaa" + sum);
                        tv3.setText("  Total Sales:  "+ finalY + (card+cash+paytm+other)*n);
                        tv4.setText("  Cash:"+ finalY + cash*n);
                        tv5.setText("  Card:" + finalY+ card*n);
                        tv6.setText("  Paytm:" + finalY+ paytm*n);
                        tv77.setText("  Other:" + finalY+ other*n);

                        tv7.setText("Cash In Hand(Rs) : " + ((sum*30) -deposit+op));
                        tv66.setText("Deposits(Rs) : "+ deposit2);

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }*/
  int amount;
    int c=0;
    int paytm = 0;
    int other=0;
    int card=0;
    int p=0;
  public void read()
  {cash=0;
      c=0;
  paytm = 0;
    other=0;
      card=0;
      sum=0;
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


              }
              if ((paytm==0&&cash==0&&card==0&&other==0))
              {
                  tost(0, 0, 0, 0, 0, 0);
                  Toast.makeText(kwise.this,"No Sales On This Day" ,Toast.LENGTH_LONG).show();

              }
              else
              tost(sum, c, cash, card, paytm, other);
              //btnSignIn.setEnabled(true);
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
              System.out.println("The read failed: " + firebaseError.getMessage());
          }
      });

  }
    int n=1;
    int f=1;
    public void table(View view)
    {
        Intent in = new Intent(this , tablekioskwise.class);
        in.putExtra("kid",kid);
        in.putExtra("no",n);
        startActivity(in);
    }
    public void table2(View view)
    {
        Intent in = new Intent(this , tablewise2.class);
        in.putExtra("kid",kid);
        in.putExtra("no",n);
        startActivity(in);
    }
    int n2;
    public void no(View view)
    {

        n2= Integer.parseInt(rate);
        Button bt = (Button)findViewById(R.id.button7);

        if (f==1)
        {
            n=n2;
            f=0;
            bt.setText("Now Showing in Rupees Rate= " +n);

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
        n2= Integer.parseInt(rate);
        // setContentView(R.layout.activity_errorafterstock);
        if (n==1)
            y=" No.";
        else
            y=" Rs.";

        TextView tv12=(TextView) findViewById(R.id.tv12);
        TextView tv122=(TextView) findViewById(R.id.textView45);
        TextView tv1=(TextView) findViewById(R.id.tv1);
        TextView tv11=(TextView) findViewById(R.id.tv11);
        TextView tv2=(TextView) findViewById(R.id.tv2);
        TextView tv7=(TextView) findViewById(R.id.tv7);
        TextView tv111=(TextView) findViewById(R.id.textView450);
        TextView tv3=(TextView) findViewById(R.id.tv3);
        TextView tv4=(TextView) findViewById(R.id.tv4);
        TextView tv555=(TextView) findViewById(R.id.tv555);
        TextView tv5=(TextView) findViewById(R.id.tv5);
        TextView tv55=(TextView) findViewById(R.id.textView777);
        TextView tv88=(TextView) findViewById(R.id.tv88);
        TextView tv00=(TextView) findViewById(R.id.tv00);

        String a= String.format("%-38s %-5s","Closing Balance(Rs): ",Integer.toString((ca*n2)-deposit2+Integer.parseInt(yopbal)));
        String av= String.format("%-38s %-5s","Opening Balance(Rs): ",yopbal);
        //a.replace(" ", "&nbsp");
        //a.replace(" ", "&nbsp");
        tv122.setText((a));
        String b= String.format("%-38s %-5s","Cash Deposit(Rs): ", Integer.toString(deposit2));
        //b.replace(" ", "&nbsp");
        tv12.setText((b));
        //  String c =String.format("%-38s %-5s","Total Coconuts Sold (No. of Nuts):", Integer.toString(sum));
        // c.replace(" ", "&nbsp");
        //tv1.setText(c);
        String d= String.format("%-38s %-5s","Total Sales: "+y, Integer.toString(sum *n));
        //d.replace(" ", "&nbsp");
        tv11.setText((d));
        tv111.setText((av));

      //  tv2.setText(String.format("%-38s %-5s","Opening Stock(Units):", ystock));
        tv3.setText(String.format("%-38s %-5s","Cash Sales: "+y, Integer.toString(ca*n)));
        tv4.setText(String.format("%-38s %-5s","Card Sales: "+y, Integer.toString(car*n)));
        tv5.setText(String.format("%-38s %-5s","Pay-Tm Sales: "+y, Integer.toString(pay*n)));
        tv555.setText(String.format("%-38s %-5s","Other/NIYO: "+y, Integer.toString(other*n)));
        tv2.setText(String.format("%-38s %-5s","Opening Stock(Units): ", ystock));
        int ss=Integer.parseInt(stock);
        ss=ss-dam+addedstock;
        tv88.setText(String.format("%-38s %-5s","Damages(Units): ", dam));
      tv7.setText(String.format("%-38s %-5s","Closing Stock(Units): ", ss));
        tv00.setText(String.format("%-38s %-5s","Added Stock(Units): ", Integer.toString(addedstock)));
       // Toast.makeText(this, stock + ystock ,Toast.LENGTH_LONG).show();

    }
String  ystock;
    String stock;
    int addedstock;
public void b(View view)
{
    Intent in = new Intent(this, cashflow.class);
    startActivity(in);
}
    String yopbal;
    public void checkopenks() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        final int[] count = {0};
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);
                    String k= sale.getPass();
                    if(k.equals(kid)){
                    String amount = sale.getLoggedin();
                       opbal=sale.getOpeningbal();
                        ystock=sale.getYeststock();
                         yopbal=sale.getYestopeningbal();
                        stock= sale.getStock();
                        pref3.edit().putString("yopbal",yopbal).commit();
                        op=Integer.parseInt(opbal);
                        dam =Integer.parseInt(sale.getE());
                        time=sale.getOuttime();
                        time=time.replace(":","");
                        rate=sale.getRate();
                        addedstock=Integer.parseInt(sale.getAddstock());
                    // hi(amount);
                    if (amount.equals("true"))
                    {
                        tv2.setText(" Kiosk Currently Open.");
                        //tv45.setText("Opening Bal:(Rs) "+opbal);

                    }
                        else
                        {
                            tv2.setText("Kiosk is Currently Closed. ");
                            //tv45.setText("Opening Bal:(Rs) "+opbal);


                        }
                    }
                }
                r();

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}