package com.perchtech.humraz.farmkiosk;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.admin.adminlogin;
import com.perchtech.humraz.farmkiosk.pm.pmlogin;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;
import com.perchtech.humraz.farmkiosk.warehouse.warelogin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class newlogin extends AppCompatActivity {
    ActionProcessButton btnSignIn;
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> pass = new ArrayList<String>();
    ArrayList<String> openball = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();
    ArrayList<String> outt = new ArrayList<String>();
    ArrayList<String> rate = new ArrayList<String>();
    ArrayList<String> stat = new ArrayList<String>();
    String usernamee;
    String openbal;
    String diff;


    SharedPreferences pref4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlogin);
        SharedPreferences pref3 = null;
        SharedPreferences pref2 = getSharedPreferences("date", MODE_PRIVATE);
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mon.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
        pref4=getSharedPreferences("difference",MODE_PRIVATE);
        String date2 = da + "-" + month + "-" + yea;
        pref2.edit().putString("datee", date2).commit();
        btnSignIn = (ActionProcessButton) findViewById(R.id.login);
        btnSignIn.setEnabled(false);
        Firebase.setAndroidContext(this);
       //
        // read();
        read3();
      //  write();
        read2();
        boolean c= checkLocationPermission();
        if (c)
        {
            chec();
        }

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission. ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission. ACCESS_COARSE_LOCATION)) {



            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission. ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    public void chec()
    {

        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user


            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Gps Not Enabled!")
                    .setContentText("Open Settings")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(myIntent);


                        }
                    })
                    .show();


        }

    }
    public void click(View view) {
        SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
        final String time = t.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
        final String date = mon.format(new Date());
        EditText ed1 = (EditText) findViewById(R.id.user2);
        EditText ed2 = (EditText) findViewById(R.id.pass);
        usernamee = ed1.getText().toString();
        String passs = ed2.getText().toString();
        int flag = 0;
        for (int i = 0; i < username.size(); i++) {
            String us2 = username.get(i);
            String passs2 = pass.get(i);
            if (usernamee.equals("Admin"))
            {

            }
            else if (us2.equals(usernamee) && passs.equals(passs2))

            {
                SharedPreferences prefs3 = null;
                openbal=openball.get(i);
                prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
                prefs3.edit().putString("kname", us2).commit();
                prefs3.edit().putString("openbal", openbal).commit();
                prefs3.edit().putString("time",times.get(i)).commit();
                prefs3.edit().putString("outt",outt.get(i)).commit();
                prefs3.edit().putString("rate" ,rate.get(i)).commit();
                String s =stat.get(i);
                if (s.equals("true"))
                {
                    Intent in  = new Intent(this, loginactivity.class);
                    in.putExtra("username", us2);
                    startActivity(in);
                }
                else
                {
                    Intent in = new Intent(this, kioskhomepage.class);
                    startActivity(in);

                }
               // startActivity(in);
                finish();
                flag = 1;
            }
           /* else {

            }*/

        }

    }

    public void read2() {
        final SimpleDateFormat daaaay = new SimpleDateFormat("dd/MM/yyyy");
        final String month = daaaay.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                    String passs = user1.getPost();
                    String name = user1.getPass();
                    String a= user1.getIndate();
                    String fda= user1.getFlagdate();


                    if (fda.equals(month))
                    {

                    }else
                    {
                        userSnapshot.getRef().child("yestopeningbal").setValue(user1.getOpeningbal().toString());
                        userSnapshot.getRef().child("flagdate").setValue(month);
                        userSnapshot.getRef().child("diff").setValue("0");

                        userSnapshot.getRef().child("yeststock").setValue(user1.getStock().toString());
                        //userSnapshot.getRef().child("openingbal").setValue(user1.getOpeningbal().toString());
                      //  userSnapshot.getRef().child("stock").setValue(user1.getOpeningbal().toString());
                        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/opbal&stock/"+name+"/"+month.replace("/",""));

                        //Getting values to store

                        //Creating Person object
                        kioskmake sale= new kioskmake();

                        //Adding values
                        sale.setOpeningbal(user1.getOpeningbal().toString());

                        sale.setStock(user1.getStock().toString());
                        sale.setYeststock(user1.getStock().toString());
                        sale.setYestopeningbal(user1.getOpeningbal().toString());

                        sale.setIndate(month);

                        //Storing values to firebase
                        ref.push().setValue(sale);

                    }

                }

//                btnSignIn.setEnabled(true);
                read();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                    String passs = user1.getPost();
                    String name = user1.getPass();
                    String ti = user1.getIntime();
                    String d=user1.getIndate();
                    username.add(name);
                    pass.add(passs);
                    openball.add(user1.getOpeningbal().toString());

                    DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
                    String inputDateStr=d;
                    Date date = null;
                    try {
                        date = inputFormat.parse(inputDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outputDateStr = outputFormat.format(date);



                    times.add("Time: " + ti+ "\nDate :\u0020" + outputDateStr);
                    outt.add(user1.getStock().toString());
                    diff =user1.getRate();
                    rate.add(diff);
                    stat.add(user1.getLoggedin());




                    // tost(sum);


                }

                btnSignIn.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    String pp;
    public void read3() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/VERSION/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                        pp=user1.getPass();
                    if (pp.equals("3.1"))
                    {

                    }
                    else
                    {
                        new SweetAlertDialog(newlogin.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Old Version Detected!")
                                .setContentText("You are using an old version that is unstable, press ok to update now.")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.perchtech.humraz.farmkiosk")));                                }
                                })
                                .show();
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void write()
    {
        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/VERSION/");
        kioskmake person = new kioskmake();
        person.setPass("3.1");



        ref.push().setValue(person);



    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }
    public void mo(View view)
    {
        Intent in = new Intent( this, adminlogin.class);
        startActivity(in);
    }
    public void pm(View view)
    {
        Intent in = new Intent( this, pmlogin.class);
        startActivity(in);
    }
    public void warer(View view)
    {
        Intent in = new Intent( this, warelogin.class);
        startActivity(in);
    }
}
