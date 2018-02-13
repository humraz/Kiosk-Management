package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.warehouse.wareorders;

import java.text.SimpleDateFormat;
import java.util.Date;

public class minuss extends AppCompatActivity {
int st;
    TextView tv;
    TextView tv2;
    String mode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minuss);
        Intent in = getIntent();
        tv=(TextView)findViewById(R.id.textView3);
      //  tv=(TextView)findViewById(R.id.textView43);
        st=in.getIntExtra("stock",0);
       // find(Integer.toString(st+1));
    }
    public void doo(View view)
    {

        if (mode.equals(""))
        {
            Toast.makeText(this, "Select mode first",Toast.LENGTH_LONG).show();
        }
        else {


            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            String tyme = time.format(new Date());
            SimpleDateFormat mon = new SimpleDateFormat("MM");
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            SimpleDateFormat day = new SimpleDateFormat("dd");
            String month = mon.format(new Date());
            String yea = year.format(new Date());
            String da = day.format(new Date());
            SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
            String kioskid = prefs3.getString("kname", null);
            Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/SALES/" + kioskid + "/" + yea + "/" + month + "/" + da);

            //Getting values to store
            EditText ed = (EditText) findViewById(R.id.editText6);

            String s = ed.getText().toString();
            String sup=s;
            if (s.equals(""))
            {
                Toast.makeText(this, "Enter a Value ",Toast.LENGTH_LONG).show();

            }
            else {


                s = "-" + s;

                //Creating Person object
                kiosksalesdaily sale = new kiosksalesdaily();

                //Adding values
                sale.setAmount(s);
                sale.setPaymentmode(mode);
                sale.setTime(tyme);

                //Storing values to firebase
                ref.push().setValue(sale);
                int so = Integer.parseInt(sup);
                st = st + so;
                find(Integer.toString(st));
            }
        }


    }
    public void cash(View view)
    {mode= "cash";
        BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);
        BootstrapButton ca= ( BootstrapButton) findViewById(R.id.card);
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.cash);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);

        p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        ca.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
        d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        Toast.makeText(this, "Selected CASH" , Toast.LENGTH_LONG).show();

    }
    public void card(View view)
    {mode= "card";
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.card);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);

        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
        p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
        d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        Toast.makeText(this, "Selected Card" , Toast.LENGTH_LONG).show();

    }
    public void paytm(View view)
    {mode= "paytm";
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.paytm);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton ca= (BootstrapButton) findViewById(R.id.card);
        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
        cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        ca.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
        d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        // p.setEnabled(false);
        //ca.setEnabled(false);
        Toast.makeText(this, "Selected Paytm" , Toast.LENGTH_LONG).show();

    }
    public void other(View view)
    {mode= "other";
        BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.other);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
        BootstrapButton v= ( BootstrapButton) findViewById(R.id.card);
        p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        v.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        // p.setEnabled(false);
        //ca.setEnabled(false);
        Toast.makeText(this, "Selected other" , Toast.LENGTH_LONG).show();

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
              //  tv.setText("Current Stock is :"+sto );
//                tv2.setText("Previous Stock: "+Integer.toString(st));
                Toast.makeText(minuss.this, "Corrections Done",Toast.LENGTH_LONG).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent in = new Intent(minuss.this,billing.class);
                        finish();
                        startActivity(in);
                    }
                }, 2000);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


}
