package com.perchtech.humraz.farmkiosk;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.victor.loading.rotate.RotateLoading;

import org.eazegraph.lib.models.BarModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class salesmp extends AppCompatActivity {
    String kioskid = "yolo";
    String prevday;
    // ValueLineChart mCubicValueLineChart;
    //  ValueLineSeries series;
    org.eazegraph.lib.charts.BarChart mBarChart;

    float sum = 0;
    int c = 0;
    float av = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesmp);
        Firebase.setAndroidContext(this);
        //  ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname", null);
       // Toast.makeText(this, kioskid, Toast.LENGTH_LONG).show();
        //  ValueLineSeries series = new ValueLineSeries();
        //  series.setColor(0xFF56B7F1);
        calc();






    }


    public void calc() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                    String amount = sale.getAmount();
                    // hi(amount);
                    int amou = Integer.parseInt(amount);

                    sum = sum + amou;
                    c++;


                    //  series.addPoint(new ValueLinePoint(day, amou));


                }
                // mCubicValueLineChart.addSeries(series);
                // mCubicValueLineChart.startAnimation();
                av = sum / c;
                chart();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
/*
public void chart2()
{
    BarChart chart = (BarChart) findViewById(R.id.chart);

   // BarData data = new BarData(getxvals(),getdataset());

    //chart.setData(data);
    chart.setDescription("hello");
    chart.animateXY(4000,4000);
    chart.invalidate();
}
*/

    public void chart()

    {
       final ArrayList<BarDataSet> dsets = new ArrayList<>();;
        final ArrayList<String> d = new ArrayList<>();

        final ArrayList<BarEntry> values = new ArrayList<>();
        mBarChart = (org.eazegraph.lib.charts.BarChart) findViewById(R.id.barchart);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                int i= 0;
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                    String amount = sale.getAmount();
                    int amou = Integer.parseInt(amount);
                    String day = sale.getTime();
                    DateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
                    DateFormat outputFormat = new SimpleDateFormat("dd MMM");
                    String inputDateStr=day;
                    Date date = null;
                    try {
                        date = inputFormat.parse(inputDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outputDateStr = outputFormat.format(date);
                    d.add(outputDateStr);

                    values.add( new BarEntry((amou),i));
                    i++;


                }
                BarDataSet ds = new BarDataSet(values,"Total Sales (Units)");
                ds.setColors(ColorTemplate.COLORFUL_COLORS);

                float h= (float)i;
                dsets.add(ds);
                BarChart chart = (BarChart) findViewById(R.id.chart);

                BarData data = new BarData(d,ds);

                chart.setData(data);
                chart.setDescription("");
                float g= 7;
                chart.moveViewToX(h);
                chart.setVisibleXRangeMaximum(g);
                chart.animateXY(4000,4000);
                chart.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    private ArrayList<BarDataSet> getdataset() {

        ArrayList<BarDataSet> dsets = null;

        ArrayList<BarEntry> values = new ArrayList<>();
        Random ran = new Random();
        int x = ran.nextInt(10) + 10;


        BarEntry v1 = new BarEntry(x,0);
        values.add(v1);
        x = ran.nextInt(10) + 10;
        BarEntry v2 = new BarEntry(x,1);
        values.add(v2);
        x = ran.nextInt(100) + 10;
        BarEntry v3 = new BarEntry(x,2);
        values.add(v3);
        x = ran.nextInt(100) + 10;
        BarEntry v4 = new BarEntry(x,3);
        values.add(v4);
        x = ran.nextInt(100) + 10;
        BarEntry v5 = new BarEntry(x,4);
        values.add(v5);
        x = ran.nextInt(100) + 10;
        BarEntry v6 = new BarEntry(x,5);
        values.add(v6);

        BarDataSet ds = new BarDataSet(values,"Number of Accidents");
        ds.setColors(ColorTemplate.COLORFUL_COLORS);

        dsets = new ArrayList<>();
        dsets.add(ds);

        return dsets;


    }

   ArrayList<String> getxvals() {

        ArrayList<String> x = new ArrayList<>();

        x.add("00-04");
        x.add("04-08");
        x.add("08-12");
        x.add("12-16");
        x.add("16-20");
        x.add("20-24");

        return x;


    }



}
