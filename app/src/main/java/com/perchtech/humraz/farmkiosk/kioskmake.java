package com.perchtech.humraz.farmkiosk;

/**
 * Created by humra on 8/16/2016.
 */
public class kioskmake {
    private String post;
  private String pass;
    private String loggedin;
    private String salestatus;
    private String phonenumber;
    private String intime;
    private String outtime;
    private String indate;
    private String flagdate;
    private String stock;
    private String openingbal;
    private String yestopeningbal;
    private String yeststock;
    private String e;
    private String addstock;
    private String diff;

private String rate;

    long stackId;
    public kioskmake() {
      /*Blank default constructor essential for Firebase*/
    }
    public kioskmake(String a)
    {

    }
    //Getters and setters
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {

        return post;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLoggedin() {
        return loggedin;
    }

    public void setLoggedin(String loggedin) {
        this.loggedin = loggedin;
    }


    public String getSalestatus() {
        return salestatus;
    }

    public void setSalestatus(String salestatus) {
        this.salestatus = salestatus;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }



    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }



    public String getOpeningbal() {
        return openingbal;
    }

    public void setOpeningbal(String openingbal) {
        this.openingbal = openingbal;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getYestopeningbal() {
        return yestopeningbal;
    }

    public void setYestopeningbal(String yestopeningbal) {
        this.yestopeningbal = yestopeningbal;
    }

    public String getYeststock() {
        return yeststock;
    }

    public void setYeststock(String yeststock) {
        this.yeststock = yeststock;
    }

    public String getFlagdate() {
        return flagdate;
    }

    public void setFlagdate(String flagdate) {
        this.flagdate = flagdate;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getAddstock() {
        return addstock;
    }

    public void setAddstock(String addstock) {
        this.addstock = addstock;
    }
}