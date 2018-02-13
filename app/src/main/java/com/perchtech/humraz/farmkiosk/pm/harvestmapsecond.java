package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class harvestmapsecond extends AppCompatActivity {
    int num[][]= new int[40][40];
int a;
    int b;
    String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestmapsecond);
        Intent in = getIntent();
        TextView tv= (TextView) findViewById(R.id.textView14);
        tv.setText("Touch the areas with trees (Green)");
        a =Integer.parseInt(in.getStringExtra("a"));
        b =Integer.parseInt(in.getStringExtra("b"));
        c=in.getStringExtra("c");

        int numRow =a;
        int numCol = b;
        for (int i=0;i<a;i++)
        {
            for (int j=0;j<b;j++)
            {
                num[i][j]=0;
            }
        }
        TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

        for(int i = 0; i < numRow; i++) {
            HorizontalScrollView HSV = new HorizontalScrollView(this);
            HSV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            //tblRow.setBackgroundResource(R.color.gray_btn_bg_color);

            for(int j = 0; j < numCol; j++) {
                final ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.black);
                final int p=i;
                final int k=j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        imageView.setImageResource(R.drawable.green);
                        num[p][k]=1;
                    }});
                imageView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        imageView.setImageResource(R.drawable.black);
                        num[p][k]=0;
                        return true;
                    }
                });

                tblRow.addView(imageView,j);
            }

            HSV.addView(tblRow);
            tblLayout.addView(HSV, i);
        }
    }

    public void ch(View view)
    {
        String ab="";
        for (int i=0;i<a;i++)
        {
            for (int j=0;j<b;j++)
            {
                int b= num[i][j];
                ab=ab+Integer.toString(b);
            }
            ab=ab+"\n";
        }

        Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/harvestmap/");
        farmerclass person = new farmerclass();
        person.setHarvestmap1(num);

        person.setFarmname(c);
       // person.setA(num);
        ref.push().setValue(person);
        Intent in = new Intent( this, harvestmapwater.class);
        in.putExtra("name",c);
        in.putExtra("r",a);
        in.putExtra("c",b);

        startActivity(in);

       // Toast.makeText(this, "Stock Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
    }






}
