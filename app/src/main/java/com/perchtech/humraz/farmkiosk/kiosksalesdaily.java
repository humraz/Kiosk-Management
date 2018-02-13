package com.perchtech.humraz.farmkiosk;

/**
 * Created by humra on 8/16/2016.
 */
public class kiosksalesdaily {
    private String amount;
    private String time;
    private String paymentmode;
    private String deposit;

    long stackId;
    public kiosksalesdaily() {
      /*Blank default constructor essential for Firebase*/
    }
    public kiosksalesdaily(String a)
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

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }
}