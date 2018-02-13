package com.perchtech.humraz.farmkiosk.pm;

/**
 * Created by humra on 8/16/2016.
 */
public class farmerclass {
    private int[][] harvestmap1;
    private String farmname;
    private int[][] harvestmapwater;
    private int[][] harvestmappest;
    private int[][] getHarvestmapblack;
    private String borewell;
    private String irrigation;
    private double latitude;
    private double longitude;



    long stackId;

    public farmerclass() {
      /*Blank default constructor essential for Firebase*/
    }

    public farmerclass(String a) {

    }

    //Getters and setters


    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }





    public int[][] getHarvestmapwater() {
        return harvestmapwater;
    }

    public void setHarvestmapwater(int[][] harvestmapwater) {
        this.harvestmapwater = harvestmapwater;
    }

    public int[][] getHarvestmap1() {
        return harvestmap1;
    }

    public void setHarvestmap1(int[][] harvestmap1) {
        this.harvestmap1 = harvestmap1;
    }

    public int[][] getHarvestmappest() {
        return harvestmappest;
    }

    public void setHarvestmappest(int[][] harvestmappest) {
        this.harvestmappest = harvestmappest;
    }

    public int[][] getGetHarvestmapblack() {
        return getHarvestmapblack;
    }

    public void setGetHarvestmapblack(int[][] getHarvestmapblack) {
        this.getHarvestmapblack = getHarvestmapblack;
    }

    public String getBorewell() {
        return borewell;
    }

    public void setBorewell(String borewell) {
        this.borewell = borewell;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}