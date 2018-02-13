package com.perchtech.humraz.farmkiosk.warehouse;

/**
 * Created by humra on 8/16/2016.
 */
public class wareclass {
    private String stock;
    private String loss;
    private String comment;
    private String time;
    private String date;


    long stackId;
    public wareclass() {
      /*Blank default constructor essential for Firebase*/
    }
    public wareclass(String a)
    {

    }
    //Getters and setters
    public String getStock() {
        return stock;
    }
    @Override
    public String toString() {

        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}