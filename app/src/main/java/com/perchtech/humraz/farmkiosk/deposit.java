package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class deposit extends AppCompatActivity {
    String kioskid="yolo";
    String tyme;
    String date="";
    int sum = 0;
    String amount;
String yopbal;
    int ybal;
    String rate;
    int r=0;
int st;
    ProcessButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        SharedPreferences prefs3= getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname",null);
        yopbal= prefs3.getString("yopbal",null);
        ybal=Integer.parseInt(yopbal);
        rate= prefs3.getString("rate",null);
        r=Integer.parseInt(rate);
        Intent in = getIntent();
        st=in.getIntExtra("stock",0);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
        tyme= time.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        date=da+"-"+month+"-"+yea;
        Firebase.setAndroidContext(this);
        read();
        bt=(ProcessButton) findViewById(R.id.login);
        bt.setEnabled(false);
    }
    int cash=0;

    public void read()
    {
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/SALES/" + kioskid+"/"+yea+"/"+month+"/"+da);
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr="";
                int amount;
                int c=0;
                int paytm = 0;
                int card=0;
                int p=0;

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);


                    amstr=sale.getAmount();
                    amount=Integer.parseInt(amstr);
                    sum=sum+amount;
                    c++;

                    switch (sale.getPaymentmode())
                    {
                        case "paytm": p= Integer.parseInt(sale.getAmount());
                            paytm+=p;
                            break;
                        case "cash": p= Integer.parseInt(sale.getAmount());
                            cash+=p;
                            break;
                        case "card": p= Integer.parseInt(sale.getAmount());
                            card+=p;
                            break;

                    }
                    //tost(sum,c,cash,card,paytm);

bt.setEnabled(true);


                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, billing.class);
        in.putExtra("stock",st);
        startActivity(in);
    }
    public void write(View view)
    {int av= ybal + cash*r;
        EditText ed = (EditText) findViewById(R.id.user2) ;
        amount =ed.getText().toString();
        int am =Integer.parseInt(amount);
        av=av-am;

        if (amount == "")
        {
            Toast.makeText(this,"Enter The Amount First" ,Toast.LENGTH_LONG).show();
        }
        else if ( av<0)

        {
            Toast.makeText(this,"You Cannot Deposit More Cash Than Available" ,Toast.LENGTH_LONG).show();

        }
        else {
            Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/deposits/"+kioskid);
            deposits person = new deposits();

            SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");

            String time = t.format(new Date());
            SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

            String date = mon.format(new Date());
            person.setDate(date);
            person.setTime(time);
            person.setAmount(amount);
            person.setKid(kioskid);

            ref.push().setValue(person);

            //Toast.makeText(this, "Amount Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
            //read();
            new SweetAlertDialog(deposit.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Deposit has been confirmed")
                    .setContentText("Amount Added On date " + date )
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent in  = new Intent( deposit.this, billing.class);
                            in.putExtra("stock",st);
                            startActivity(in);

                        }
                    })
                    .show();
        }
    }
    public void tost(int sum, int cc,int ca, int car, int pay)
    {
        // rl.stop();
        // setContentView(R.layout.activity_errorafterstock);
        TextView tv1=(TextView) findViewById(R.id.textView);
        TextView tv2=(TextView) findViewById(R.id.textView2);

        tv1.setText("Cash In Hand: "+ sum*30);
        tv2.setText("Goods Sold: " +Integer.toString(sum));
        sum=0;

    }
}
