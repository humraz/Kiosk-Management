package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class createsupplier extends AppCompatActivity {
public String name;
    public String phno;
    public String loc;
    public String supid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsupplier);
        Firebase.setAndroidContext(this);

    }

    public void c(View view)
    {
        EditText ed1= (EditText) findViewById(R.id.editText1);
        EditText ed2= (EditText) findViewById(R.id.editText2);

        EditText ed3= (EditText) findViewById(R.id.editText3);
        name= ed1.getText().toString();
        phno= ed3.getText().toString();
        loc=ed2.getText().toString();
        supid= name.substring(0,4) + loc.substring(0,3);




        write(supid);




    }

    public void write(String supid)
    {
        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/suppliers/");
        supplier person = new supplier();
        person.setPhno(phno);
        person.setLocation(loc);
        person.setSupname(name);
        person.setSupid(supid);


        ref.push().setValue(person);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Created id:" + supid)
                .setContentText("Proceed?")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent in = new Intent(createsupplier.this, sales.class);
                        startActivity(in);
                        Toast.makeText(createsupplier.this, "Added Supplier To Database", Toast.LENGTH_LONG).show();
                    }
                })
                .show();






    }

}
