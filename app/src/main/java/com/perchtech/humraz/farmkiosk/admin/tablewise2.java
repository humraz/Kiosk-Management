package com.perchtech.humraz.farmkiosk.admin;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perchtech.humraz.farmkiosk.R;
import com.perchtech.humraz.farmkiosk.kioskmake;
import com.perchtech.humraz.farmkiosk.ordering;
import com.perchtech.humraz.farmkiosk.warehouse.wareclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class tablewise2 extends AppCompatActivity {
    String kid;
    int sum = 0;
    int cash = 0;

    String time;
    int t;

    TextView tv7;
    TextView tv77;

    TextView tv6;
    TextView tv66;
    int no = 1;
    TextView tv3;
    TextView tv5;
    TextView tv4;
    TextView tv45;
    //tost(sum, c, cash, card, paytm, other);
    String opbal;
    int op = 0;
    TextView tv2;
    int deposit = 0;
    private static final String[][] DATA_TO_SHOW = new String[8][8];
    int d = 0;
    private static final String[] TABLE_HEADERS = {"Date","OP Bal", "CL Bal", "OP STK", "CL STK","DMGS","Added"};

    int deposit2 = 0;
    String url;
    String url2;
    TableView<String[]> tableView;
    int l = 0;
    int g=0;
    String datt3;
    int y = 0;
    int depooo = 0;
    String yopbal;
    String url3;
String  url4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablekioskwise);
        SharedPreferences pref3 = null;

        pref3 = getSharedPreferences("kioskname", MODE_PRIVATE);
        yopbal = pref3.getString("yopbal", null);
        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

      /*  TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(getApplicationContext(), 5, 130);
        columnModel.setColumnWidth(1, 130);
        columnModel.setColumnWidth(2, 130);
        columnModel.setColumnWidth(3, 130);
        columnModel.setColumnWidth(4, 130);
        columnModel.setColumnWidth(5, 130);
        tableView.setColumnModel(columnModel);*/
        String dayOfTheWeek = sdf.format(d);
        kid = getIntent().getStringExtra("kid");

        no = getIntent().getIntExtra("no", 1);
        //   Toast.makeText(this, kid ,Toast.LENGTH_LONG).show();
        SimpleDateFormat mon = new SimpleDateFormat("dd MMM");
        String date = mon.format(new Date());

        SimpleDateFormat monb = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        String month = monb.format(new Date());
        String yea = year.format(new Date());
        String da = day.format(new Date());

        url = "https://kioskfarm.firebaseio.com/SALES/" + kid + "/" + yea + "/" + month + "/" + da;
        url4 ="https://kioskfarm.firebaseio.com/ORDERS/";
        int dd = 0;
        dd = Integer.parseInt(da);

        for (l = 0; l < 6; l++) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -l);
            String datt = dateFormat.format(cal.getTime());

            DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal3 = Calendar.getInstance();
            cal3.add(Calendar.DATE, -l);
            datt3 = dateFormat3.format(cal3.getTime());

y2=0;

            DateFormat dateFormat4 = new SimpleDateFormat("ddMMyyyy");
            Calendar cal4 = Calendar.getInstance();
            cal4.add(Calendar.DATE, -l);
            String datt4 = dateFormat4.format(cal4.getTime());

            DateFormat dateFormat2 = new SimpleDateFormat("dd MMM");
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, -l);
            String datt2 = dateFormat2.format(cal2.getTime());
            DATA_TO_SHOW[l][0] = datt2;
            System.out.println("datesrhere" + datt4);


            url = "https://kioskfarm.firebaseio.com/SALES/" + kid + "/" + datt;
            url2 = "https://kioskfarm.firebaseio.com/opbal&stock/" + kid + "/" + datt4;
            url3 = "https://kioskfarm.firebaseio.com/kioskdamages/" + kid;

              //  y=l;
            // read(url);
            //   r(datt3);
            read2(url2,datt3);

           dd(url3,datt3);
            ware(url4,datt3);


        }
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        //tv.setText(date + "  " +dayOfTheWeek);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                tableView.setDataAdapter(new SimpleTableDataAdapter(tablewise2.this, DATA_TO_SHOW));

                // Toast.makeText(billing.this, "S" , Toast.LENGTH_LONG).show();


            }
        }, 3000);

    }
    int opball=0;
    int opstock=0;
    int yopball=0;
    int ystock=0;
    public void read2(final String url3,final String d) {
        final Firebase ref = new Firebase(url3);
        opball=0;
         opstock=0;
         yopball=0;
         ystock=0;
        //Value event listener for realtime data update
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    kioskmake sale = userSnapshot.getValue(kioskmake.class);
                    if (sale.getIndate().equals(d)) {

                        opball = Integer.parseInt(sale.getOpeningbal());
                        opstock = Integer.parseInt(sale.getStock());
                        yopball = Integer.parseInt(sale.getYestopeningbal());
                        ystock=Integer.parseInt(sale.getYeststock());
                        DATA_TO_SHOW[y][1] = Integer.toString(yopball);
                        DATA_TO_SHOW[y][2] = Integer.toString(opball);
                        DATA_TO_SHOW[y][3] = Integer.toString(ystock);
                        DATA_TO_SHOW[y][4] = Integer.toString(opstock);

                    }
                    else
                    {
                        opball=0;
                        opstock=0;
                        yopball=0;
                        ystock=0;
                        DATA_TO_SHOW[y][1] = "0";
                        DATA_TO_SHOW[y][2] ="0";
                        DATA_TO_SHOW[y][3] = "0";
                        DATA_TO_SHOW[y][4] = "0";
                    }

                }


//               ;
                y++;
                System.out.println("datesrhere" + y);
               // dd(url3);


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }








int yy=0;
    public void dd(final String url3, final String datt3) {
        final Firebase ref = new Firebase(url3);
        //Value event listener for realtime data update
        yy =0;
        SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy");
        final String tyme= time.format(new Date());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    wareclass sale = userSnapshot.getValue(wareclass.class);
                    if (sale.getDate().equals(datt3)) {
                        String t =sale.getDate();
                        if (t.equals(tyme))
                        yy += Integer.parseInt(sale.getStock().toString());
                        DATA_TO_SHOW[g][5] = Integer.toString(yy);


                    }
                    else
                            yy=0;


                }
                g++;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
int y2;
    int f=0;
    public void ware(final String url3, final String datt3) {
        final Firebase ref = new Firebase(url3);
        //Value event listener for realtime data update
        y2 =0;
        SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy");
        final String tyme= time.format(new Date());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    ordering sale = userSnapshot.getValue(ordering.class);
                    if (sale.getKioskname().equals(kid))
                    {


                    if (sale.getStatus().equals("Delivered")) {
                        if (sale.getDatedelivered().equals(datt3)) {
                            y2 += Integer.parseInt(sale.getAmountdelivered().toString());
                            System.out.println("detected one" +f);
                            DATA_TO_SHOW[f][6] = Integer.toString(y2);
                        }
                    }
                    }
                    else y2=0;


                }
                f++;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }



}