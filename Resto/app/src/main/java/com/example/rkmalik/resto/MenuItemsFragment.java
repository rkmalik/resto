package com.example.rkmalik.resto;

import android.app.Activity;
import android.app.ListFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.content.Intent;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shashankranjan on 3/13/15.
 */
public class MenuItemsFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<Category> categoryList;
    Activity fragActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.fragment_menu_items, container, false);
       fragActivity = this.getActivity();

        Intent intent = this.getActivity().getIntent();
        int id = intent.getIntExtra("id", 0);

        DBHelper dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        System.out.println("rest id is"+String.valueOf(id));
        categoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);

        for(int i=0; i<categoryList.size(); i++)
        {
            Category category = categoryList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        dbHelper.close();

       listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView2);

       listAdapter = new ExpandableListAdapter(fragActivity, categoryList);
       listView.setAdapter(listAdapter);
       return rootView;
    }
}
