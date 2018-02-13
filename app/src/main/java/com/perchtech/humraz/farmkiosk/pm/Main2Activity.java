package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.Stocktakingfirst;
import com.perchtech.humraz.farmkiosk.billing;
import com.perchtech.humraz.farmkiosk.newlogin;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
TextView tv;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
SharedPreferences pref3=null;
    static int[] imageResources = new int[]{


            R.drawable.order,
            R.drawable.invoice,
            R.drawable.loss,
            R.drawable.hist,

            R.drawable.loss,





    };
    static int[] Strings = new int[]{


            R.string.bills,
            R.string.order,
            R.string.los,

            R.string.billhistory,

            R.string.harvest,








    };
    static int imageResourceIndex = 0;
    static int str = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView) findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView29);
        tv3=(TextView)findViewById(R.id.textView2);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView5);
        tv6=(TextView)findViewById(R.id.textView6);


        pref3=getSharedPreferences("log",MODE_PRIVATE);
        String name=pref3.getString("pmname",null);
        SimpleDateFormat mon = new SimpleDateFormat("EEEE dd MMM yyyy");
        tv3.setText("PM Id: " +name);
        String month = mon.format(new Date());
        tv2.setText(month);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 0) {
                                stock(index);

                            }
                            if (index == 1) {
                                ord(index);

                            }
                            if (index == 2) {
                                loss(index);

                            }
                            if (index == 3) {
                                liss(index);

                            }

                            if (index == 4) {
                                lis(index);

                            }



                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);
            read();
        }
    }
    String stock;
    String loss;
    public void read() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementstock/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);
                    stock = user1.getStock();
                    loss=user1.getLoss();

                }
                tv4.setText("Available Stock(Units): " +stock);
                tv5.setText("Current Loss(Units): " +loss);
read3();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    String date;
    String reason;
    public void read3() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementloss/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {


                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);
                    date=user1.getDate();
                    reason=user1.getComment();
                    loss=user1.getLoss();

                }
                tv6.setText("Last Loss Reported On " +date +"\nLoss Reported "+loss+"\nReason Was "+reason);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        read();
        read3();
    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, pmlogin.class);
        startActivity(in);
    }
    public static int getString() {
        if (str >= Strings.length) str = 0;
        return Strings[str++];
    }
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    public void ord(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, pmwareorders.class);
        startActivity(in);
    }
    public void liss(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, pmbillhistory.class);
        startActivity(in);
    }
    public void lis(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, harvestmapfirst.class);
        startActivity(in);
    }
    public void loss(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, pmloss.class);
        startActivity(in);
    }
    public void stock(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, pmbill.class);
        startActivity(in);
    }
}
