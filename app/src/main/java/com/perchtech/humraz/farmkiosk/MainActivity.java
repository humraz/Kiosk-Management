package com.perchtech.humraz.farmkiosk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fasterxml.jackson.databind.node.IntNode;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Intent in = new Intent(this, loginactivity.class);
       // startActivity(in);
       // finish();

    }
public void bill(View view)
{
    Intent in = new Intent(this, billing.class);
 startActivity(in);
}
    public void ware(View view)
    {
        Intent in = new Intent(this, warehouseconfirmorder.class);
        startActivity(in);
    }
    public void sto(View view)
    {
        Intent in = new Intent(this, Stocktakingfirst.class);
        startActivity(in);
    }
    public void k(View view)
    {
        Intent in = new Intent(this, orderingfirst.class);
        startActivity(in);
    }
    public void k1(View view)
    {
        Intent in = new Intent(this, salesactivity.class);
        startActivity(in);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
