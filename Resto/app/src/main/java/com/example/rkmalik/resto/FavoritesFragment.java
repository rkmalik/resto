package com.example.rkmalik.resto;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shashankranjan on 3/13/15.
 */
public class FavoritesFragment extends Fragment /*implements AdapterView.OnItemClickListener*/{
    ListViewAdapter listViewAdapter;
    ListView listView;
    List<String> items;
    Activity fragActivity;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        prepareData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        fragActivity = this.getActivity();

        listView = (ListView) rootView.findViewById(R.id.listView2);

        prepareData();

        listViewAdapter = new ListViewAdapter(fragActivity, items);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(items.get(i));
                Intent detaiIntent = new Intent(fragActivity, FoodItemDetailActivity.class);
                detaiIntent.putExtra("name", items.get(i));
                startActivity(detaiIntent);
            }
        });

        listView.setAdapter(listViewAdapter);

        return rootView;
    }


    void prepareData(){
        items = new ArrayList<String>();

        items.add("Green Pepper");
        items.add("Flat Bread");
        items.add("Monterey Cheddar (Shredded)");
        items.add("Red Wine Vinegar");
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Intent detaiIntent = new Intent(fragActivity, FoodItemDetailActivity.class);
//        detaiIntent.putExtra("name", items.get(i));
//        startActivity(detaiIntent);
//    }
}
