package com.perchtech.humraz.farmkiosk.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class resetactivity extends AppCompatActivity {
    ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<String> phno = new ArrayList<>();
    ArrayList<String> loggedin = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetactivity);
        read();
    }
    public void cardpop()
    {
        for(int i=0;i<cardslis.size();i++)
        {
            Card card = new Card(getApplicationContext(), R.layout.cardd);
            final String name= cardslis.get(i);//Create a CardHeader
            if(name.equalsIgnoreCase("admin"))

                continue;

            final CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            String ph= phno.get(i);
            String l= loggedin.get(i);
            if(l.equals("true"))
            {
                l="Logged In";

            }
            else
            {
                l= "Not Logged In";
            }
            card.setTitle("Phno: "+ ph + " \nStatus: " +l);
            //Add Header to card

            header.setOtherButtonVisible(true);
            // card.setBackgroundResourceId(R.color.colorPrimary);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Intent in = new Intent(resetactivity.this, resettwo.class);
                    in.putExtra("kid", header.getTitle());
                    startActivity(in);


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
    public void read()
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/KIOSKS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake ord = userSnapshot.getValue(kioskmake.class);
                    String pending = ord.getPass();
                    //   Toast.makeText(pastorders.this, ord.getKioskname(),Toast.LENGTH_SHORT).show();
                    String phn= ord.getPhonenumber();
                    phno.add(phn);
                    String lg= ord.getLoggedin();
                    loggedin.add(lg);
                    cardslis.add(pending);
                }
                cardpop();
            }




            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
