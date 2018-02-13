package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.reportloss;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

public class pmloss extends AppCompatActivity {

    String loss;
    String stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);
        Firebase.setAndroidContext(this);
        read();

    }

    public void reploss(View view)
    {
        Intent in = new Intent(this, pmreportloss.class);
        startActivity(in);
    }


    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementstock/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                     loss = user1.getLoss();
                    stock = user1.getStock();




                    // tost(sum);


                }
                TextView tv= (TextView) findViewById(R.id.textView33);
                TextView tv2= (TextView) findViewById(R.id.textView);
                tv2.setText("Available : " +stock);
                tv.setText("LOSS : " + loss);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
