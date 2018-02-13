package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.billing;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.loginactivity;
import com.perchtech.humraz.farmkiosk.newlogin;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class adminlogin extends AppCompatActivity {
    ActionProcessButton btnSignIn;
    ArrayList<String> username = new ArrayList<String>();
    ;
    ArrayList<String> pass = new ArrayList<String>();
    ;
    String usernamee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        btnSignIn = (ActionProcessButton) findViewById(R.id.login);
        btnSignIn.setEnabled(false);
        Firebase.setAndroidContext(this);
        read();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, newlogin.class);
        startActivity(in);
    }
    public void click(View view) {
        EditText ed1 = (EditText) findViewById(R.id.user2);
        EditText ed2 = (EditText) findViewById(R.id.pass);
        usernamee = ed1.getText().toString();
        String passs = ed2.getText().toString();
        int flag = 0;
        for (int i = 0; i < username.size(); i++) {
            String us2 = username.get(i);
            String passs2 = pass.get(i);
            if (usernamee.equalsIgnoreCase("admin") && passs.equals(passs2))

            {

                Intent in = new Intent(this, homepage.class);
                startActivity(in);
                finish();
                flag = 1;
            }
           /* else {

            }*/

        }

        if (flag == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("No User Found Or Wrong Password")

                    .show();
        }

    }
    public void read() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);

                    String passs = user1.getPost();
                    String name = user1.getPass();
                    username.add(name);
                    pass.add(passs);


                    // tost(sum);


                }

                btnSignIn.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
