package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.perchtech.humraz.farmkiosk.R;

public class harvestmapfirst extends AppCompatActivity {
EditText ed1;
    EditText ed2;
    EditText ed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestmapfirst);
        ed1=(EditText)findViewById(R.id.rows);
        ed2=(EditText)findViewById(R.id.cols);
        ed3=(EditText)findViewById(R.id.name);


    }
    public void  con(View view)
    {
        String a= ed1.getText().toString();
        String b= ed2.getText().toString();
        String c= ed3.getText().toString();
        if (a.equals("")||b.equals("")||c.equals(""))
        {
            Toast.makeText(this, "Some Fields Empty" ,Toast.LENGTH_LONG).show();
        }
        else

        {


            Intent in = new Intent(this, harvestmapsecond.class);
            in.putExtra("a", a);
            in.putExtra("b", b);
            in.putExtra("c", c);
            startActivity(in);
        }

    }
    public void click(View view)
    {

        Intent in = new Intent(this, viewfarms.class);
        startActivity(in);



    }
}
