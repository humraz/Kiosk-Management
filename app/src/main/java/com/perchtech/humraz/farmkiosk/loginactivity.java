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
import com.perchtech.humraz.farmkiosk.admin.adminlogin;
import com.perchtech.humraz.farmkiosk.admin.menuadmin;
import com.perchtech.humraz.farmkiosk.admin.sales;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;
import com.perchtech.humraz.farmkiosk.warehouse.warelogin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class loginactivity extends ActionBarActivity {
    ActionProcessButton btnSignIn;
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> pass = new ArrayList<String>();
    ArrayList<String> openball = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();
    ArrayList<String> outt = new ArrayList<String>();
    ArrayList<String> yopbal = new ArrayList<String>();
    String usernamee;
    ArrayList<String> rate = new ArrayList<String>();

    String name;
String openbal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        SharedPreferences pref3 = null;
        Intent in = getIntent();
        name=in.getStringExtra("username");
        //Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        SharedPreferences pref2 = getSharedPreferences("date", MODE_PRIVATE);
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month = mon.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
        String date2 = da + "-" + month + "-" + yea;
        pref2.edit().putString("datee", date2).commit();
        btnSignIn = (ActionProcessButton) findViewById(R.id.login);
        btnSignIn.setEnabled(false);
        Firebase.setAndroidContext(this);
        read();

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    GPSTracker gps;


    public boolean check(String namee)
    {   double l=0;
        double lon=0;
       /* if (namee.equalsIgnoreCase("yu"))
        {
            l=9.933725;
            lon= 76.260277;
        }
        else if (namee.equalsIgnoreCase("mindtree"))
        {
            l=12.918094;
             lon= 77.502573;
        }
        else if (namee.equalsIgnoreCase("embassytech"))
        {
            l=12.930961;
            lon= 77.692602;
        }
        else if (namee.equalsIgnoreCase("manyatatech"))
        {
            l=13.047537;
            lon= 77.621163;
        }
        else
        if (namee.equals("TCS")) {
             l=12.848052;
             lon=77.679291;
        }
        else
        if (namee.equals("Globalvillage")) {
            l=12.920141;
            lon=77.502959;
        }*/
       /*
        else
        {
            return true;
        }*/
        gps = new GPSTracker(loginactivity.this);

// Post params to be sent to the server



        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        //chnage this bro

        l=latitude;
         lon=longitude;
        double dist = distance(l, lon, latitude, longitude);
        if (dist>0.7)
        {
            return false;
        }
        else
        {
            return true;
        }





        //Toast.makeText(this, Double.toString(latitude) +Double.toString(longitude),Toast.LENGTH_LONG).show();
    }
    public void click(View view) {
        boolean loc = check(name);
        if (loc)
        {

            SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
        final String time = t.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
        final String date = mon.format(new Date());
        EditText ed1 = (EditText) findViewById(R.id.user2);
        EditText ed2 = (EditText) findViewById(R.id.pass);
        usernamee = name;
        String passs = ed2.getText().toString();
        int flag = 0;
        for (int i = 0; i < username.size(); i++) {
            String us2 = username.get(i);
            String passs2 = pass.get(i);
            if (us2.equals(usernamee) && passs.equals(passs2))

            {
                SharedPreferences prefs3 = null;
                openbal = openball.get(i);
                prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
                prefs3.edit().putString("kname", us2).commit();
                prefs3.edit().putString("openbal", openbal).commit();
                prefs3.edit().putString("time", outt.get(i)).commit();
                prefs3.edit().putString("outt", outt.get(i)).commit();
                prefs3.edit().putString("rate",rate.get(i)).commit();
                prefs3.edit().putString("yopbal",yopbal.get(i)).commit();
                Intent in = new Intent(this, billing.class);
                startActivity(in);
                finish();
                flag = 1;
            }
           /* else {

            }*/

        }
        if (flag == 1) {
            final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
            //Value event listener for realtime data update
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                        String passs = user1.getPass();
                        if (passs.equals(usernamee)) {
                            userSnapshot.getRef().child("loggedin").setValue("true");
                            userSnapshot.getRef().child("intime").setValue(time);
                            userSnapshot.getRef().child("flagdate").setValue(date);
                            userSnapshot.getRef().child("indate").setValue(date);
                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }
        if (flag == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("No User Found Or Wrong Password")

                    .show();
        }
    }
        else
        {
            TextView tv10=(TextView)findViewById(R.id.textView12);
            tv10.setText("You Are Too Far Away From The Kiosk!\nMake Sure You Login Only When At The Kiosk!");
           // Toast.makeText(this, "You are not at the kiosk yet!" ,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, kioskhomepage.class);
        startActivity(in);
    }
public void mo(View view)
{
    Intent in = new Intent( this, adminlogin.class);
    startActivity(in);
}
    int glag=0;
    public void warer(View view)
    {
        Intent in = new Intent( this, warelogin.class);
        startActivity(in);
    }
    String od;
    String yop;
    String ysto;
    public void read() {
        SimpleDateFormat daaaay = new SimpleDateFormat("dd/MM/yyyy");
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

                        userSnapshot.getRef().child("yeststock").setValue(user1.getStock().toString());
                        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/opbal&stock/"+name+"/"+a.replace("/",""));

                        //Getting values to store

                        //Creating Person object
                        kioskmake sale= new kioskmake();

                        //Adding values
                        sale.setOpeningbal(user1.getOpeningbal().toString());
                        sale.setStock(user1.getStock().toString());

                        sale.setIndate(a);

                        //Storing values to firebase
                        ref.push().setValue(sale);

                    }
                    String ti = user1.getIntime();
                    username.add(name);
                    pass.add(passs);
                    openball.add(user1.getOpeningbal().toString());
                    times.add(ti);
                    yopbal.add(user1.getYestopeningbal().toString());
                    outt.add(user1.getOuttime().toString());
                    rate.add(user1.getRate());



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
    public void ed()

    {
        SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");

        String month = mon.format(new Date());


    }


}
