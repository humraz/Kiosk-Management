package com.perchtech.humraz.farmkiosk.warehouse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by humraz on 1/3/18.
 */

public class data  {
    private String date;
    private String  kname;
    private String amount;
    private String cardsdes;
    private String key;
    private String cardslis;


    public data(String date,String kname,String amount,String cardsdes,String key ,String cardslis) {
       this.amount=amount;
        this.date=date;
        this.cardsdes=cardsdes;
        this.kname=kname;
        this.key=key;
        this.cardslis=cardslis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardsdes() {
        return cardsdes;
    }

    public void setCardsdes(String cardsdes) {
        this.cardsdes = cardsdes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCardslis() {
        return cardslis;
    }

    public void setCardslis(String cardslis) {
        this.cardslis = cardslis;
    }

    public static Comparator<data> StuNameComparator = new Comparator<data>() {

        public int compare(data s1, data s2) {
            String StudentName1 = s1.getDate();
            String StudentName2 = s2.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
Date date1=null;
            Date date2=null;
            try {
                  date1 = sdf.parse(StudentName1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                 date2 = sdf.parse(StudentName2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //ascending order
            return date2.compareTo(date1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
  /*  @Override
    public int compareTo(data comp) throws ParseException {
        String compareage=((data)comp).getDate();
        Date date1=null;
        Date date2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        date1 = sdf.parse(this.date);

        date2 = sdf.parse(compareage);

        *//* For Ascending order*//*
        int a= date1.compareTo(date2);

        return a ;
    }*/
}
