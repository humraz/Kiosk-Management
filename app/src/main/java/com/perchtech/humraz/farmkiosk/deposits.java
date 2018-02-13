package com.perchtech.humraz.farmkiosk;

/**
 * Created by humra on 8/16/2016.
 */
public class deposits {
    private String amount;
    private String time;
    private String date;
    private String kid;


    long stackId;
    public deposits() {
      /*Blank default constructor essential for Firebase*/
    }
    public deposits(String a)
    {

    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }
}