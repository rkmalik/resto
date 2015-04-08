package com.restopackage.rkmalik.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

/**
 * Created by vicks on 11/03/15.
 */
public class FoodItem implements Parcelable {
    private int id=-1;
    private String name=null;
    private String phoneticName = null;
    private String localName = null;
    private String desc = null;
    private int catId=-1;
    private boolean isFav=false;
    private boolean isVeg=false;
    private int calories=-1;
    private String servingSize = null;
    private Time lastAccessed=null;
    private Bitmap mainImage = null;

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

    public Bitmap getMainImage() {
        return mainImage;
    }

    public void setMainImage(Bitmap mainImage) {
        this.mainImage = mainImage;
    }

    public String getPhoneticName() {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName) {
        this.phoneticName = phoneticName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
