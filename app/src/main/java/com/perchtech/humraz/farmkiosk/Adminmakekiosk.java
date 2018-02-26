package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.perchtech.humraz.farmkiosk.admin.menuadmin;
import com.perchtech.humraz.farmkiosk.admin.sales;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.perchtech.humraz.farmkiosk.R.string.cashflow;


public class Adminmakekiosk extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_adminmakekiosk);
    Firebase.setAndroidContext(this);
  }

  public void click(View v)
  {


    EditText ed = (EditText) findViewById(R.id.fname);
    EditText ed2 = (EditText) findViewById(R.id.pass);
    EditText ed3 = (EditText) findViewById(R.id.phn);
    String ph= ed3.getText().toString();
    String kname = ed.getText().toString();
    String pass = ed2.getText().toString();
    Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");

    //Getting values to store

    //Creating Person object
    kioskmake person = new kioskmake();

    //Adding values
    person.setPost(pass);
    person.setDiff("0");
    person.setPass(kname);
    person.setOpeningbal("0");
    person.setStock("0");
    person.setOuttime("3:00");
    person.setIntime("3:00");
    person.setIndate("3/3/3");
      person.setYestopeningbal("0");
    person.setYeststock("0");
    person.setE("0");

    person.setRate("30");
    person.setPhonenumber(ph);
    person.setLoggedin("false");
    person.setSalestatus("white");
    //Storing values to firebase
    ref.push().setValue(person);

    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Success!")
            .setContentText("Successfully Created Kiosk")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
              @Override
              public void onClick(SweetAlertDialog sDialog) {
                Intent in = new Intent(Adminmakekiosk.this, sales.class);
                startActivity(in);

              }
            })
            .show();
  }


}