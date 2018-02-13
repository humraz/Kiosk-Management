package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.Successafterstock;
import com.perchtech.humraz.farmkiosk.kioskmake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import android.view.View;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class pmstockincrease extends AppCompatActivity {
    String am;
    String stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmstockincrease);
        Firebase.setAndroidContext(this);
        read();

    }

    public void ccc(View view)
{
    EditText ed = (EditText) findViewById(R.id.editText4);
    am = ed.getText().toString();
    int amount= Integer.parseInt(am);
    int a = Integer.parseInt(stock);
    int tot= a+amount;
    String newstock = Integer.toString(tot);
    modify(newstock);
write();

    ed.setText("");

}



    public void modify(final String newstock) {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                        userSnapshot.getRef().child("stock").setValue(newstock);

                    }
                }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
public void st(View view)
{
    Intent in = new Intent(this, stockhistory.class);
    startActivity(in);
}
    public void write()
    {
        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/paststock");
        wareclass person = new wareclass();
        person.setStock(am);
        SimpleDateFormat t = new SimpleDateFormat("hh:mm:ss");

        String time = t.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

        String date = mon.format(new Date());
        person.setDate(date);
        person.setTime(time);

        ref.push().setValue(person);

        Toast.makeText(this, "Stock Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
        read();

    }
    public void read() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);
                    stock = user1.getStock();
                }
                TextView tv2= (TextView) findViewById(R.id.textView);
                tv2.setText("Available : " +stock);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
