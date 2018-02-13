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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;

public class harvestmapdisease extends AppCompatActivity {
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
        tv.setText("Touch the trees that have Diseases(Red)");
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

                } else if (num[i][j] == 1) {
                    imageView.setImageResource(R.drawable.green);

                }
                else if (num[i][j] == 2) {
                    imageView.setImageResource(R.drawable.yellow);

                }
                final int p = i;
                final int k = j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        imageView.setImageResource(R.drawable.red);
                        num[p][k] = 3;
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
        Intent in = new Intent( this, farmerdetails.class);
        in.putExtra("name",name);
         startActivity(in);

        // Toast.makeText(this, "Stock Added On date " + date + "and time " + time, Toast.LENGTH_LONG).show();
    }

}

