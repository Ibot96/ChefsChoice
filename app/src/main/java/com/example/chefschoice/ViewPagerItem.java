package com.example.chefschoice;

import java.util.List;

public class ViewPagerItem {

    int imageID;
    String rezeptName;
    List<Zutaten> zutatenList;
    public List<Zutaten> getZutatenList() {
        return zutatenList;
    }

    public void setZutatenList(List<Zutaten> zutatenList) {
        this.zutatenList = zutatenList;
    }

    public ViewPagerItem(int imageID, String rezeptName, List<Zutaten> zutatenList) {
        this.imageID = imageID;
        this.rezeptName = rezeptName;
        this.zutatenList = zutatenList;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getRezeptName() {
        return rezeptName;
    }

    public void setRezeptName(String rezeptName) {
        this.rezeptName = rezeptName;
    }
}
