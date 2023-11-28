package com.example.myapplication;

import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MainModel {
    String flowerName,flowerDescription,flowerPrice;

    MainModel() {

    }

    public MainModel(String flowerName, String flowerDescription, String flowerPrice) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerPrice = flowerPrice;

    }

    public String getFlowerName() {
        return flowerName;
    }

    public String getFlowerDescription() {
        return flowerDescription;
    }

    public String getFlowerPrice() {
        return flowerPrice;
    }

}
