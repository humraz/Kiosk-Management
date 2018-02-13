package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.admin.supplier;
import com.perchtech.humraz.farmkiosk.warehouse.bill;
import com.perchtech.humraz.farmkiosk.warehouse.paybill;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.util.ArrayList;
import java.util.List;

public class pmbill extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String stock;
    String ratenuts;
    String kadki;
    String nuts;
    String sodi;
    String kadkirate;
    String actualnuts;
    String subtotalnuts;
    int oldst;
    String subtotalkadki;
    String total;
    String stock2;
    String supplier;
    int nutss;
    int kadkii ;
    int ratenutss ;
    int sodii;
    int kadkiratee;
    List<String> sups = new ArrayList<String>();
    List<String> sups2 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmbill);
        ProcessButton b= (ProcessButton) findViewById(R.id.replosss);
        //  b.setEnabled(false);
        b.setVisibility(View.INVISIBLE);
        sups.add("Select A Supplier");
        read();
        readsups();







    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        supplier= parent.getItemAtPosition(pos).toString();
        // Toast.makeText(this, "Selected: " + supplier, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void ca(View view)
    {

        EditText ed = (EditText) findViewById(R.id.editText1);
        EditText ed2 = (EditText) findViewById(R.id.editText2);
        EditText ed3 = (EditText) findViewById(R.id.editText3);
        EditText ed4 = (EditText) findViewById(R.id.editText4);
        EditText ed5 = (EditText) findViewById(R.id.editText5);

        if(supplier.equals("Select A Supplier")||supplier.equals(""))
        {
            Toast.makeText(this, "Select A Supplier First", Toast.LENGTH_LONG).show();
        }
        else if(ed4.getText().toString().equals("") ||ed.getText().toString().equals(""))
        {
            Toast.makeText(this, "Some Required Fields Missing!", Toast.LENGTH_LONG).show();

        }

        else {


            nuts = ed.getText().toString();
            kadki = ed4.getText().toString();

            if (ed2.getText().toString().equals("")) {
                ratenuts = "25";
            } else {
                ratenuts = ed2.getText().toString();
            }
            if (ed3.getText().toString().equals("")) {
                sodi = "2";
            } else {
                sodi = ed3.getText().toString();
            }

            if (ed5.getText().toString().equals("")) {
                kadkirate = "3";
            } else {
                kadkirate = ed5.getText().toString();
            }


       /* ACTUAL NUTS = NUTS - (SODI X NUTS)/100
        SUBTOTAL (NUTS) = ACTUAL NUTS X RATE NUTS
        SUBTOTAL (KADKI) = KADKI X RATE KADKI
            TOTAL = SUBTOTAL (NUTS) + SUBTOTAL (KADKI)*/

            try {


                nutss = Integer.parseInt(nuts);
                kadkii = Integer.parseInt(kadki);
                ratenutss = Integer.parseInt(ratenuts);
                sodii = Integer.parseInt(sodi);
                kadkiratee = Integer.parseInt(kadkirate);
                int n = nutss + kadkii;
                oldst+=n;
                stock2 = Integer.toString(oldst);


                int actnuts = nutss - ((sodii * nutss) / 100);
                int subnuts = actnuts * ratenutss;
                int subkadki = kadkii * kadkiratee;
                int tot = subnuts + subkadki;


                total = Integer.toString(tot);
                actualnuts = Integer.toString(actnuts);
                subtotalnuts = Integer.toString(subnuts);
                subtotalkadki = Integer.toString(subkadki);

                TextView tv = (TextView) findViewById(R.id.textView1);
                TextView tv2 = (TextView) findViewById(R.id.textView2);
                TextView tv3 = (TextView) findViewById(R.id.textView3);
                TextView tv4 = (TextView) findViewById(R.id.textView4);
                tv.setText("Actual Nuts: " + actualnuts);
                tv2.setText("SubTotal(Nuts): " + subtotalnuts);
                tv3.setText("SubTotal(Kadki) :" + subtotalkadki);
                tv4.setText("Total: " + total);
                Toast.makeText(this, "Supplier Selected: " + supplier , Toast.LENGTH_LONG).show();
                ProcessButton b = (ProcessButton) findViewById(R.id.replosss);
                b.setVisibility(View.VISIBLE);
            }
            catch(NumberFormatException e) {
                Toast.makeText(this, "Wrong Integer Format" , Toast.LENGTH_LONG).show();
            } catch(NullPointerException e) {
                Toast.makeText(this, "Wrong Integer Format " , Toast.LENGTH_LONG).show();
            }


        }


    }


    public void ca2(View view)
    {


        Intent in = new Intent( this, pmpaybill.class);
        in.putExtra("supplier" ,supplier);
        in.putExtra("qnty",total);
        in.putExtra("stock", stock2);
        startActivity(in);





    }






    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementstock/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                    stock = user1.getStock();

                    oldst = Integer.parseInt(stock);


                    // tost(sum);


                }
                TextView tv= (TextView) findViewById(R.id.textView33);
                TextView tv2= (TextView) findViewById(R.id.textView);
                tv2.setText("Available : " +stock);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void readsups() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/suppliers/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    com.perchtech.humraz.farmkiosk.admin.supplier user1 = userSnapshot.getValue(supplier.class);

                    String a = user1.getSupid();
                    sups.add(a);

                }
                final Spinner spinner = (Spinner) findViewById(R.id.spinner);

                // Spinner click listener

                // Spinner Drop down elements


                // Creating adapter for spinner



                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(pmbill.this, android.R.layout.simple_spinner_item, sups);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);


                spinner.setOnItemSelectedListener(pmbill.this);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}

