package com.perchtech.humraz.farmkiosk.pm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.deliveredorders;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.warehouse.editorder;
import com.perchtech.humraz.farmkiosk.warehouse.fulfill;
import com.perchtech.humraz.farmkiosk.warehouse.waremenu;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class pmwareorders extends AppCompatActivity {
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> kname = new ArrayList<String>();
    ArrayList<String> amount = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();
String k="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wareorders);
        Firebase.setAndroidContext(this);
        read();
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
                    if (k.equals(ord.getKioskname())) {

                       // if(pending.equals("pending")) {
                            cardslis.add( pending);
                            StringBuilder finalt = new StringBuilder();
                            kname.add(ord.getKioskname());
                            amount.add(ord.getAmount());
                            String id = ord.getId();
                            key.add( id);

                            finalt.append("Date Placed: " + ord.getDateplaced());

                            finalt.append("\nUrgent: " + ord.getUrgent());
                            String f = String.valueOf(finalt);
                            cardsdes.add( f);
                       /* }
                        else
                        {
                            cardslis.add( pending);
                            StringBuilder finalt = new StringBuilder();
                            kname.add(ord.getKioskname());
                            amount.add(ord.getAmount());
                            String id = ord.getId();
                            key.add( id);

                            finalt.append("\nDate Placed: " + ord.getDateplaced());

                            finalt.append("\nUrgent: " + ord.getUrgent());
                            String f = String.valueOf(finalt);
                            cardsdes.add(f);
                        }*/

                        flag=0;
                    }
                }
                if(flag==0)
                    cardpop();
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


                .show();
    }
    public void cardpop()
    {
        Collections.reverse(cardsdes);
        Collections.reverse(cardslis);
        Collections.reverse(key);
        Collections.reverse(amount);
        Collections.reverse(kname);
        for(int i=0;i<cardsdes.size();i++)
        {
            Card card = new Card(getApplicationContext(), R.layout.cardd);
            final String name= cardslis.get(i);
            final String jame=cardsdes.get(i);
            final String kn=kname.get(i);
            final String am =amount.get(i);
            final String id = key.get(i);



            //Create a CardHeader
            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(name);
            card.setTitle(jame+ "\n" + kn +"\n" + am);
            //Add Header to card

            header.setOtherButtonVisible(true);
            if(name.equals("pending"))
                header.setOtherButtonDrawable(R.drawable.hourglass);
            else if(name.equals("expired"))
                header.setOtherButtonDrawable(R.drawable.poison);
            else if(name.equals("InTransit"))
                header.setOtherButtonDrawable(R.drawable.trucking);
            else if(name.equals("Accepted"))
                header.setOtherButtonDrawable(R.drawable.checked);
            else if(name.equals("Delivered"))
                header.setOtherButtonDrawable(R.drawable.pp);
            else if(name.equals("Cancelled"))
                header.setOtherButtonDrawable(R.drawable.de);

            else if(name.equals("Declined"))
                header.setOtherButtonDrawable(R.drawable.prohibition);
            else
                header.setOtherButtonDrawable(R.drawable.checked);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    if(name.equals("pending"))
                    {
                      pend(id);
                    }
                    if(name.equals("Accepted"))
                    {
                        accepted(id, kn ,am);
                    }
                    /*else if(name.equals("Delivered"))
                    {
                        Intent in = new Intent(pmwareorders.this , deliveredorders.class);
                        in.putExtra("id", id);
                        startActivity(in);
                    }*/




                }
            });
            //Add a callback
            header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
                @Override
                public void onButtonItemClick(Card card, View view) {


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
        Intent in = new Intent(this, Main2Activity.class);
        startActivity(in);
    }
    public void find(final String id ,final String stat)
    {
        final Firebase ref = new Firebase("https://kioskfarm.firebaseio.com/PMORDERS/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering ord = userSnapshot.getValue(ordering.class);
                    String id2 = ord.getId();
                    if(id2.equals(id))
                    {
                        userSnapshot.getRef().child("status").setValue(stat);
                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();
        read();

    }
public void pend(final String id)
{
    new SweetAlertDialog(pmwareorders.this, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("Choose An Option")
            .showCancelButton(true)
            .setCancelText("Decline")
            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    Toast.makeText(pmwareorders.this, "You have Declined This order",Toast.LENGTH_LONG).show();
                    find(id, "Declined");
                    sDialog.cancel();

                }
            })
            .setConfirmText("Accept")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    Toast.makeText(pmwareorders.this, "You have Accepted This order",Toast.LENGTH_LONG).show();
                    find(id, "Accepted");
                    Intent in = new Intent( pmwareorders.this, pmwareorders.class);
                    startActivity(in);
                    sDialog.dismissWithAnimation();
                }
            }).show();
}
    public void accepted(final String id, final String kn, final String am)
    {

        new SweetAlertDialog(pmwareorders.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Choose An Option")
                .showCancelButton(true)
                .setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(pmwareorders.this, "You have Cancelled This order",Toast.LENGTH_LONG).show();
                        find(id, "Cancelled");

                        sDialog.cancel();


                    }
                })
                .setConfirmText("Edit")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(pmwareorders.this, "You Are Editing This order",Toast.LENGTH_LONG).show();
                        find(id, "Edited");
                        Intent in = new Intent(pmwareorders.this, editorder.class);
                        startActivity(in);
                        sDialog.dismissWithAnimation();
                    }
                })

                .setConfirmText("Fulfill")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(pmwareorders.this, "You Are Fulfilling This order",Toast.LENGTH_LONG).show();
                        //find(id, "Edited");
                        Intent in = new Intent(pmwareorders.this, pmfulfill.class);
                        in.putExtra("kn", kn);
                        in.putExtra("id",id);
                        in.putExtra("am", am);
                        startActivity(in);
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

}
