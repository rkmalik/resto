package com.example.rkmalik.data;

import java.util.List;

/**
 * Created by vicks on 11/03/15.
 */
public class Category {
    private int id=-1;
    private String name=null;
    private List<FoodItem> foodItems=null;

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

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}
