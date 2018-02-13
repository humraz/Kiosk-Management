package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.os.Handler;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class harvestmapwater extends AppCompatActivity {
String map1;
    int numRow=10;
    int numCol=10;
    int [][] num = new int[40][40];
    String name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestmapsecond);
        Intent in = getIntent() ;
        TextView tv= (TextView) findViewById(R.id.textView14);
        tv.setText("Touch the trees that have water stress (Yellow)");
        name = in .getStringExtra("name");
        numRow=in.getIntExtra("r",0);
        numCol=in.getIntExtra("c",0);
      //  Toast.makeText(this, Integer.toString(numRow) + Integer.toString(numCol),Toast.LENGTH_LONG).show();



        Firebase.setAndroidContext(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                read();
                /// Toast.makeText(billing.this, "S" , Toast.LENGTH_LONG).show();


            }
        }, 800);

    }

    public void read() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/harvestmap/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    farmerclass user1 = userSnapshot.getValue(farmerclass.class);

                    num=user1.getHarvestmap1();
                }
plotfirst();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


   /* public void getfromarray()
    {
        List<String> myList = new ArrayList<String>(Arrays.asList(map1.split("\n")));
        r=myList.size();
        String t= myList.get(0);
        c=t.length();
        Toast.makeText(this, Integer.toString(c)+ Integer.toString(r),Toast.LENGTH_LONG).show();

        String ab="";
       for (int i=0;i<r;i++)
        {
            String temp= myList.get(i);
            List<String> row = new ArrayList<String>(Arrays.asList(temp.split("")));
            for(int p=0;p<row.size();p++)
            {
               // if (row.get(p)!="")
                  *//*  if (row.get(p).equals("0"))
                    num[i][p]=0;
                    else if (row.get(p).equals("1"))
                        num[i][p]=1;
*//*
                ab=ab+row.get(p);
            }
           ab=ab+"\n";
        }
        Toast.makeText(this, ab,Toast.LENGTH_LONG).show();
        numRow=r;
        numCol=c;
        plotfirst()
    }
*/
    public void plotfirst()
    {
        TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

        for(int i = 0; i < numRow; i++) {
            HorizontalScrollView HSV = new HorizontalScrollView(this);
            HSV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            //tblRow.setBackgroundResource(R.color.gray_btn_bg_color);

            for (int j = 0; j < numCol; j++) {
                final ImageView imageView = new ImageView(this);
                if (num[i][j] == 0) {
                    imageView.setImageResource(R.drawable.black);
                   // imageView.setTag(0);


                } else {
                    imageView.setImageResource(R.drawable.green);
                   // imageView.setTag(1);

                }
                final int p = i;
                final int k = j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                            //Toast.makeText(harvestmapwater.this, "green",Toast.LENGTH_LONG).show();

                        imageView.setImageResource(R.drawable.yellow);
                        num[p][k] = 2;
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        imageView.setImageResource(R.drawable.green);
                        num[p][k] = 1;
                        return true;
                    }
                });

                tblRow.addView(imageView, j);
            }

            HSV.addView(tblRow);
            tblLayout.addView(HSV, i);
        }
    }



    public void ch(View view)
    {
        String ab="";
        for (int i=0;i<numCol;i++)
        {
            for (int j=0;j<numRow;j++)
            {
                int b= num[i][j];
                ab=ab+Integer.toString(b);
            }
            ab=ab+"\n";
        }

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/harvestmap/");
        //Value event listener for realtime data update
        final String finalAb = ab;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    farmerclass user1 = userSnapshot.getValue(farmerclass.class);
                    if (user1.getFarmname().equals(name))
                    {
                        userSnapshot.getRef().child("harvestmap1").setValue(num);

                    }


                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        Intent in = new Intent( this, harvestmapdisease.class);
        in.putExtra("name",name);
        in.putExtra("r",numRow);
        in.putExtra("c",numCol);
        startActivity(in);
        // Toast.makeText(this, "Stock Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
    }

}
