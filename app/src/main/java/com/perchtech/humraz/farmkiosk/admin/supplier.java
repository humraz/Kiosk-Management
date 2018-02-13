package com.perchtech.humraz.farmkiosk.admin;

/**
 * Created by humra on 8/16/2016.
 */
public class supplier {

private String supid;
    private String supname;
    private String phno;
    private String location;

    long stackId;
    public supplier() {
      /*Blank default constructor essential for Firebase*/
    }
    public supplier(String a)
    {

    }

    public String getSupid() {
        return supid;
    }

    public void setSupid(String supid) {
        this.supid = supid;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    //Getters and setters

}