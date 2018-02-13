package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskhomepage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class wareorderingfirst extends ActionBarActivity implements DatePickerDialogFragment.DatePickerDialogHandler{
String orderamount;
    String date;
    String d2;
    String urgent= "Not Urgent";
    String stock;
   // TextView mResultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderingfirst);
        ToggleButton tg= (ToggleButton) findViewById(R.id.toggleButton);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date=sdf.format(new Date());
        tg.setChecked(false);
        Firebase.setAndroidContext(this);
        read();
       // mResultTextView= (TextView)findViewById(R.id.date);


    }
    public void read() {
        SharedPreferences prefs3= getSharedPreferences("kioskname",MODE_PRIVATE);
        final  String  k= prefs3.getString("kname", null);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);


                        stock= user1.getStock();





                    // tost(sum);


                }
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Available Stock: "+ stock);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

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
            ProcessButton pd = (ProcessButton)findViewById(R.id.confirmorder);
            pd.setEnabled(false);
        }

            else
        {
            Toast.makeText(this,"Date Selected :"+d+"-"+mon+"-"+yea ,Toast.LENGTH_LONG).show();
            ProcessButton pd = (ProcessButton)findViewById(R.id.confirmorder);
            pd.setEnabled(true);
        }




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
    public void k(View view)
    {ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        if (toggle.isChecked()) {
            // The toggle is enabled
            urgent = "Urgent";
            Toast.makeText(this, "State is Urgent", Toast.LENGTH_LONG).show();
        } else {
            // The toggle is disabled
            urgent = "Not Urgent";
            Toast.makeText(this, "State is Not Urgent", Toast.LENGTH_LONG).show();
        }


    }


    public void clic(View view) {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment);
        dpb.show();

    }
    public void clicc(View view) {
       Intent in = new Intent(this, warepastorders.class);
        startActivity(in);

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, waremenu.class);
        startActivity(in);
    }
public void confirm(View view)
{
    EditText ed= (EditText) findViewById(R.id.order);
   // EditText ed2= (EditText) findViewById(R.id.date);
    orderamount= ed.getText().toString();
    if (orderamount.equals(""))
    {
       Toast.makeText(this, "Enter An Amount First",Toast.LENGTH_LONG).show();
    }
    else
    {
        Intent in = new Intent(this, wareordersecond.class);
        in.putExtra("ordamount", orderamount);
        in.putExtra("date", date);
        in.putExtra("urgent", urgent);
        startActivity(in);
    }
   // date= ed2.getText().toString();

}

}
