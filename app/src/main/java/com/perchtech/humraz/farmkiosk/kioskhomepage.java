package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.perchtech.humraz.farmkiosk.admin.kiosklist;
import com.perchtech.humraz.farmkiosk.admin.kwise;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class kioskhomepage extends AppCompatActivity {
    String name;
    static int[] imageResources = new int[]{

            R.drawable.order,


            R.drawable.board,
            R.drawable.taabb




    };

    static int[] Strings = new int[]{

            R.string.order,

            R.string.sale,

            R.string.depsit





    };
    SharedPreferences pref2= null;
    static int imageResourceIndex = 0;
    static int str = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kioskhomepage);
        Firebase.setAndroidContext(this);
        SharedPreferences pref3 =null;
        pref2 =getSharedPreferences("move",MODE_PRIVATE);
        pref2.edit().putString("f", "1").commit();
        pref3 = getSharedPreferences("kioskname", MODE_PRIVATE);
       name =pref3.getString("kname", null);
       boolean a=  check(name);
        String time=pref3.getString("time", null);
        String stock =pref3.getString("outt",null);
        String op = pref3.getString("openbal",null);
        String cashdiff =pref3.getString("rate",null);
        TextView tv= (TextView) findViewById(R.id.textView);
        TextView tv2= (TextView) findViewById(R.id.textView2);
        TextView tv4= (TextView) findViewById(R.id.textView5);
        TextView tv5= (TextView) findViewById(R.id.textView6);
        TextView tv8= (TextView) findViewById(R.id.textView8);

        SimpleDateFormat mon = new SimpleDateFormat("MMM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        String month = mon.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());
        String date2 = da + " " + month + " " +yea;
        tv.setText("Kiosk Name: " +name);
        tv2.setText(time);

tv8.setText("Rate for Kiosk(RS): " +cashdiff);
        tv4.setText("Current Stock(No.): " +stock);
        tv5.setText("Cash In Hand(Rs.) : " +op);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 1) {
                                start(index);

                            }
                            if (index == 0) {
                                orderingg(index);
                            }

                            if (index == 2) {
                                depsit(index);
                            }


                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);

    }}

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
public void rate(View view)
{
    Intent in= new Intent( this, ratechange.class);
    in.putExtra("name",name);
    startActivity(in);
}
    public boolean check(String namee)
    {   double l;
        double lon;

        if (namee.equalsIgnoreCase("yu"))
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
        }
        else
        {
           return true;
        }
        gps = new GPSTracker(kioskhomepage.this);

// Post params to be sent to the server



        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        double dist = distance(l, lon, latitude, longitude);
        TextView tv9= (TextView) findViewById(R.id.textView9);
        String d= Double.toString(dist);
        if (d.length()>5)
        d=d.substring(0,5);
        if (dist<0.4)
        {
            Random ran = new Random();
            int x = ran.nextInt(6) + 4;
            tv9.setText("Distance from Kiosk " + name + " is 0.0" + x + "  KM");

        }
else {
            tv9.setText("Distance from Kiosk " + name + " is " + d + " KM");
        }
return true;


        //Toast.makeText(this, Double.toString(latitude) +Double.toString(longitude),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume(){
        super.onResume();
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
if (name.equals(user1.getPass().toString()))
{   String stockk= user1.getStock().toString();
    String op = user1.getOpeningbal();
    int d =Integer.parseInt(user1.getDiff());
    int c= Integer.parseInt(user1.getOpeningbal());
    c=c-d;
    TextView tv5= (TextView) findViewById(R.id.textView6);
    TextView tv4= (TextView) findViewById(R.id.textView5);
    tv4.setText("Current Stock(No.): " +stockk);

    tv5.setText("Cash In Hand(Rs.) : " +op);
    SharedPreferences pref3 =null;
    pref3 = getSharedPreferences("kioskname", MODE_PRIVATE);
    pref3.edit().putString("openbal", op).commit();
    pref3.edit().putString("outt",stockk).commit();
    pref3.edit().putString("rate",user1.getRate()).commit();
    TextView tv8= (TextView) findViewById(R.id.textView8);
    tv8.setText("Rate for Kiosk(RS): " +user1.getRate());

}


                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public static int getString() {
        if (str >= Strings.length) str = 0;
        return Strings[str++];
    }
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    public void start(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, salesmp.class);
        startActivity(in);
    }

    public void orderingg(int pos)
    {
        // Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, orderingfirst.class);
        startActivity(in);
    }

    public void depsit(int pos)
    {
        Intent in = new Intent(kioskhomepage.this, kwise.class);
        in.putExtra("kid", name);
        startActivity(in);

        // Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
    }
    public  void click(View view)
    {
       // ProcessButton pd = (ProcessButton) findViewById( R.id.login);
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }

    public void onBackPressed() {
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }

    public  void start(View view)
    {

        Intent in =new Intent(this, loginactivity.class);
        in.putExtra("username", name);
        startActivity(in);
    }

}
