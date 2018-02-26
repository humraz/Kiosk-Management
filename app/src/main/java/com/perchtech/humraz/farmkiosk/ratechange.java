package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ratechange extends AppCompatActivity {
String  name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratechange);
        Intent in = getIntent();
        name=in.getStringExtra("name");
    }

public void change(View view) {
    final EditText ed = (EditText) findViewById(R.id.editText6);
    final String r = ed.getText().toString();
    if (r.equals("")) {
Toast.makeText(this ,"Enter an amount first",Toast.LENGTH_LONG).show();
    } else {


        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String passs = user1.getPass();
                    if (passs.equals(name)) {
                        userSnapshot.getRef().child("rate").setValue(r);

                    }
                }
                Toast.makeText(ratechange.this, "Rate Change Confirmed.", Toast.LENGTH_LONG).show();
                Intent in = new Intent(ratechange.this, kioskhomepage.class);

                startActivity(in);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
}
