package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class successafterbilling extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successafterbilling);


        move();
    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, billing.class);
        startActivity(in);
    }
public void move()
{
    Intent in = new Intent(this,billing.class);
    finish();
    startActivity(in);
}

}
