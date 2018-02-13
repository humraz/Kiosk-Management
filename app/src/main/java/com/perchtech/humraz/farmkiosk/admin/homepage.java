package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.ProcessButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.perchtech.humraz.farmkiosk.Adminmakekiosk;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.warehouse.losslog;
import com.perchtech.humraz.farmkiosk.warehouse.reportloss;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class homepage extends AppCompatActivity {
    ArrayList<Card> cards  = new ArrayList<Card>();
    ArrayList<String > ks  = new ArrayList<String >();
    ArrayList<String> stock =new ArrayList<>();
    ArrayList<String> order =new ArrayList<>();
    ArrayList<String> delay =new ArrayList<>();
    ArrayList<String> transloss =new ArrayList<>();
    static int[] imageResources = new int[]{
            R.drawable.loss,
            R.drawable.board,
            R.drawable.invoice,
            R.drawable.order,
            R.drawable.food,
            R.drawable.plus,
            R.drawable.update,






    };
    static int[] Strings = new int[]{
            R.string.loss,

            R.string.salechart,

            R.string.cashflow,
            R.string.sales,
            R.string.make,
            R.string.sales2,
            R.string.reset,






    };
    static int imageResourceIndex = 0;
    static int str = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Firebase.setAndroidContext(this);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 0) {

                                Intent in = new Intent(homepage.this, losslog.class);
                                startActivity(in);
                            }
                            if (index == 1) {
                                Intent in = new Intent(homepage.this, salesgraph.class);
                                startActivity(in);
                            }
                            if (index == 2) {

                                start(index);
                            }
                            if (index == 3) {
                                sales(index);
                            }
                            if (index == 4) {

                                stock(index);
                            }
                            if (index == 5) {
                                sale(index);
                            }
                            if (index == 6) {
                                saless(index);
                            }

                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);

        }
       // checkopenks();
        ofline();

    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, adminlogin.class);
        startActivity(in);
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
    public void saless(int pos)
    {
        //   Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
        Intent in = new Intent( this, resetactivity.class);
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

    public void checkopenks() {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
 final        TextView tv2 =(TextView) findViewById(R.id.textView);
        //Value event listener for realtime data update
        final int[] count = {0};
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);
                    String amount = sale.getLoggedin();
                    String k = sale.getPass();
                    int st= Integer.parseInt(sale.getStock().toString());
                    if (st<5)
                    {
                        stock.add("Kiosk " +k + " Has stock of "+ Integer.toString(st) + " Units Only");
                    }
                    // hi(amount);
                    if (amount.equals("true"))
                    {
                        count[0] +=1;
                    }
                }
                tv2.setText(count[0]+" Kiosks Currently Open.");
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public  void cardd2()
    {
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Kiosks With Critical Stock");
        String k=stock.get(0);
        for(int i=1;i<stock.size();i++)
        {
            k=k+ "\n" +stock.get(i);
        }
        card.setTitle(k);
        header.setOtherButtonVisible(true);


        //card.setBackgroundResourceId(R.color.grey_title);
        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }


    public  void card3()
    {
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Orders That Were Declined Today");
        String k=order.get(0);
        for(int i=1;i<order.size();i++)
        {
            k=k+ "\n" +order.get(i);
        }
        card.setTitle(k);
        header.setOtherButtonVisible(true);


        //card.setBackgroundResourceId(R.color.grey_title);
        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }



    public  void cardd()
    {
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Kiosks That Have Not logged In Yet");
        String k=ks.get(0);
        for(int i=1;i<ks.size();i++)
        {
            k=k+ "\n" +ks.get(i);
        }
        card.setTitle(k);
        header.setOtherButtonVisible(true);


       // card.setBackgroundResourceId(R.color.grey_title);
        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
    public  void cardd4()
    {
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Kiosks That Have Delayed Orders");
        String k=delay.get(0);
        for(int i=1;i<delay.size();i++)
        {
            k=k+ "\n" +delay.get(i);
        }
        card.setTitle(k);
        header.setOtherButtonVisible(true);



        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
    public  void c()
    {
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Orders With Transit Loss");
        String k=transloss.get(0);
        for(int i=1;i<transloss.size();i++)
        {
            k=k+ "\n" +transloss.get(i);
        }
        card.setTitle(k);
        header.setOtherButtonVisible(true);



        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
    public void ofline() {
        SimpleDateFormat mon = new SimpleDateFormat("dd/MM/yyyy");
        final String d = mon.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake user1 = userSnapshot.getValue(kioskmake.class);
                    String k = user1.getPass().toString();
                    String l = user1.getLoggedin().toString();
                    String time = user1.getIntime().toString();
                    if (time.length()>5)
                    time=time.substring(0,5);
                    String date = user1.getIndate().toString();


                    DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    DateFormat outputFormat = new SimpleDateFormat("dd EEEE MMM");
                    String inputDateStr=date;
                    Date date2 = null;
                    try {
                        date2 = inputFormat.parse(inputDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outputDateStr = outputFormat.format(date2);



                    if (l.equals("false")) {


                        if (k.equals("Admin")|| date.equals(d)) {
                        } else {
                            ks.add(k + "  Last - " +time + " " + outputDateStr);

                        }


                    }

                }
                if (ks.size()!=0)
                cardd();
                if (stock.size()!=0)
                cardd2();
                wareread();
                r3();

            }



            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void r3()
    {
        SimpleDateFormat mon = new SimpleDateFormat("dd-MM-yyyy");
        final String date = mon.format(new Date());
        SimpleDateFormat mon3 = new SimpleDateFormat("d/M/yyyy");
        final String date3 = mon3.format(new Date());
        SimpleDateFormat mon2 = new SimpleDateFormat("d/M/yyyy");
        final String date2 = mon2.format(new Date());
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String pending = ord.getStatus();
                    //   Toast.makeText(pastorders.this, ord.getKioskname(),Toast.LENGTH_SHORT).show();
                    String id =ord.getId();
                   id=id.replace(ord.getKioskname(),"");
                    if (id.length()>15) {
                        id = id.substring(1, 12);
                    }
                    String strDate= ord.getDateplaced();
                    String d= ord.getDaterequired();

                    if (strDate.equals(date))
                    {
                        if (pending.equals("Declined") || pending.equals("Cancelled"))
                            order.add("Kiosk " +ord.getKioskname() +" With Order id " +id);
                    }
                    if (pending.equals("InTransit") || pending.equals("Accepted"))
                    {
                        Date sD = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                        try {
                            sD = sdf.parse(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (new Date().after(sD))
                        {

                           delay.add("Kiosk " +ord.getKioskname() +" With Order Id "+ id);
                        }
                    }
                    if (pending.equals("Delivered"))
                    {
                        String datedeliv =ord.getDatedelivered();
                    if (datedeliv.equals(date3))
                    {
                        int a= Integer.parseInt(ord.getAmount().toString());
                        int b =Integer.parseInt(ord.getAmountdelivered().toString());
                        if(b!=a)
                            transloss.add("Kiosk " +ord.getKioskname() +" With Order Id "+ id);

                    }
                    }
                }
                if (order.size()!=0)
                card3();
                if (delay.size()!=0)
                    cardd4();
                if (transloss.size()!=0)
                    c();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public  void stockcard(int n, String l)
    {   String  s=Integer.toString(n);
        Card card = new Card(getApplicationContext(), R.layout.cardd);
        CardHeader header = new CardHeader(getApplicationContext());
        header.setTitle("Warehouse Notification!");


        card.setTitle("The Stock In the warehouse is below buffer threshold. Stock is currently " + s + " And the loss is currently " + loss  );
        header.setOtherButtonVisible(true);


      //  card.setBackgroundResourceId(R.color.grey_title);
        header.setOtherButtonVisible(true);


        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                String a = card.getTitle();
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);
        cards.add(card);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getApplicationContext(), cards);

        CardListView listView = (CardListView) findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }
    int st;
    String loss;

    public void wareread() {

        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/warehouse/");
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);
                    st = Integer.parseInt(user1.getStock().toString());
                     loss =user1.getLoss();
                }
                if (st<10000)
                {
                    stockcard(st, loss);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    }
