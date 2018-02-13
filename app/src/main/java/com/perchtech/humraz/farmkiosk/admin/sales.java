package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
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
import com.perchtech.humraz.farmkiosk.Adminmakekiosk;
import com.perchtech.humraz.farmkiosk.Manifest;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.kiosksalesdaily;
import com.perchtech.humraz.farmkiosk.loginactivity;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class sales extends ActionBarActivity implements DatePickerDialogFragment.DatePickerDialogHandler{
    ArrayList<String> kiosks = new ArrayList<>();
    ArrayList<String> dateee = new ArrayList<>();
    ArrayList<String> done = new ArrayList<>();
    ArrayList<String> phno = new ArrayList<>();
    HashMap<String, String> h = new HashMap<String, String>();

    ArrayList<Card> cards ;
int sum=0;
    static int[] imageResources = new int[]{
            R.drawable.invoice,
            R.drawable.order,
            R.drawable.exit,
            R.drawable.plus




    };
    static int[] Strings = new int[]{
            R.string.cashflow,
            R.string.sales,
            R.string.make,
            R.string.sales2





    };
    static int imageResourceIndex = 0;
    static int str = 0;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Firebase.setAndroidContext(this);
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
                                sale(index);
                            }

                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);

        }
        readkiosks();




    }
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        // mResultTextView.setText(getString(R.string.date_picker_result_value, year, monthOfYear, dayOfMonth));
        String date;
        monthOfYear++;
        String d= Integer.toString(dayOfMonth);
        if(d.length()==1)
            d="0"+d;

        String mon= Integer.toString(monthOfYear);
        if(mon.length()==1)
            mon="0"+mon;
        String yea= Integer.toString(year);
        date= d+mon+yea;
        Toast.makeText(this,"Date Selected :"+date ,Toast.LENGTH_LONG).show();
        chart(date);
    }
    public void clic(View view) {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment);
        dpb.show();

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
        Intent in = new Intent( this, sales.class);
        startActivity(in);
    }
    public void sale(int pos)
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
    public void chart(final String date) {

cards= new ArrayList<>();
        for (int i = 0; i < kiosks.size(); i++) {
            sum=0;
            c=0;
            System.out.println("checkhereeee sum = 0 "+sum);
            final String kioskid = kiosks.get(i);

            final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/CLOSINGBALANCE/" + kioskid);



            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot usersSnapshot) {
                    for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                        kiosksalesdaily sale = userSnapshot.getValue(kiosksalesdaily.class);
                        String d = sale.getTime();

                        String amount = sale.getAmount();
                        int am = Integer.parseInt(amount);
                        sum+=am;
                        c++;

                        if (d.equals(date)) {
                            done.add(kioskid);
                            int av= sum/c;

                         //   System.out.println("checkhereeeee" + kioskid);
                            System.out.println("sum for closing kiosk" + sum + " "+av);
                            Card card = new Card(getApplicationContext(),R.layout.cardd);
                            CardHeader header = new CardHeader(getApplicationContext());
                            header.setTitle(kioskid);

                            final String num = h.get(kioskid);
                           header.setOtherButtonVisible(true);
                            header.setOtherButtonDrawable(R.drawable.phone);
                            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                                @Override
                                public void onButtonItemClick(Card card, View view) {
                                    startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num)));

                                }
                            });
                            int r = (int) (0.79 * av);
                            int o = (int) (0.8 * av);
                            int w = (int) av;
                            int g = (int) (1.2 * av);
                            String t;

                            if (am < r) {
                                card.setBackgroundResourceId(R.color.red_error);
                                t = "Sales is less than 20% of the average of  the sales on the previous four Wednesdays.";
                            }
                            else if (am < o) {
                                card.setBackgroundResourceId(R.color.holo_orange_light);
                                t = "Sales is less than 20% of the average of  the sales on the previous four Wednesdays.";
                            }
                            else if (am < w) {
                                card.setBackgroundResourceId(R.color.bpWhite);
                                t = "Sales is within +/- 20% of the average of  the sales on the previous four Wednesdays.";
                            }else if(am<g) {
                                card.setBackgroundResourceId(R.color.holo_green_light);
                                t = "Sales is more than 20% of the average of  the sales on the previous four Wednesdays.\n";
                            } else {
                                card.setBackgroundResourceId(R.color.green_complete);
                                t = "Sales is more than 20% of the average of  the sales on the previous four Wednesdays. Also it is the highest of all the five Wednesdays.\n";
                            }
                            card.setTitle("Total Sales For The Day: "+amount +"\n" +t);

                            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                                @Override
                                public void onButtonItemClick(Card card, View view) {
                                    startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num)));

                                }
                            });
                            card.setOnClickListener(new Card.OnCardClickListener() {
                                @Override
                                public void onClick(Card card, View view) {
                                    String a=card.getTitle();
                                    Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                                }
                            });
                            card.addCardHeader(header);
                            cards.add(card);

                        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(),cards);

                        CardListView listView = (CardListView) findViewById(R.id.myList);
                        if (listView!=null){
                            listView.setAdapter(mCardArrayAdapter);
                        }

                        }



                    }




                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });

        }
ofline();






    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, homepage.class);
        startActivity(in);
    }
public void ofline()
{
    final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
    //Value event listener for realtime data update
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot usersSnapshot) {
            for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                String k = user1.getPass().toString();
                String l = user1.getLoggedin().toString();
                String time =user1.getIntime().toString();
                String date =user1.getIndate().toString();

                if (l.equals("false")) {

                    if (done.contains(k)) {

                    } else if(k.equals("Admin"))
                    {}else{
                        Card card = new Card(getApplicationContext(), R.layout.cardd);
                        CardHeader header = new CardHeader(getApplicationContext());
                        header.setTitle(k);
                        card.setTitle("Kiosk Not logged In For The Day Yet! \nLast LogIn Date: "+date+ "\nLast Log In Time: " +time);
                        header.setOtherButtonVisible(true);

                        final String num = h.get(k);
                            card.setBackgroundResourceId(R.color.grey_title);
                        header.setOtherButtonVisible(true);
                      header.setOtherButtonDrawable(R.drawable.phone);
                        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                            @Override
                            public void onButtonItemClick(Card card, View view) {
                                startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num)));

                            }
                        });
                        card.setOnClickListener(new Card.OnCardClickListener() {
                            @Override
                            public void onClick(Card card, View view) {
                                String a=card.getTitle();
                                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                            }
                        });
                        card.addCardHeader(header);
                        cards.add(card);

                        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(),cards);

                        CardListView listView = (CardListView) findViewById(R.id.myList);
                        if (listView!=null){
                            listView.setAdapter(mCardArrayAdapter);
                        }

                    }


                }
                if(l.equals("true"))
               // if(l.equals("true"))
                {
                    Card card = new Card(getApplicationContext(),R.layout.cardd);
                    CardHeader header = new CardHeader(getApplicationContext());
                    header.setTitle(k);
                    card.setTitle("Kiosk logged In For The Day And Sales Is Going On.\nLogIn Time: "+time+"\nLogIn Date: " +date);


                    card.setBackgroundResourceId(R.color.blue_normal);
                    header.setOtherButtonVisible(true);
                    final String num = h.get(k);
                    header.setOtherButtonDrawable(R.drawable.phone);
                    header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                        @Override
                        public void onButtonItemClick(Card card, View view) {
                            startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num)));

                        }
                    });
                    card.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            String a=card.getTitle();
                            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                        }
                    });
                    card.addCardHeader(header);
                    cards.add(card);

                    CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(),cards);

                    CardListView listView = (CardListView) findViewById(R.id.myList);
                    if (listView!=null){
                        listView.setAdapter(mCardArrayAdapter);
                    }
                }


            }


        }


        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }
    });

}

  /*  public void cardpop()
    {
        for(int i=0;i<finallist.size();i++)
        {
            Card card = new Card(getApplicationContext());
            final String name= finallist.get(i);
            final String jame=finallist2.get(i);
            System.out.println("checkhereeeee " +finallist.size());
            System.out.println("checkhereeeee " +jame);


            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame);
            //Add Header to card

            System.out.println("checkhereeeee" + name);


            // card.setBackgroundResourceId(R.color.colorPrimary);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    String a=card.getTitle();
                    Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                }
            });
            card.addCardHeader(header);
            cards.add(card);
        }
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(),cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }*/

    public void readkiosks() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String k = user1.getPass().toString();
                    if(k.equalsIgnoreCase("admin"))
                        continue;
                    kiosks.add(k);
                    dateee.add(user1.getIndate() + " " + user1.getOuttime());
String num = user1.getPhonenumber();
                    h.put(k,num);
                }
                SimpleDateFormat mon = new SimpleDateFormat("ddMMyyyy");
                final String date = mon.format(new Date());
                chart(date);


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
