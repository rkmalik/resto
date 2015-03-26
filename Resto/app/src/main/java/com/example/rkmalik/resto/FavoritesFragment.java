package com.example.rkmalik.resto;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.List;

/**
 * Created by shashankranjan on 3/13/15.
 */
public class FavoritesFragment extends Fragment{
    ListViewAdapter listViewAdapter;
    ListView listView;
    List<String> items;
    List<String> pronun;
    Activity fragActivity;
    int restId;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        System.out.println("Favorite View Created!!");
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        fragActivity = this.getActivity();

        Intent intent = this.getActivity().getIntent();
        restId = intent.getIntExtra("restId", 0);

        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        final List<FoodItem> favItems = RestaurantModel.getFavourites(database, restId);
        database.close();

        listView = (ListView) rootView.findViewById(R.id.listView2);

        listViewAdapter = new ListViewAdapter(fragActivity, restId, favItems, this);

        listView.setAdapter(listViewAdapter);
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        List<FoodItem> favItems = RestaurantModel.getFavourites(database, restId);
        database.close();
        listViewAdapter = new ListViewAdapter(fragActivity, restId, favItems, this);
        listView.setAdapter(listViewAdapter);
    }
}
