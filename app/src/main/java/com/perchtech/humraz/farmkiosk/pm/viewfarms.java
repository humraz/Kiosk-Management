package com.perchtech.humraz.farmkiosk.pm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

public class viewfarms extends AppCompatActivity {
String farmname="mysore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfarms);
        Firebase.setAndroidContext(this);
        read();
    }
    int num[][]=new int[100][100];
    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/harvestmap/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    farmerclass user1 = userSnapshot.getValue(farmerclass.class);

                    if (user1.getFarmname().equals("mysore"))
                    {
                       num=user1.getHarvestmap1();
                    }



                    // tost(sum);


                }
               /* TextView tv= (TextView) findViewById(R.id.textView13);
                tv.setText(map);*/

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
