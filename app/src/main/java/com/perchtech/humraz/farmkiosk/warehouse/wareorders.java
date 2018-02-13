package com.perchtech.humraz.farmkiosk.warehouse;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.deliveredorders;
import com.perchtech.humraz.farmkiosk.loginactivity;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.pastorders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

public class wareorders extends AppCompatActivity {
    ArrayList<Card> cards = new ArrayList<Card>();
  /*  ArrayList<String> cardslis = new ArrayList<String>();
    ArrayList<String> kname = new ArrayList<String>();
    ArrayList<String> amount = new ArrayList<String>();
    ArrayList<String> cardsdes = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();
    ArrayList<Date> date = new ArrayList<Date>();*/
  ArrayList<data> arraylist = new ArrayList<data>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wareorders);
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

                       // if(pending.equals("pending")) {



                         /*   cardslis.add( pending);
                            StringBuilder finalt = new StringBuilder();
                            kname.add(ord.getKioskname());
                            amount.add(ord.getAmount());
                            String id = ord.getId();
                            key.add( id);

                            finalt.append("Date Placed: " + ord.getDateplaced());


                    finalt.append("\nUrgent: " + ord.getUrgent());
                            String f = String.valueOf(finalt);
                            cardsdes.add( f);*/
                    StringBuilder finalt = new StringBuilder();
                    finalt.append("Date Placed: " + ord.getDateplaced());
                    finalt.append("\nUrgent: " + ord.getUrgent());
                    String f = String.valueOf(finalt);

                    arraylist.add(new data( ord.getDateplaced(),ord.getKioskname(),ord.getAmount(),f,ord.getId(),ord.getStatus()));









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

                }
                sort();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    void sort()
    {
       // int n = arr.size();
        //Collections.sort(date);


        // One by one move boundary of unsorted subarray
      /*  for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr.get(j) < arr.get(min_idx))
                    min_idx = j;
*/
            // Swap the found minimum element with the first
            // element
         /*   Collections.swap(arr, min_idx,i);
            Collections.swap(cardsdes, min_idx,i);

            Collections.swap(cardslis, min_idx,i);

            Collections.swap(key, min_idx,i);
            Collections.swap(amount, min_idx,i);
            Collections.swap(kname, min_idx,i);*/


       // }
        Collections.sort(arraylist,data.StuNameComparator);
        cardpop();
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
     /*   Collections.reverse(cardsdes);
        Collections.reverse(cardslis);
        Collections.reverse(key);
        Collections.reverse(amount);
        Collections.reverse(kname);*/
        for(int i=0;i<arraylist.size();i++)
        {
            Card card = new Card(getApplicationContext(), R.layout.cardd);
           /* final String name= cardslis.get(i);
            final String jame=cardsdes.get(i);
            final String kn=kname.get(i);
            final String am =amount.get(i);
            final String id = key.get(i);

*/
            final String name= arraylist.get(i).getCardslis();
            final String jame=arraylist.get(i).getCardsdes();
            final String kn=arraylist.get(i).getKname();
            final String am =arraylist.get(i).getAmount();
            final String id = arraylist.get(i).getKey();

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
                    else if(name.equals("Delivered"))
                    {
                        Intent in = new Intent(wareorders.this , deliveredorders.class);
                        in.putExtra("id", id);
                        startActivity(in);
                    }




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
        Intent in = new Intent(this, waremenu.class);
        startActivity(in);
    }
    public void find(final String id ,final String stat)
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
    new SweetAlertDialog(wareorders.this, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("Choose An Option")
            .showCancelButton(true)
            .setCancelText("Decline")
            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    Toast.makeText(wareorders.this, "You have Declined This order, Please Wait while we update the status",Toast.LENGTH_LONG).show();
                    find(id, "Declined");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent( wareorders.this, wareorders.class);
                            startActivity(in);
                        }
                    }, 2000);
                    sDialog.cancel();

                }
            })
            .setConfirmText("Accept")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    Toast.makeText(wareorders.this, "You have Accepted This order, Please Wait while we update the status",Toast.LENGTH_LONG).show();
                    find(id, "Accepted");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent( wareorders.this, wareorders.class);
                            startActivity(in);
                        }
                    }, 2000);

                    sDialog.dismissWithAnimation();
                }
            }).show();
}
    public void accepted(final String id, final String kn, final String am)
    {

        new SweetAlertDialog(wareorders.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Choose An Option")
                .showCancelButton(true)
                .setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(wareorders.this, "You have Cancelled This order, Please Wait while we update the status",Toast.LENGTH_LONG).show();
                        find(id, "Cancelled");
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent( wareorders.this, wareorders.class);
                                startActivity(in);
                            }
                        }, 2000);
                        sDialog.cancel();


                    }
                })
                .setConfirmText("Edit")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(wareorders.this, "You Are Editing This order",Toast.LENGTH_LONG).show();
                        find(id, "Edited");
                        Intent in = new Intent(wareorders.this, editorder.class);
                        startActivity(in);
                        sDialog.dismissWithAnimation();
                    }
                })

                .setConfirmText("Fulfill")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(wareorders.this, "You Are Fulfilling This order",Toast.LENGTH_LONG).show();
                        //find(id, "Edited");
                        Intent in = new Intent(wareorders.this, fulfill.class);
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
