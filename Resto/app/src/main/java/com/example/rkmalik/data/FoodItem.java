package com.example.rkmalik.data;

import android.text.format.Time;

import java.sql.Blob;

/**
 * Created by vicks on 11/03/15.
 */
public class FoodItem {
    private int id=-1;
    private String name=null;
    private String desc = null;
    private int catId=-1;
    private boolean isFav=false;
    private boolean isVeg=false;
    private int calories=-1;
    private Time lastAccessed=null;
    private Blob mainImage = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean isVeg) {
        this.isVeg = isVeg;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Time getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Time lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Blob getMainImage() {
        return mainImage;
    }

    public void setMainImage(Blob mainImage) {
        this.mainImage = mainImage;
    }
}
