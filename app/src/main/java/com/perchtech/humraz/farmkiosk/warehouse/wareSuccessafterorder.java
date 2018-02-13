package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.pm.Main2Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class wareSuccessafterorder extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successafterorder);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Successfully Added Order To Server")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        move();

                    }
                })
                .show();

    }
    public void move()
    {
        Intent in = new Intent(this,waremenu.class);
        startActivity(in);
    }



}
