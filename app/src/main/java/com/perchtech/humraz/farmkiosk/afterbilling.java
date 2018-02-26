package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapBadge;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class afterbilling extends ActionBarActivity {
String number;
    String payment="";
    String kioskid="yolo";
    String url="";
    String date="";
    String tyme;
    String stock;
    RotateLoading rl;
    int no;
    int check;
    int st;
    ProcessButton pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterbilling);
       number= getIntent().getStringExtra("number");
        st=getIntent().getIntExtra("stock",0);
        check=getIntent().getIntExtra("checker",0);
      TextView  tv= (TextView) findViewById(R.id.tv1);
        tv.setText(number);
        Firebase.setAndroidContext(this);
       SharedPreferences prefs3= getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname",null);
        /////GEtting The date
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        tyme= time.format(new Date());
        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
         date=da+"-"+month+"-"+yea;
        pd=(ProcessButton) findViewById(R.id.checkout);
        url="https://kioskfarm.firebaseio.com/SALES/" + kioskid+"/"+yea+"/"+month+"/"+da;

     // func();


        //r();

    }
/*public  void  func()
{
    final Firebase ref = new Firebase(url);
    //Adding values
    if (ref.child("paymentmode").equals())
    kiosksalesdaily sale = new kiosksalesdaily();
    sale.setPaymentmode("Cash");
    sale.setAmount("0");

    //Storing values to firebase
    ref.push().setValue(sale);
    sale.setPaymentmode("paytm");

    sale.setAmount("0");
    sale.setPaymentmode("card");

    sale.setAmount("0");
    sale.setPaymentmode("other");

    sale.setAmount("0");
    //Storing values to firebase
    ref.push().setValue(sale);
}*/


   /* public void r() {
        SharedPreferences prefs3= getSharedPreferences("kioskname",MODE_PRIVATE);
        final  String  k= prefs3.getString("kname", null);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                    String idd = user1.getPass();
                    if(k.equals(idd))
                    {
                        stock= user1.getStock();
                        st=Integer.parseInt(stock);

                    }


                    // tost(sum);


                }

pd.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }*/

    public void error()
    {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("You Did Not Select The Payment Method")
                .show();
    }
    public void click(View v)
    {
        final String p= payment;
        final String amount= number;
        no=Integer.parseInt(number);
        st=st-no;
        check=check-no;
        stock=Integer.toString(st);
        final String time= tyme;
if(payment.equals(""))
{
error();
}else if(check<0)
{
    new SweetAlertDialog(afterbilling.this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Alert!")
            .setContentText("Your Stock is too less to perfom this transaction. Consider Changing the amount")


            .show();
}
        else {

  final Firebase ref = new Firebase(url);
    //Value event listener for realtime data update



    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot usersSnapshot) {
            if (usersSnapshot.exists()) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {

                    kiosksalesdaily ord = userSnapshot.getValue(kiosksalesdaily.class);
                    String am= ord.getAmount();

                    if (ord.getPaymentmode().equals(p)) {

                        int a = Integer.parseInt(am);
                        int b= Integer.parseInt(amount);
                        int g=a+b;

                        userSnapshot.getRef().child("amount").setValue(Integer.toString(g));
                        userSnapshot.getRef().child("time").setValue(time);
                    }
                }
            }
                else {
                final Firebase ref = new Firebase(url);
                //Adding values

                kiosksalesdaily sale = new kiosksalesdaily();
                sale.setPaymentmode(p);
                sale.setTime(time);
                sale.setAmount(amount);
                ref.push().setValue(sale);
                if (!p.equals("cash")) {

                    sale.setTime(time);
                    sale.setPaymentmode("cash");
                    sale.setAmount("0");
                    ref.push().setValue(sale);
                }
                //Storing values to firebase

                if (!p.equals("paytm")) {
                    sale.setPaymentmode("paytm");
                    sale.setTime(time);
                    sale.setAmount("0");
                    ref.push().setValue(sale);
                }

                if (!p.equals("card")) {
                    sale.setPaymentmode("card");
                    sale.setTime(time);
                    sale.setAmount("0");
                    ref.push().setValue(sale);
                }

                if (!p.equals("other")) {
                    sale.setPaymentmode("other");
                    sale.setTime(time);

                    sale.setAmount("0");
                    ref.push().setValue(sale);
                }
                //Storing values to firebase

                }





        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }
    });



    ///////////////////////// old onehere in case error in sales
    /*
    Firebase ref = new Firebase(url);

    //Getting values to store

    //Creating Person object
    kiosksalesdaily sale = new kiosksalesdaily();

    //Adding values
    sale.setAmount(amount);
    sale.setPaymentmode(p);
    sale.setTime(time);

    //Storing values to firebase
    ref.push().setValue(sale);
*/  rl = (RotateLoading) findViewById(R.id.rotateloading);
    rl.start();
    find(stock);



}
    }
public void cash(View view)
{payment= "cash";
    BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);
    BootstrapButton ca= ( BootstrapButton) findViewById(R.id.card);
    BootstrapButton g= ( BootstrapButton) findViewById(R.id.cash);
    g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);

    p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
    ca.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
    BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
    d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
  //  Toast.makeText(this, "Selected CASH" , Toast.LENGTH_LONG).show();

}
    public void paytm(View view)
    {
payment ="paytm";
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.paytm);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton ca= (BootstrapButton) findViewById(R.id.card);
        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
       cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
       ca.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
        d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
      //  Toast.makeText(this, "Selected PAY-TM" , Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, billing.class);
        in.putExtra("stock",Integer.toString(st));
        startActivity(in);
    }

    public void card(View view)
    {
payment="card";
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.card);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);

        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
        p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        BootstrapButton d= ( BootstrapButton) findViewById(R.id.other);
        d.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
      //  Toast.makeText(this, "Selected CARD" , Toast.LENGTH_LONG).show();
    }
    public void other(View view)
    {
        payment="other";
        BootstrapButton p= ( BootstrapButton) findViewById(R.id.paytm);
        BootstrapButton g= ( BootstrapButton) findViewById(R.id.other);
        g.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        BootstrapButton cash= ( BootstrapButton) findViewById(R.id.cash);
        BootstrapButton v= ( BootstrapButton) findViewById(R.id.card);
        p.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        cash.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        v.setBootstrapBrand(DefaultBootstrapBrand.WARNING);

        //Toast.makeText(this, "Selected Other" , Toast.LENGTH_LONG).show();
    }

    public void find(final String sto )
    {
       /* final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
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
                }*/
                rl.stop();
                // Toast.makeText(afterbilling.this, "Successfully Added sale to Server." , Toast.LENGTH_LONG).show();

                Intent in = new Intent(afterbilling.this, billing.class);
               in.putExtra("stock",stock);
                finish();
                startActivity(in);
          /*  }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
*/
    }

}
