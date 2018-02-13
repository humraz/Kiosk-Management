package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.perchtech.humraz.farmkiosk.Adminmakekiosk;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.Stocktakingfirst;
import com.perchtech.humraz.farmkiosk.billing;
import com.perchtech.humraz.farmkiosk.salesactivity;

public class menuadmin extends AppCompatActivity {
    static int[] imageResources = new int[]{
            R.drawable.invoice,
            R.drawable.order,
            R.drawable.exit,
            R.drawable.checked




    };
    static int[] Strings = new int[]{
            R.string.cashflow,
            R.string.sales,
            R.string.make,
            R.string.sales2






    };
    static int imageResourceIndex = 0;
    static int str = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadmin);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 0) {
                                start(index);

                            }
                            if (index == 1) {
                                sales(index);
                            }
                            if (index == 2) {
                                stock(index);

                            }
                            if (index == 3) {
                                sales(index);
                            }

                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);

        }
    }
    public void start(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, cashflow.class);
        startActivity(in);
    }
    public void stock(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, Adminmakekiosk.class);
        startActivity(in);
    }
    public void sales(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, createsupplier.class);
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
}
