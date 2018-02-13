package com.perchtech.humraz.farmkiosk.admin;

/**
 * Created by humra on 8/16/2016.
 */
public class reset {

private String text;
    private String date;
    private String time;



    long stackId;
    public reset() {
      /*Blank default constructor essential for Firebase*/
    }
    public reset(String a)
    {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    //Getters and setters

}