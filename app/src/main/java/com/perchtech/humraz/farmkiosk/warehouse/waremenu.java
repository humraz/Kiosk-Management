package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;

public class waremenu extends AppCompatActivity {
String stock;
    String loss;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waremenu);
        Firebase.setAndroidContext(this);
        tv=(TextView) findViewById(R.id.textView7);
        read();

    }
    @Override
    public void onResume() {
        super.onResume();
        read();
    }
    public void read() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);
                    stock = user1.getStock();
                    loss=user1.getLoss().toString();

                }
                TextView tv2= (TextView) findViewById(R.id.textView);
                tv2.setText("Available Stock (Units): " +stock);
                tv.setText("Current Loss (Units): " +loss);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void reploss(View view)
    {
        Intent in = new Intent(this, reportloss.class);
        startActivity(in);
    }
    public void bill(View view)
    {
        Intent in = new Intent(this, bill.class);
        startActivity(in);
    }
    public void logout(View view)
    {
        Intent in = new Intent(this, warelogin.class);
        startActivity(in);
    }
    public void word(View view)
    {
        Intent in = new Intent(this, wareorders.class);
        startActivity(in);
    }
    public void loss(View view)
    {
        Intent in = new Intent(this, wareorderingfirst.class);
        startActivity(in);
    }
    @Override
    public void onBackPressed() {
       Intent in = new Intent(this, warelogin.class);
        startActivity(in);
    }
    public void pm(View view)
    {
       Intent in = new Intent(this, billhistory.class);
       startActivity(in);
    }
}
