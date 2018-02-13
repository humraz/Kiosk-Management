package com.perchtech.humraz.farmkiosk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;


public class warehouseconfirmorder extends ActionBarActivity {
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();

    String k = "yolo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouseconfirmorder);
        Firebase.setAndroidContext(this);
        read();
    }

    public void read()
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String pending = ord.getStatus();
                    if (k.equals(ord.getKioskname())) {
                        if(pending.equals("pending")) {
                            cardslis.add(0, pending);
                            StringBuilder finalt = new StringBuilder();
                            finalt.append("Kiosk Name:" + ord.getKioskname());
                            finalt.append("\nAmount: " + ord.getAmount());
                            String id = ord.getId();
                            key.add(0, id);

                            finalt.append("\nDate Placed: " + ord.getDateplaced());

                            finalt.append("\nUrgent: " + ord.getUrgent());
                            String f = String.valueOf(finalt);
                            cardsdes.add(0, f);
                        }
                        else
                        {
                            cardslis.add( pending);
                            StringBuilder finalt = new StringBuilder();
                            finalt.append("Kiosk Name:" + ord.getKioskname());
                            finalt.append("\nAmount: " + ord.getAmount());
                            String id = ord.getId();
                            key.add( id);

                            finalt.append("\nDate Placed: " + ord.getDateplaced());

                            finalt.append("\nUrgent: " + ord.getUrgent());
                            String f = String.valueOf(finalt);
                            cardsdes.add(f);
                        }
                    } else {
                        p();

                    }
                }
                cardpop();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void p()
    {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Sorry!")
                .setContentText("No Past Orders Found")


                .show();
    }
    public void cardpop()
    {
        for(int i=0;i<cardsdes.size();i++)
        {
            Card card = new Card(getApplicationContext());
            final String name= cardslis.get(i);
            final String jame=cardsdes.get(i);
            final String id = key.get(i);



            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame);
            //Add Header to card

            header.setOtherButtonVisible(true);
            if(name.equals("pending"))
                header.setOtherButtonDrawable(R.drawable.hourglass);
            else
                header.setOtherButtonDrawable(R.drawable.checked);
            //Add a callback
            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                @Override
                public void onButtonItemClick(Card card, View view) {

                     find(id);
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
    public void find(final String id)
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/ORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String id2 = ord.getId();
                    if(id2.equals(id))
                    {
                        userSnapshot.getRef().child("status").setValue("COMPLETED");
                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

}
