package com.restopackage.rkmalik.data;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vicks on 11/03/15.
 */
public class Restaurant implements Serializable {
    private int id=-1;
    private String name=null;
    private String cityName=null;
    private int menuId=-1;
    private List<Category> categories=null;
    private Bitmap logo = null;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }
}
