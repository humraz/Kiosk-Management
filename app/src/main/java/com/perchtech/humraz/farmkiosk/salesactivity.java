package com.perchtech.humraz.farmkiosk;

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

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class salesactivity extends ActionBarActivity {
    String kioskid = "yolo";
    String prevday;
    // ValueLineChart mCubicValueLineChart;
    //  ValueLineSeries series;
    BarChart mBarChart;
    RotateLoading rl;
    float sum = 0;
    int c = 0;
    float av = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesactivity);
        Firebase.setAndroidContext(this);
        //  ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname", null);
        rl = (RotateLoading) findViewById(R.id.rotateloading);

        rl.start();
        //  ValueLineSeries series = new ValueLineSeries();
        //  series.setColor(0xFF56B7F1);
        calc();
        // chart();


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

    public void chart()

    {

        //int n = Integer.valueOf(a);
        /// float v= (n/1000);
        //  mBarChart.addBar(new BarModel(v, 0xFF123456));

        mBarChart = (BarChart) findViewById(R.id.barchart);
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
                    String day = sale.getTime();
                    if (amou > av)
                        mBarChart.addBar(new BarModel(amou, 0xFF6CF775));
                    else
                        mBarChart.addBar(new BarModel(amou, 0xFFF7363B));
                    //  series.addPoint(new ValueLinePoint(day, amou));


                }
                // mCubicValueLineChart.addSeries(series);
                // mCubicValueLineChart.startAnimation();
                rl.stop();
                mBarChart.startAnimation();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void hi(String amount) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(amount)
                .setContentText("You Did Not Enter The Amount")

                .show();
    }

}

