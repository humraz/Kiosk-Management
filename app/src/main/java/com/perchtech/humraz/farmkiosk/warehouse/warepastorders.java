package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.deliveredorders;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.orderingfirst;
import com.perchtech.humraz.farmkiosk.orderreceived;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;


public class warepastorders extends ActionBarActivity {
   ArrayList<Card> cards = new ArrayList<Card>();
   /* ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();
    ArrayList<Integer> date = new ArrayList<Integer>();*/
    ArrayList<data> arraylist = new ArrayList<data>();
    String name2;
    String k = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastorders);

        Firebase.setAndroidContext(this);
        read();
        SharedPreferences prefs3= null;
       // prefs3= getSharedPreferences("log",MODE_PRIVATE);
        k= "Admin";


    }
      int flag=1;

    public void read()
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/PMORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String pending = ord.getStatus();
                 //   Toast.makeText(pastorders.this, ord.getKioskname(),Toast.LENGTH_SHORT).show();
                    if (k.equals(ord.getKioskname())) {
                       // cardslis.add(pending);

                        StringBuilder finalt = new StringBuilder();
                        finalt.append("Warehouse Id:" + ord.getKioskname());
                        finalt.append("\nAmount: " + ord.getAmount());
                      //  key.add(ord.getId());

                        finalt.append("\nDate Placed: " + ord.getDateplaced());

                        finalt.append("\nUrgent: " + ord.getUrgent());
                        String f = String.valueOf(finalt);
                        //cardsdes.add(f);
                        flag++;
                        arraylist.add(new data( ord.getDateplaced(),ord.getKioskname(),ord.getAmount(),f,"",ord.getStatus()));
                    }
                }
                if(flag==1)
                {
                    flag++;
                    Collections.sort(arraylist,data.StuNameComparator);
                    cardpop();
                }
                else
                    p();
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

            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    move();

                }
            })
            .show();
}
    public void move()
    {
        Intent in = new Intent(this, wareorderingfirst.class);
        startActivity(in);
    }


    public void cardpop()
    {
      /*  Collections.reverse(cardsdes);
        Collections.reverse(cardslis);
        Collections.reverse(key);*/
        for(int i=0;i<arraylist.size();i++)
        {
            Card card = new Card(getApplicationContext(),R.layout.cardd);
            final String name= arraylist.get(i).getCardslis();
            final String jame=arraylist.get(i).getCardsdes();
            //final String jame =Integer.toString(date.get(i));

            final String id = arraylist.get(i).getKey();

            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame);
            //Add Header to card

            header.setOtherButtonVisible(true);
            if(name.equals("pending"))
            header.setOtherButtonDrawable(R.drawable.hourglass);
            else if(name.equals("InTransit"))
                header.setOtherButtonDrawable(R.drawable.trucking);
            else if(name.equals("Accepted"))
                header.setOtherButtonDrawable(R.drawable.checked);
            else if(name.equals("Delivered"))
                header.setOtherButtonDrawable(R.drawable.pp);
            else if(name.equals("expired"))
                header.setOtherButtonDrawable(R.drawable.poison);
            else if(name.equals("Declined"))
                header.setOtherButtonDrawable(R.drawable.prohibition);
            else if(name.equals("Cancelled"))
                header.setOtherButtonDrawable(R.drawable.de);
            else
                header.setOtherButtonDrawable(R.drawable.checked);
            //Add a callback
            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                @Override
                public void onButtonItemClick(Card card, View view) {
                    name2 = name;
                    // find(name);
                }
            });
           // card.setBackgroundResourceId(R.color.colorPrimary);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    if(name.equals("InTransit"))
                    {
                        pend(id, jame);
                    }
                   /* else if(name.equals("Delivered"))
                    {
                       Intent in = new Intent(warepastorders.this , deliveredorders.class);
                        in.putExtra("id", id);
                        startActivity(in);
                    }*/
                    else {
                        String a = card.getTitle();
                        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                    }
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
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, wareorderingfirst.class);
        startActivity(in);
    }
    public void pend(final String id, final String jame)
    {
        new SweetAlertDialog(warepastorders.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Has The Order Arrived?")
                .showCancelButton(true)
                .setCancelText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                       // Toast.makeText(pastorders.this, "You have Declined This order",Toast.LENGTH_LONG).show();

                        sDialog.cancel();

                    }
                })
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                       // Toast.makeText(pastorders.this, id + jame,Toast.LENGTH_LONG).show();
                      move2(id, jame);

                        sDialog.dismissWithAnimation();
                    }
                }).show();
    }

    public void move2(final String id, final String jame)
    {
        Intent in = new Intent(this, wareorderreceived.class);
        in.putExtra("id", id);
        in.putExtra("na", jame);
        startActivity(in);
    }

}
