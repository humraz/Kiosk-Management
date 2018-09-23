package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class billing extends ActionBarActivity {
String number="0";
    TextView tv;
    static int[] imageResources = new int[]{


            R.drawable.board,

            R.drawable.exit,
            R.drawable.deposit,





    };
    static int[] Strings = new int[]{


            R.string.sale,
            R.string.sess,

            R.string.depsi,






    };
    static int imageResourceIndex = 0;
    static int str = 0;
    SharedPreferences pref2=null;
    String stock;
    int st;
    int flag=0;
    int sum = 0;
    int cash =0;
    int t;
    int amount;
    int c=0;
    Button bt;
    int caa=0;
    int paytm = 0;
    int other=0;
    int card=0;
    int dam;
    int n2;
    String rate;
    String pp;
    int p=0;
    int addst;
    int s=0;
    int s3=0;
    String kioskid;
    String tyme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        tv = (TextView) findViewById(R.id.tv1);
        Firebase.setAndroidContext(this);
        Intent in = getIntent();
        pp=in.getStringExtra("stock");

        s=Integer.parseInt(pp);
        r();
       //
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
      //  SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
s3=Integer.parseInt(prefs3.getString("stock",null));
        pref2 =getSharedPreferences("move",MODE_PRIVATE);
        rate= prefs3.getString("rate",null);
        n2=Integer.parseInt(rate);
        dam=Integer.parseInt(prefs3.getString("damage",null));
        pref2.edit().putString("f", "1").commit();
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bt=(Button) findViewById(R.id.minus);
        bt.setEnabled(false);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.
                HAM_3);
        addst=Integer.parseInt(prefs3.getString("addst",null));
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {

                            if (index == 0) {
                                start(index);

                            }

                            if (index == 1) {
                                stock(index);

                            }

                            if (index == 2) {
                                deposit(index);

                            }



                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);
        }
        caa=0;
        sum = 0;
        paytm = 0;
        other=0;
        card=0;
        r();
        read();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                caa=0;
                sum = 0;
                paytm = 0;
                 other=0;
                card=0;
               r();
                read();
               // Toast.makeText(billing.this, "S" , Toast.LENGTH_LONG).show();


            }
        }, 800);
    }

    public void read()
    {




        String kioskid="";
        SharedPreferences prefs3= getSharedPreferences("kioskname", MODE_PRIVATE);
        kioskid = prefs3.getString("kname",null);



        SimpleDateFormat mon = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String month= mon.format(new Date());
        String yea= year.format(new Date());
        String da= day.format(new Date());
        final String url="https://kioskfarm.firebaseio.com/SALES/" + kioskid+"/"+yea+"/"+month+"/"+da;

        System.out.println("The read failed: " + kioskid+"/"+yea+"/"+month+"/"+da);

        final Firebase ref = new Firebase(url);
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                String amstr="";
                if (usersSnapshot.exists())
                {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);



                    System.out.println("The read failed: " + caa+ card+paytm);
                    amstr=sale.getAmount();
                    amount=Integer.parseInt(amstr);
                  //  String tt= sale.getTime();


                        sum = sum + amount;
                        c++;

                        switch (sale.getPaymentmode()) {
                            case "paytm":

                                p = Integer.parseInt(sale.getAmount());
                                paytm += p;
                                break;
                            case "cash":
                                p = Integer.parseInt(sale.getAmount());
                                caa += p;
                                break;
                            case "card":
                                p = Integer.parseInt(sale.getAmount());
                                card += p;
                                break;
                            case "other":
                                p=Integer.parseInt(sale.getAmount());
                                other+=p;
                                break;





                    }


                }
              //  System.out.println("The read failed: " + cash+ card+paytm);

                tost(sum, c, caa, card, paytm, other);
               // btnSignIn.setEnabled(true);
            }
                else
                {
                    final Firebase ref = new Firebase( url);
                    //Adding values
                    String p ="cash";
                    String time= tyme;

                    kiosksalesdaily sale = new kiosksalesdaily();
                    sale.setPaymentmode(p);
                    sale.setTime(time);
                    sale.setAmount("0");
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

    }

    //minus function

    public void minus(View view)
    {

      Intent in =new Intent(this, minuss.class);
        in.putExtra("stock", st);

        startActivity(in);




    }








    int n=1;
    int f=1;
    public void no(View view)
    {


       // Button bt = (Button)findViewById(R.id.button7);

        if (f==1)
        {
           // bt.setText("Now Showing in Rupees");
            n=n2;
            f=0;
        }
        else
        {
           // bt.setText("Now Showing in Numbers");
            n=1;
            f=1;
        }

        tost(sum, c, caa, card, paytm, other);
    }

    public void tost(int sum, int cc,int ca, int car, int pay, int other)
    {
        // rl.stop();
        String  y=" No.";

        // setContentView(R.layout.activity_errorafterstock);
        if (n==1)
            y=" No.";
        else
            y=" Rs.";
        // setContentView(R.layout.activity_errorafterstock);
        TextView tv12=(TextView) findViewById(R.id.cash1);
        TextView tv122=(TextView) findViewById(R.id.card1);
        TextView tv1=(TextView) findViewById(R.id.pay1);
        TextView tv11=(TextView) findViewById(R.id.other1);

      // String a= String.format("Cash (Rs) ",Integer.toString(ca*n2));
        //a.replace(" ", "&nbsp");
        if (ca*n<0)
            tv12.setText(("Cash "+y+Integer.toString(0)));
            else

         tv12.setText(("Cash "+y+Integer.toString(ca*n)));
       // String b= String.format("Card(Rs) ", Integer.toString(car*n2));
        //b.replace(" ", "&nbsp");
        if (pay*n<0)
            tv1.setText(" | Pay-Tm "+y+ Integer.toString(0));
            else
        tv1.setText(" | Pay-Tm "+y+ Integer.toString(pay*n));

        if (car*n<0)
            tv122.setText((" | Card "+y+ Integer.toString(0)));
            else

         tv122.setText((" | Card "+y+ Integer.toString(car*n)));
        //String c =String.format("Pay-Tm(Rs) ", Integer.toString(pay*n2));
        // c.replace(" ", "&nbsp");

      //  String d= String.format("Other (Rs) ", Integer.toString(other*n2));
        //d.replace(" ", "&nbsp");
        if (other*n<0)
            tv11.setText((" | Other "+y+ Integer.toString(0)));
            else

        tv11.setText((" | Other "+y+ Integer.toString(other*n)));

        TextView tv2= (TextView) findViewById(R.id.textView3);
        System.out.println(s);
        // String p = Integer.toString(s);
        s=s3;
        s=s-sum-dam+addst;
        tv2.setText("Current Stock(Units) : " +Integer.toString(s));

      //  tv55.setText(String.format("%-38s %-5s","Cash In Hand(Rs): ", Integer.toString((cash*n2)-deposit+op)));

    }
    public void r() {
      /*  SharedPreferences prefs3= getSharedPreferences("kioskname",MODE_PRIVATE);
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


                }*/
        st=s;
               // bt.setEnabled(true);
                 if(st==0 && flag==0) {
                     flag=1;
                    new SweetAlertDialog(billing.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sorry!")
                            .setContentText("Your Stock is now 0. Please order and update Stock before proceeding.")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();

                }




                     else if   (st<5 &&flag ==0)
                {
                    flag=1;
                    new SweetAlertDialog(billing.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warning!")
                            .setContentText("Your Stock is declining and is less than 5. Please order more stock in the ordering tab.")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }



           /* }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
*/
    }
    public static int getString() {
        if (str >= Strings.length) str = 0;
        return Strings[str++];
    }
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    public void start(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, salesmp.class);
        startActivity(in);
    }
public void damages(int pos) {

    Intent in = new Intent(this, damages.class);
    in.putExtra("stock", st);
    startActivity(in);
}
    public void deposit(int pos) {

        Intent in = new Intent(this, depositnewhomepage.class);
        in.putExtra("stock", st);
        in.putExtra("cash",caa);
        startActivity(in);
    }
    /*public void orderingg(int pos)
    {
        // Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, orderingfirst.class);
        pref2.edit().putString("f", "0").commit();
        startActivity(in);
    }*/
    public void stock(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, Stocktakingfirst.class);
        startActivity(in);
    }

public void one(View view)
{
    if(tv.getText().toString().equals(""))
    {
        tv.setText("1");
        number= "1";
    }
    else
    {
        String st= tv.getText().toString();
        tv.setText(st+"1");
        number= st+"1";
    }
}
    public void two(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            tv.setText("2");
            number= "2";
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"2");
            number= st+"2";
        }
    }

    public void three(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "3";
            tv.setText("3");
        }
        else
        {

            String st= tv.getText().toString();
            tv.setText(st+"3");
            number= st+"3";
        }
    }
    public void four(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "4";
            tv.setText("4");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"4");
            number= st+"4";
        }
    }
    public void five(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "5";
            tv.setText("5");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"5");
            number= st+"5";
        }
    }
    @Override
    public void onBackPressed() {
       Toast.makeText(this, "Please Log out If you are done",Toast.LENGTH_SHORT).show();}
    public void six(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "6";
            tv.setText("6");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"6");
            number= st+"6";
        }
    }
    public void seven(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "7";
            tv.setText("7");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"7");
            number= st+"7";
        }
    }
    public void eight(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "8";
            tv.setText("8");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"8");
            number= st+"8";
        }
    }
    public void nine(View view)
    {
        if(tv.getText().toString().equals(""))
        {
            number= "9";
            tv.setText("9");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"9");
            number= st+"9";
        }
    }
    public void zero(View view)
    {
        if(tv.getText().toString().equals(""))
        {number= "0";
            tv.setText("");
        }
        else
        {
            String st= tv.getText().toString();
            tv.setText(st+"0");
            number= st+"0";
        }
    }
    public void clear(View view)
    {
tv.setText("");
        number= "";
    }
    public void check(View view)
    {
if(tv.getText().equals(""))
{  new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        .setTitleText("Oops...")
        .setContentText("You Did Not Enter The Amount")
        .show();

}
else {
   move();
}

    }
    public void move()
    {
        Intent in = new Intent(this, afterbilling.class);
        in.putExtra("number", number);
        in.putExtra("stock",st);
        in.putExtra("checker",s);
        finish();
        startActivity(in);
    }

    @Override
    public void onResume() {
        super.onResume();
        bt.setEnabled(false);

        r();
        bt.setEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
