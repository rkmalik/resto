package com.example.rkmalik.resto;

import android.app.Activity;
import android.app.ListFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class MenuItemsFragment extends Fragment{
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<Category> categoryList;
    Activity fragActivity;
    DBHelper dbHelper;
    int id;
    int openGroupIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.fragment_menu_items, container, false);
       fragActivity = this.getActivity();

        Intent intent = this.getActivity().getIntent();
        id = intent.getIntExtra("restId", 0);

        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        categoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);

        for(int i=0; i<categoryList.size(); i++)
        {
            Category category = categoryList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        database.close();

       listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView2);

       listAdapter = new ExpandableListAdapter(fragActivity, categoryList, id);
       listView.setAdapter(listAdapter);
       listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){

           @Override
           public void onGroupExpand(int i) {
               openGroupIndex = i;
           }
       });

       listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener(){

           @Override
           public void onGroupCollapse(int i) {
               openGroupIndex = -1;
           }
       });

       if(openGroupIndex > -1) {
           listView.expandGroup(openGroupIndex);
       }
       return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        categoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);

        for(int i=0; i<categoryList.size(); i++)
        {
            Category category = categoryList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        database.close();
        listAdapter = new ExpandableListAdapter(fragActivity, categoryList, id);
        listView.setAdapter(listAdapter);
        if(openGroupIndex > -1){
            listView.expandGroup(openGroupIndex);
        }

    }
}
