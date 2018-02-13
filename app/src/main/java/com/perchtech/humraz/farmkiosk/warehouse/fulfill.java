package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.ordering;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class fulfill extends AppCompatActivity implements DatePickerDialogFragment.DatePickerDialogHandler,TimePickerDialogFragment.TimePickerDialogHandler {
String stock;
    String kn;
    String am;
    String driver="Driver 1";
    String date;
    String id;
    String time;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill);
        Intent in = getIntent();
        kn= in.getStringExtra("kn");
        am= in.getStringExtra("am");
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");

        date= mon.format(new Date());
        time="9.00";
        id=in.getStringExtra("id");
        TextView tv= (TextView) findViewById(R.id.textView4);
        tv.setText("Kiosk : " + kn);
        EditText ed2 =(EditText) findViewById(R.id.editText2);
        ed2.setHint("Amount Needed:" + am);
        read();
    }
    public void  today(View view)
    {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

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
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

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

    public  void click(View view)
    {
        EditText ed1 =(EditText) findViewById(R.id.editText2);
      //  EditText ed2 =(EditText) findViewById(R.id.editText4);
        amount = ed1.getText().toString();

        //Toast.makeText(this, amount +time+date+ driver + kn, Toast.LENGTH_LONG ).show();
        find(id);


    }


    public void find(final String id )
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String id2 = ord.getId();
                    if(id2.equals(id))
                    {
                        userSnapshot.getRef().child("status").setValue("InTransit");
                        userSnapshot.getRef().child("newamount").setValue(amount);
                        userSnapshot.getRef().child("deliverydategiven").setValue(date);
                        userSnapshot.getRef().child("deliverytime").setValue(time);
                        userSnapshot.getRef().child("driver").setValue(driver);


                    }

                }
                int st= Integer.parseInt(stock);
                int am =Integer.parseInt(amount);
                st=st-am;
                String s = Integer.toString(st);
                f(s);
                new SweetAlertDialog(fulfill.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText("Order Can Now Be Shipped!")
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

    public void f(final String newstock) {
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
public void move()
{

    Intent in = new Intent(this, wareorders.class);
    startActivity(in);
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

    public void d1(View view){

        Button bt =(Button) findViewById(R.id.d2);
        bt.setEnabled(false);
        Toast.makeText(this, "You Selected Driver 1" ,Toast.LENGTH_LONG).show();
        driver="Driver 1";

    }
    public void d2(View view){

        Button bt =(Button) findViewById(R.id.d1);
        bt.setEnabled(false);
        Toast.makeText(this, "You Selected Driver 2" ,Toast.LENGTH_LONG).show();
        driver= "Driver 2";
    }
    public void read() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
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
