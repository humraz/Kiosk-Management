package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.GPSTracker;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.loginactivity;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

public class farmerdetails extends AppCompatActivity {
    GPSTracker gps;
    double latitude;
    double longitude;
    ToggleButton tg;
    String name;

    SwipeSelector swipeSelector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerdetails);
        Toast.makeText(this, "Harvest Map Has Been Recorded" ,Toast.LENGTH_LONG).show();
       tg= (ToggleButton) findViewById(R.id.toggleButton2);
        tg.setChecked(false);
        Intent in = getIntent();
        name =in.getStringExtra("name");


        swipeSelector = (SwipeSelector) findViewById(R.id.swipe1);
        swipeSelector.setItems(
                // The first argument is the value for that item, and should in most cases be unique for the
                // current SwipeSelector, just as you would assign values to radio buttons.
                // You can use the value later on to check what the selected item was.
                // The value can be any Object, here we're using ints.
                new SwipeItem(0, "Irrigation Method", "Pipeline"),
                new SwipeItem(1, "Irrigation Method", "Flood"),
                new SwipeItem(2, "Irrigation Method", "Drip")
        );

    }
    public void location(View view)

    {
        gps = new GPSTracker(farmerdetails.this);

// Post params to be sent to the server



        latitude = gps.getLatitude();
         longitude = gps.getLongitude();
    }

    public void proceed(View view)

    {
        SwipeItem selectedItem = swipeSelector.getSelectedItem();
String irigation = null;
        int value = (Integer) selectedItem.value;
        if (value == 0) {
            irigation="pipeline";

        }
        else if (value == 1) {
            irigation="Flood";

        }
        else if (value == 2) {
            irigation="Drip";

        }
        String borewell="";

        if(tg.isChecked())
        {
            borewell="true";
        }
        else

        {
            borewell="false";
        }

final String iri = irigation;
        final String bor= borewell;
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/harvestmap/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    farmerclass user1 = userSnapshot.getValue(farmerclass.class);
                    if (user1.getFarmname().equals(name))
                    {
                        userSnapshot.getRef().child("latitude").setValue(latitude);
                        userSnapshot.getRef().child("longitude").setValue(longitude);
                        userSnapshot.getRef().child("borewell").setValue(bor);
                        userSnapshot.getRef().child("irrigation").setValue(iri);

                    }

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
