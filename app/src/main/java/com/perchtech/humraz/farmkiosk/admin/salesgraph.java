package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.kiosksalesdaily;

import org.w3c.dom.Text;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class salesgraph extends AppCompatActivity {
    ArrayList<String> kiosks = new ArrayList<>();
    ArrayList<String> kiosks2 = new ArrayList<>();
    ArrayList<String> amountt = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> done = new ArrayList<>();
    ArrayList<String> offline = new ArrayList<>();
    HashMap<String, String> h = new HashMap<String, String>();
    org.eazegraph.lib.charts.BarChart mBarChart;

    ArrayList<Card> cards ;
    int sum=0;

    int c=0;
    int p=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesgraph);
        mBarChart = (org.eazegraph.lib.charts.BarChart) findViewById(R.id.barchart);
        Firebase.setAndroidContext(this);
        readkiosks();

    }


    public void chart(final String date) {
System.out.println("chart");
        cards= new ArrayList<>();
        for (int i = 0; i < kiosks.size(); i++) {
            sum=0;
            c=0;
           // System.out.println("checkhereeee sum = 0 "+sum);
            final String kioskid = kiosks.get(i);

            final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);



            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                        String d = sale.getTime();
                        String amount = sale.getAmount();
                        int am = Integer.parseInt(amount);
                        sum+=am;
                        c++;

                        if (d.equals(date)) {
                            done.add(kioskid);
                            int av= sum/c;
                            amountt.add(amount);
                            kiosks2.add(kioskid);

                            System.out.println("sum for closing kiosk" + sum + " "+av + "\n");

                            final String num = h.get(kioskid);

                            int r = (int) (0.79 * av);
                            int o = (int) (0.8 * av);
                            int w = (int) av;
                            int g = (int) (1.2 * av);
                            String t;
                            if (am < r) {
                               // card.setBackgroundResourceId(R.color.red_error);
                                state.add("red");
                            }
                            else if (am < o) {
                              //  card.setBackgroundResourceId(R.color.holo_orange_light);
                                state.add("orange");
                            }
                            else if (am < w) {
                                state.add("white");
                               // card.setBackgroundResourceId(R.color.bpWhite);
                            }else if(am<g) {
                                state.add("green");
                               // card.setBackgroundResourceId(R.color.holo_green_light);
                            } else {
                                state.add("darkgreen");
                                //card.setBackgroundResourceId(R.color.green_complete);
                            }
                          //  card.setTitle("Total Sales For The Day: "+amount +"\n" +t);


                        }



                    }

                    p++;
                    if(p==1)
                    ofline();

                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });


        }







    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, homepage.class);
        startActivity(in);
    }
    public void ofline()
    {System.out.println("offline");
       // System.out.println("hi");
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String k = user1.getPass().toString();
                    String l = user1.getLoggedin().toString();

                    if (l.equals("false")) {

                        if (done.contains(k)) {

                        } else {

                            if(k.equals("Admin"))
                            {

                            }
                            else
                            {
                            final String num = h.get(k);


                            kiosks2.add(k);
                            state.add("offline");
                            amountt.add("0");
                        }}


                    }
                    if(l.equals("true"))
                    // if(l.equals("true"))
                    {


                        read(k);



                    }


                }
                display();



            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public  void display()
    {System.out.println("display");
        ArrayList<BarDataSet> dsets = new ArrayList<>();;
        ArrayList<String> d = new ArrayList<>();
        ArrayList<Integer> c = new ArrayList<>();
        ArrayList<BarEntry> values = new ArrayList<>();
       // TextView tv = (TextView) findViewById(R.id.textView9);
        String a="";
        System.out.print(kiosks2.size()+ " size is here");
        int j=0;
        for(int i =0;i<kiosks2.size();i++)
        {int am = Integer.parseInt(amountt.get(i));
            a = a+kiosks2.get(i)+ "  " +amountt.get(i)+" "+ state.get(i)+ "\n";
            //System.out.println("hereeeeeee"+a);
            switch (state.get(i))
            {
                case "red":c.add(getResources().getColor(R.color.red_error));
                    break;
                case "orange":c.add(getResources().getColor(R.color.orange_button));
                    break;
                case "white":c.add(getResources().getColor(R.color.divider_white));
                    break;
                case "green":c.add(getResources().getColor(R.color.holo_green_light));
                    break;
                case "darkgreen":c.add(getResources().getColor(R.color.green_complete));
                    break;
                case "offline":c.add(getResources().getColor(R.color.grey_title));
                    break;
                case "loggedin":c.add(getResources().getColor(R.color.blue_btn_bg_color));
                    break;
                default:c.add(getResources().getColor(R.color.black_button));
                    break;
            }
            values.add( new BarEntry(am/30,j));
            j++;

        }
        BarDataSet ds = new BarDataSet(values,"Total Sales For The Day(Units)");
        ds.setColors(c);

        float h= (float)j;
        dsets.add(ds);
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(kiosks2,ds);

        chart.setData(data);
        chart.setDescription("");
       float g= 6;
        chart.moveViewToX(h);
        chart.setVisibleXRangeMaximum(g);
        chart.animateXY(4000,4000);
        chart.invalidate();
    }
    public void read(final String kid) {
        System.out.println("read");
sum=0;
        SimpleDateFormat mont = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mont.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());



        String url = "https://kioskfarm.firebaseio.com/SALES/" + kid + "/" + yea + "/" + month + "/" + da;
        final Firebase ref = new Firebase(url);
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

                    sum = sum + amount;
                    //  System.out.println("aaaaaaaaaaaaaaaaa" + sum);


                }
                kiosks2.add(kid);
                state.add("loggedin");
                amountt.add(Integer.toString(sum*30));
                sum=0;
                display();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void readkiosks() {
        System.out.println("readkisok");
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String k = user1.getPass().toString();
                    if(k.equalsIgnoreCase("admin"))
                        continue;
                    kiosks.add(k);
                    String num = user1.getPhonenumber();
                    h.put(k,num);
                }
                SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
                final String date = mon.format(new Date());
                chart(date);


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }




}
