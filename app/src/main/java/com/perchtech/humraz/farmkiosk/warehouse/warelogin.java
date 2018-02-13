package com.perchtech.humraz.farmkiosk.warehouse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.admin.sales;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.loginactivity;
import com.perchtech.humraz.farmkiosk.newlogin;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class warelogin extends AppCompatActivity {
    ActionProcessButton btnSignIn;
    ArrayList<String> username = new ArrayList<String>();
    ;
    ArrayList<String> pass = new ArrayList<String>();
    ;
    String usernamee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelogin);
        btnSignIn = (ActionProcessButton) findViewById(R.id.login);
        btnSignIn.setEnabled(false);
        Firebase.setAndroidContext(this);
        read();
        scheduleAlarm();
    }
    public void cl(View view) {
        EditText ed1 = (EditText) findViewById(R.id.user2);
        EditText ed2 = (EditText) findViewById(R.id.pass);
        usernamee = ed1.getText().toString();
        String passs = ed2.getText().toString();
        int flag = 0;
        for (int i = 0; i < username.size(); i++) {
            String us2 = username.get(i);
            String passs2 = pass.get(i);
            if (usernamee.equalsIgnoreCase("admin") && passs.equals(passs2))

            {

                Intent in = new Intent(this, waremenu.class);
                startActivity(in);
                finish();
                flag = 1;
            }
           /* else {

            }*/

        }

        if (flag == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("No User Found Or Wrong Password")

                    .show();
        }

    }
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,AlarmManager.INTERVAL_HOUR
                , pIntent);

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }
   /* public void cl(View view)
    {
        Intent in = new Intent(this , waremenu.class);
        startActivity(in);
    }*/
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
                    username.add(name);
                    pass.add(passs);


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

}
