package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.perchtech.humraz.farmkiosk.R.string.sale;

public class damages extends AppCompatActivity {
    int st;
    TextView tv;
    TextView tv2;
    String mode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damages);

        Intent in = getIntent();

        tv=(TextView)findViewById(R.id.textView3);
        //  tv=(TextView)findViewById(R.id.textView43);
        st=in.getIntExtra("stock",0);
        tv.setText("Stock: " +st);
    }

    public void doo(View view)
    {




            SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
            String kioskid = prefs3.getString("kname", null);

            //Getting values to store
            EditText ed = (EditText) findViewById(R.id.editText6);
            String s = ed.getText().toString();
            String news=s;
            s="-"+s;
            SimpleDateFormat t = new SimpleDateFormat("hh:mm:ss");

            String time = t.format(new Date());
            SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

            String date = mon.format(new Date());
            Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/kioskdamages/"+kioskid);

            //Getting values to store

            //Creating Person object
            wareclass sale= new wareclass();

            //Adding values
            sale.setStock(news);
            sale.setTime(time);
            sale.setDate(date);

            //Storing values to firebase
            ref.push().setValue(sale);
            int so= Integer.parseInt(s);
            st=st+so;
            find(Integer.toString(st));




    }
    public void history(View view)
    {
        Intent in= new Intent(this, damagehistory.class);
        startActivity(in);
    }
    public void find(final String sto )
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        SharedPreferences prefs3= getSharedPreferences("kioskname",MODE_PRIVATE);
        final  String  k= prefs3.getString("kname", null);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake ord = userSnapshot.getValue(kioskmake.class);
                    String id2 = ord.getPass();
                    if(id2.equals(k))
                    {
                        userSnapshot.getRef().child("stock").setValue(sto);
                    }
                }
                tv.setText("Current Stock is :"+sto );
//                tv2.setText("Previous Stock: "+Integer.toString(st));
                Toast.makeText(damages.this, "Damages Confirmed.",Toast.LENGTH_LONG).show();
                Intent in = new Intent(damages.this,billing.class);
                startActivity(in);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
