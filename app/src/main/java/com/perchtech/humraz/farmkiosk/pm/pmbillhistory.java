package com.perchtech.humraz.farmkiosk.pm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.warehouse.bills;

import java.util.ArrayList;
import java.util.Collections;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class pmbillhistory extends AppCompatActivity {
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmbillhistory);
        Firebase.setAndroidContext(this);
        read();
    }


    public void read()
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/procurementpaststock/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    bills ord = userSnapshot.getValue(bills.class);

                    String id = ord.getId();
                    id= id.substring(0,13);
                    id="Bill ID# : " +id;
                    String time = ord.getTime();
                    String date = ord.getDate();
                    String supplier = ord.getSupplier();
                    String qnty = ord.getQnty();
                    String paymentmethod = ord.getPaymentmethod();


                    cardslis.add( id);


                    StringBuilder finalt = new StringBuilder();

                    finalt.append("Time:  " + time + "  Date:  " + date);

                    finalt.append("\nSupplier: " + supplier);
                    finalt.append("\nQuantity: " + qnty);
                    finalt.append("\nPayment Method: " + paymentmethod);
                    finalt.append("\nAmount: " + ord.getAmount());
                    String f = String.valueOf(finalt);
                    cardsdes.add( f);

                }
                cardpop();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void cardpop()
    {
        Collections.reverse(cardsdes);
        Collections.reverse(cardslis);

        for(int i=0;i<cardsdes.size();i++)
        {
            Card card = new Card(getApplicationContext(), R.layout.cardd);
            final String name= cardslis.get(i);
            final String jame=cardsdes.get(i);



            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame);
            //Add Header to card


            // card.setBackgroundResourceId(R.color.colorPrimary);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {

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
    }


}
