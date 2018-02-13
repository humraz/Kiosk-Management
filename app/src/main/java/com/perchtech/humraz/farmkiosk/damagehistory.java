package com.perchtech.humraz.farmkiosk;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.util.ArrayList;
import java.util.Collections;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class damagehistory extends AppCompatActivity {
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();
    String loss;
    String stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damagehistory);
        Firebase.setAndroidContext(this);
        read();
    }
    public void read()
    {
        SharedPreferences prefs3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        String kioskid = prefs3.getString("kname", null);
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/kioskdamages/"+kioskid);
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass user1 = userSnapshot.getValue(wareclass.class);

                    stock = user1.getStock().toString();
                    String time= user1.getTime().toString();
                    String date= user1.getDate().toString();
                    String st = "Damage reported: " + stock + "\nTime Reported: " + time+ "\nDate: "+date;

                    cardslis.add("Damage of "+stock + " Units");
                    cardsdes.add(st);

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
            Card card = new Card(getApplicationContext(),R.layout.cardd);
            final String name= cardslis.get(i);
            final String jame=cardsdes.get(i);



            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame);
            //Add Header to card

            //  header.setOtherButtonVisible(true);


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
    }

}
