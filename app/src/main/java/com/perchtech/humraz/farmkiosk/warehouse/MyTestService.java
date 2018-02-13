package com.perchtech.humraz.farmkiosk.warehouse;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.ordering;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyTestService extends IntentService {
    ArrayList<String> expiry = new ArrayList<String>();
    public MyTestService() {
        super("MyTestService");

    }
    String date;
    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("started service");

        SimpleDateFormat mon = new SimpleDateFormat("dd");
        date = mon.format(new Date());
        Firebase.setAndroidContext(this);
        find();
    }
    String f;
    String g;

    public void find() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering noo = userSnapshot.getValue(ordering.class);
                    f = noo.getStatus().toString();
                    g= noo.getDateplaced().toString();
                    String g2=g.substring(0,2);
                    if(f.equalsIgnoreCase("pending"))
                    {
                        int a=Integer.parseInt(g2);
                        int b= Integer.parseInt(date);
                        if(b>a)
                        {
                            String id= noo.getId().toString();
                            expiry.add(id);
                        }
                    }
                }
                modify();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void modify() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering user1 = userSnapshot.getValue(ordering.class);
                    String id = user1.getId();
                    for(int i=0;i<expiry.size();i++) {
                        String temp = expiry.get(i);
                        if(temp.equals(id))
                            userSnapshot.getRef().child("status").setValue("expired");
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

  /*
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, warelogin.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("New Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
                            //    .setSound(Uri.parse("android.resource://"
                              //          + getApplicationContext().getPackageName() + "/" + R.raw.s))
                                        //  .setSound(R.raq)
                       // .setSmallIcon(R.drawable.chat);



     //   AudioManager am;
       // am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        //am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        //int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
       // am.setStreamVolume(AudioManager.STREAM_ALARM, 6,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }*/

}
