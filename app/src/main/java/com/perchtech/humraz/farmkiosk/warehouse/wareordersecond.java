package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.ordering;

import java.text.SimpleDateFormat;
import java.util.Date;


public class wareordersecond extends ActionBarActivity {
    String orderamount;
    String date2;
    String date;
    String kname= "yolo";
    String urgent= "Not Urgent";
    String url="";
    String unique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersecond);
        SharedPreferences prefs3= getSharedPreferences("log", MODE_PRIVATE);
        kname = prefs3.getString("pmname",null);
        orderamount= getIntent().getStringExtra("ordamount");
        date= getIntent().getStringExtra("date");
        urgent= getIntent().getStringExtra("urgent");
        TextView tv1= (TextView) findViewById(R.id.tv1);
        TextView tv2= (TextView) findViewById(R.id.tv2);
        TextView tv3= (TextView) findViewById(R.id.tv3);
        tv1.setText("Ordered Amount "+orderamount);
        tv2.setText("Date Required " +date);
        tv3.setText("State is " +urgent);
        Firebase.setAndroidContext(this);
        url="https://kioskfarm.firebaseio.com/PMORDERS/";
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        date2=da+"-"+month+"-"+yea;
        unique = kname+"/"+da+month+yea+"/"+orderamount;

    }
    public void confirm2(View v)
    {

        Firebase ref = new Firebase(url);
        ordering order = new ordering();
        order.setKioskname(kname);
        order.setAmount(orderamount);
        order.setDaterequired(date);
        order.setUrgent(urgent);
        order.setDateplaced(date2);
        order.setStatus("pending");

        order.setId(unique);
        ref.push().setValue(order);
        move();


    }
    public  void move(){
        Intent in = new Intent(this, wareSuccessafterorder.class);
        startActivity(in);
    }

}
