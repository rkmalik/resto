package com.example.rkmalik.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicks on 12/03/15.
 */
public class RestaurantModel {

    public static List<Restaurant> getRestList(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM restaurant", null);
        cursor.moveToFirst();
        List<Restaurant> restaurants = new ArrayList();
        while (cursor.isAfterLast() == false) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
            restaurant.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            byte[] img = cursor.getBlob(cursor.getColumnIndex("logo"));
            restaurant.setLogo(BitmapFactory.decodeByteArray(img, 0, img.length));
            restaurants.add(restaurant);
            cursor.moveToNext();
        }
        return restaurants;
    }

    public static List<FoodItem> getFoodItemsBasedOnCategory(SQLiteDatabase db, int catId) {
        Cursor cursor = db.rawQuery("SELECT * FROM fooditem WHERE catId=" + catId + "", null);
        return getFoodItemsFromCursor(cursor);
    }

    public static List<FoodItem> getFavourites(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM fooditem WHERE isFav=1", null);
        return getFoodItemsFromCursor(cursor);
    }

    private static List<FoodItem> getFoodItemsFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        List<FoodItem> foodItems = new ArrayList();
        while (cursor.isAfterLast() == false) {
            FoodItem foodItem = new FoodItem();
            foodItem.setId(cursor.getInt(1));
            foodItem.setName(cursor.getString(2));
            foodItem.setDesc(cursor.getString(3));
            foodItem.setFav(cursor.getInt(5) == 1 ? true : false);
            foodItem.setVeg(cursor.getInt(6) == 1 ? true : false);
            //TODO : Last accessed timestamp
            //foodItem.setLastAccessed(cursor.getString(7)==null?new Time(): Time.parse(cursor.getString(7)));
            foodItems.add(foodItem);
            cursor.moveToNext();
        }
        return foodItems;
    }

    public static void updateIsFavFoodItem(SQLiteDatabase db, FoodItem foodItem) {
        db.execSQL("UPDATE fooditem SET isFav=" + (foodItem.isFav() ? "1" : "0") + " WHERE id=" + foodItem.getId() + "");
    }

    public static List<Category> getCategoriesBasedOnRestaurant(SQLiteDatabase db, int restId) {
        Cursor cursor = db.rawQuery("SELECT * FROM menu WHERE restId=" + restId + "", null);
        cursor.moveToFirst();
        List<Category> categories = new ArrayList();
        while (cursor.isAfterLast() == false) {
            Category category = new Category();
            category.setId(cursor.getInt(1));
            category.setName(cursor.getString(2));
            categories.add(category);
            cursor.moveToNext();
        }
        return categories;
    }
}
