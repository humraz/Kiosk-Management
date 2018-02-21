package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.deposits;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.kiosksalesdaily;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class tablekioskwise extends AppCompatActivity {
   // private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
        //    { "and", "a", "second", "test" } };
   private static final String[][] DATA_TO_SHOW = new String[11][11];
    String kid;
    int sum = 0;
    int cash=0;

    String time;
    int t;

    TextView tv7;
    TextView tv77;

    TextView tv6;
    TextView tv66;
    int no=1;
    TextView tv3;
    TextView tv5;
    TextView tv4;
    TextView tv45;
    //tost(sum, c, cash, card, paytm, other);
    String opbal;
    int op=0;
    TextView tv2;
    int deposit=0;
    int d=0;
    private static final String[] TABLE_HEADERS = { "Date", "Cash", "Card", "Pay-Tm","Other","Deposits", "Total sales" };

    int deposit2=0;
    String  url;
    String  url2;
    TableView<String[]> tableView;
    int l=0;
    String datt3;
    int y=0;
    int depooo=0;
    String yopbal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablekioskwise);
        SharedPreferences pref3 =null;

        pref3 = getSharedPreferences("kioskname", MODE_PRIVATE);
         yopbal=pref3.getString("yopbal",null);
        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        kid= getIntent().getStringExtra("kid");
        no= getIntent().getIntExtra("no",1);
     //   Toast.makeText(this, kid ,Toast.LENGTH_LONG).show();
        SimpleDateFormat mon = new SimpleDateFormat("dd MMM");
        String date = mon.format(new Date());

        SimpleDateFormat monb = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        String month= monb.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());

        url="https://kioskfarm.firebaseio.com/SALES/" + kid+"/"+yea+"/"+month+"/"+da;

        int dd=0;
        dd= Integer.parseInt(da);

        for (l=0;l<8;l++)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -l);
            String datt= dateFormat.format(cal.getTime());

            DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal3 = Calendar.getInstance();
            cal3.add(Calendar.DATE, -l);
            datt3= dateFormat3.format(cal3.getTime());


            DateFormat dateFormat4 = new SimpleDateFormat("ddMMyyyy");
            Calendar cal4 = Calendar.getInstance();
            cal3.add(Calendar.DATE, -l);
            String datt4= dateFormat4.format(cal4.getTime());

            DateFormat dateFormat2 = new SimpleDateFormat("dd MMM");
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, -l);
            String datt2= dateFormat2.format(cal2.getTime());
            DATA_TO_SHOW[l][0]=datt2;
            System.out.println("datesrhere"+datt);


            url="https://kioskfarm.firebaseio.com/SALES/" + kid+"/"+datt;
            url2="https://kioskfarm.firebaseio.com/opbal&stock/"+kid+"/"+datt4;

            read(url);
       //   r(datt3);
       //    read2(url2);

        }
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        //tv.setText(date + "  " +dayOfTheWeek);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                tableView.setDataAdapter(new SimpleTableDataAdapter(tablekioskwise.this, DATA_TO_SHOW));

                // Toast.makeText(billing.this, "S" , Toast.LENGTH_LONG).show();


            }
        }, 3000);

    }
    public void r(String datt)
    {

        SimpleDateFormat mon = new SimpleDateFormat(datt);
deposit2=0;
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

                        amstr = sale.getAmount();
                        amount = Integer.parseInt(amstr);
                        deposit2=deposit2+amount;
                    }
                    //  tost(sum,c,cash,card,paytm);
                }

                if (deposit2!=0)
                DATA_TO_SHOW[depooo][5]=Integer.toString(deposit2);
else
                    DATA_TO_SHOW[depooo][5]="0";

                depooo++;
                deposit2=0;

           // read2(url2);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    int opball=0;
    int opstock=0;
    int yopball=0;
    int ystock=0;
    public void read2(String url3) {
        final Firebase ref = new Firebase(url3);
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);


                        opball= Integer.parseInt(sale.getOpeningbal());
                         opstock =Integer.parseInt(sale.getStock());
                         yopball=Integer.parseInt(sale.getYestopeningbal());
                        ystock =Integer.parseInt(sale.getYeststock());


                }

                if ((opball==0&&opstock==0&&yopball==0&&ystock==0))
                {
                    //tost(0, 0, 0, 0, 0, 0);
                    // Toast.makeText(kwise.this,"No Sales On This Day" ,Toast.LENGTH_LONG).show();
                    DATA_TO_SHOW[y][7] = "0";
                    DATA_TO_SHOW[y][8] ="0";
                    DATA_TO_SHOW[y][9] = "0";
                    DATA_TO_SHOW[y][10] = "0";

                }

                else {
                    DATA_TO_SHOW[y][7] = Integer.toString(opball);
                    DATA_TO_SHOW[y][8] = Integer.toString(opstock);
                    DATA_TO_SHOW[y][9] = Integer.toString(yopball);
                    DATA_TO_SHOW[y][10] = Integer.toString(ystock);
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    int amount;
    int c=0;
    int paytm = 0;
    int other=0;
    int card=0;
    int p=0;
    public void read( String url)
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
                            p=0;
                            p = Integer.parseInt(sale.getAmount());
                            System.out.println("pppppp"+sale.getAmount());
                            paytm += p;
                            break;
                        case "cash":
                            p=0;
                            p = Integer.parseInt(sale.getAmount());
                            cash += p;
                            break;
                        case "card":
                            p=0;
                            p = Integer.parseInt(sale.getAmount());
                            card += p;
                            break;
                        case "other":
                            p=0;
                            p=Integer.parseInt(sale.getAmount());
                            other+=p;





                    }


                }
                // tost(sum, c, cash, card, paytm, other);

                if ((paytm==0&&cash==0&&card==0&&other==0))
                {
                    //tost(0, 0, 0, 0, 0, 0);
                   // Toast.makeText(kwise.this,"No Sales On This Day" ,Toast.LENGTH_LONG).show();
                    DATA_TO_SHOW[y][1] = "0";
                    DATA_TO_SHOW[y][2] ="0";
                    DATA_TO_SHOW[y][3] = "0";
                    DATA_TO_SHOW[y][4] = "0";
                    DATA_TO_SHOW[y][6] = "0";

                }

                else {
                    DATA_TO_SHOW[y][1] = Integer.toString(cash*no);
                    DATA_TO_SHOW[y][2] = Integer.toString(card*no);
                    DATA_TO_SHOW[y][3] = Integer.toString(paytm*no);
                    DATA_TO_SHOW[y][4] = Integer.toString(other*no);
                    DATA_TO_SHOW[y][6] = Integer.toString(sum*no);
                }
               // DATA_TO_SHOW[y][6]=Integer.toString(other);
                y++;
                cash=0;
                c=0;
                paytm = 0;
                other=0;
                card=0;
                sum=0;
                r(datt3);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
