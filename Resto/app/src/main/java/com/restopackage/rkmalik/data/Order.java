package com.restopackage.rkmalik.data;

import java.util.List;

/**
 * Created by vicks on 07/04/15.
 */
public class Order {
    private int id;
    private String name;
    private int restId;
    private List<FoodItem> ingredients;

    public Order(int id, String name, List<FoodItem> ingredients)
    {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Order()
    {

    }

    public int getRestId()
    {
        return restId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setRestId(int restId)
    {
        this.restId = restId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<FoodItem> ingredients) {
        this.ingredients = ingredients;
    }
}
