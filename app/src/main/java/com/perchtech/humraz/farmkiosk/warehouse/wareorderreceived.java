package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.pastorders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class wareorderreceived extends AppCompatActivity implements DatePickerDialogFragment.DatePickerDialogHandler,TimePickerDialogFragment.TimePickerDialogHandler{
    String id;
    String na;
    String kname;
    String amount;
    String newamount;
    String date;
    String time;
    String stock;
    int st;
    String amountrec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderreceived);
        Intent in =getIntent();
        id= in.getStringExtra("id");
        na= in.getStringExtra("na");
      //  Toast.makeText(this, na , Toast.LENGTH_LONG).show();
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

        date= mon.format(new Date());
        time="9.00";

        r();
        read();
    }
    public void  today(View view)
    {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String todayAsString = dateFormat.format(today);
        //String tomorrowAsString = dateFormat.format(tomorrow);
        date=todayAsString;
        System.out.println(todayAsString);
        // System.out.println(tomorrowAsString);

    }
    public void  tomorrow(View view)
    {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);
        date=tomorrowAsString;
        //System.out.println(todayAsString);
        // System.out.println(tomorrowAsString);

    }
    public void clic(View view) {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment);
        dpb.show();

    }

    public  void time(View view)
    {

        TimePickerBuilder tpb = new TimePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment);
        tpb.show();

    }

    @Override
    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
        // mResultTextView.setText(getString(R.string.time_picker_result_value, String.format("%02d", hourOfDay), String.format("%02d", minute)));
        //Toast.makeText(this, String.format("%02d", hourOfDay)+String.format("%02d", minute), Toast.LENGTH_LONG ).show();
        time= String.format("%02d", hourOfDay)+":"+String.format("%02d", minute);
        Button bt = (Button) findViewById(R.id.button8);
        bt.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));
    }
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        // mResultTextView.setText(getString(R.string.date_picker_result_value, year, monthOfYear, dayOfMonth));
        monthOfYear++;
        String d= Integer.toString(dayOfMonth);
        String mon= Integer.toString(monthOfYear);
        String yea= Integer.toString(year);
        date= d+"/"+mon+"/"+yea;
        Date strDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (new Date().after(strDate))
        {
            Toast.makeText(this,"You Have Selected An Invalid Date" ,Toast.LENGTH_LONG).show();

        }

        else
        {
            Toast.makeText(this,"Date Selected :"+d+"-"+mon+"-"+yea ,Toast.LENGTH_LONG).show();


        }




    }


    public void find2(final String id )
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/PMORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String id2 = ord.getId();
                    if(id2.equals(id))
                    {
                        userSnapshot.getRef().child("status").setValue("Delivered");
                        userSnapshot.getRef().child("amountdelivered").setValue(amountrec);
                        userSnapshot.getRef().child("datedelivered").setValue(date);
                        userSnapshot.getRef().child("timedelivered").setValue(time);



                    }

                }

                new SweetAlertDialog(wareorderreceived.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Order Delivery Confirmed!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                move();

                            }
                        })
                        .show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void find(final String sto )
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass ord = userSnapshot.getValue(wareclass.class);


                        userSnapshot.getRef().child("stock").setValue(sto);






                }

                new SweetAlertDialog(wareorderreceived.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Order Delivery Confirmed!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                move();

                            }
                        })
                        .show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void move()
    {
        Intent in= new Intent( this, warepastorders.class);
        startActivity(in);
    }
    public void c(View view)
    {
        EditText ed= (EditText)findViewById(R.id.editText2) ;
        amountrec= ed.getText().toString();
        if (amountrec.equals(""))
        {
            Toast.makeText(this,"Enter an Amount",Toast.LENGTH_LONG).show();
        }
else {


            // Toast.makeText(this, amount+ newamount + amountrec +date+time, Toast.LENGTH_LONG ).show();
            find2(id);
            int amrec = Integer.parseInt(amountrec);
            int newstock = amrec + st;
            String nst = Integer.toString(newstock);
            find(nst);
        }



    }
    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/PMORDERS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering user1 = userSnapshot.getValue(ordering.class);

                    String idd = user1.getId();
                    if(id.equals(idd))
                    {
                        kname = user1.getKioskname();
                        newamount = user1.getNewamount();
                        amount = user1.getAmount();

                    }


                    // tost(sum);


                }
                TextView tv1 = (TextView)findViewById(R.id.textView4);
                TextView tv2 = (TextView)findViewById(R.id.textView5);
                TextView tv3 = (TextView)findViewById(R.id.textView6);
                tv1.setText("Kiosk Name:" + kname);
                tv2.setText("Amount you Requested: " +amount);
                tv3.setText("Amount They Sent: " +newamount);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void r() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);


                        stock= user1.getStock();
                        st=Integer.parseInt(stock);




                    // tost(sum);


                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }



}
