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
        //System.out.println("category id : "+String.valueOf(catId));
        Cursor cursor = db.rawQuery("SELECT * FROM fooditem food,menu menu WHERE catId=" + catId + "", null);
        return getFoodItemsFromCursor(cursor);
    }

    public static List<FoodItem> getFavourites(SQLiteDatabase db, int restId) {
        //Cursor cursor = db.rawQuery("select fooditem.* from fooditem,menu where fooditem.is_fav=1 and fooditem.catId=menu.category_id and menu.rest_id="+restId+"", null);
        Cursor cursor = db.rawQuery("select * from fooditem where is_fav=1", null);
        return getFoodItemsFromCursor(cursor);
    }

    public static FoodItem getFoodItemDetailsFromId(SQLiteDatabase db, int id)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM fooditem WHERE _id="+id+"", null);
        return getFoodItemsFromCursor(cursor).get(0);
    }

    private static List<FoodItem> getFoodItemsFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        List<FoodItem> foodItems = new ArrayList();
        while (cursor.isAfterLast() == false) {
            FoodItem foodItem = new FoodItem();
            foodItem.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            foodItem.setName(cursor.getString(cursor.getColumnIndex("name")));
            foodItem.setPhoneticName(cursor.getString(cursor.getColumnIndex("phonetic_name")));
            foodItem.setLocalName(cursor.getString(cursor.getColumnIndex("local_name")));
            foodItem.setFav(cursor.getInt(cursor.getColumnIndex("is_fav")) == 1 ? true : false);
            foodItem.setVeg(cursor.getInt(cursor.getColumnIndex("is_veg")) == 1 ? true : false);
            foodItem.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
            foodItem.setCalories(cursor.getInt(cursor.getColumnIndex("calories")));
            foodItem.setServingSize(cursor.getString(cursor.getColumnIndex("serving_size")));
            byte[] img = cursor.getBlob(cursor.getColumnIndex("main_image"));
            foodItem.setMainImage(BitmapFactory.decodeByteArray(img, 0, img.length));
            //TODO : Last accessed timestamp
            //foodItem.setLastAccessed(cursor.getString(7)==null?new Time(): Time.parse(cursor.getString(7)));
            foodItems.add(foodItem);
            cursor.moveToNext();
        }
        return foodItems;
    }

    public static void updateIsFavFoodItem(SQLiteDatabase db, FoodItem foodItem) {
        db.execSQL("UPDATE fooditem SET is_fav=" + (foodItem.isFav() ? "1" : "0") + " WHERE _id=" + foodItem.getId() + "");
    }

    public static List<Category> getCategoriesBasedOnRestaurant(SQLiteDatabase db, int restId) {
        Cursor cursor = db.rawQuery("SELECT * FROM menu WHERE rest_id=" + restId + " ORDER BY display_order", null);
        cursor.moveToFirst();
        List<Category> categories = new ArrayList();
        while (cursor.isAfterLast() == false) {
            Category category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex("category_id")));
            category.setName(cursor.getString(cursor.getColumnIndex("category_name")));
            categories.add(category);
            cursor.moveToNext();
        }
        return categories;
    }
}
