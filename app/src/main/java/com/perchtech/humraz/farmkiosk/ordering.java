package com.perchtech.humraz.farmkiosk;

/**
 * Created by humra on 2/10/2017.
 */
public class ordering {
    private String amount;
    private String kioskname;
    private String urgent;
    private String dateplaced;
    private String daterequired;

    private String status;
    private String id;
    private String newamount;
    private String deliverydategiven;
    private String deliverytime;
    private String driver;

    private String datedelivered;
    private String amountdelivered;
    private String timedelivered;


    long stackId;
    public ordering() {
      /*Blank default constructor essential for Firebase*/
    }
    public ordering(String a)
    {

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKioskname() {
        return kioskname;
    }

    public void setKioskname(String kioskname) {
        this.kioskname = kioskname;
    }

    public String getDateplaced() {
        return dateplaced;
    }

    public void setDateplaced(String dateplaced) {
        this.dateplaced = dateplaced;
    }

    public String getDaterequired() {
        return daterequired;
    }

    public void setDaterequired(String daterequired) {
        this.daterequired = daterequired;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getDatedelivered() {
        return datedelivered;
    }

    public void setDatedelivered(String datedelivered) {
        this.datedelivered = datedelivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewamount() {
        return newamount;
    }

    public void setNewamount(String newamount) {
        this.newamount = newamount;
    }

    public String getDeliverydategiven() {
        return deliverydategiven;
    }

    public void setDeliverydategiven(String deliverydategiven) {
        this.deliverydategiven = deliverydategiven;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTimedelivered() {
        return timedelivered;
    }

    public void setTimedelivered(String timedelivered) {
        this.timedelivered = timedelivered;
    }

    public String getAmountdelivered() {
        return amountdelivered;
    }

    public void setAmountdelivered(String amountdelivered) {
        this.amountdelivered = amountdelivered;
    }


    //Getters and setters

}

