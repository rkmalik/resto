package com.example.rkmalik.resto;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.content.Intent;

import com.example.rkmalik.data.FoodItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shashankranjan on 3/13/15.
 */
public class MenuItemsFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> categories;
    HashMap<String, List<String>> items;
    HashMap<String, List<Integer>> itemImages;
    HashMap<String, List<String>> itemPronun;
    Activity fragActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.fragment_menu_items, container, false);
       fragActivity = this.getActivity();

       listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView2);
       prepareDataList();

       listAdapter = new ExpandableListAdapter(fragActivity, categories, items, itemImages, itemPronun);

//       listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
//
//           @Override
//           public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
//               Intent detailIntent = new Intent(fragActivity, FoodItemDetailActivity.class);
//               detailIntent.putExtra(FoodItemDetailFragment.ARG_ITEM_ID, items.get(categories.get(i)).get(i2));
//               startActivity(detailIntent);
//               return false;
//           }
//       });
       listView.setAdapter(listAdapter);
       return rootView;
    }

    void prepareDataList(){
        categories = new ArrayList<String>();
        items = new HashMap<String, List<String>>();
        itemImages = new HashMap<String, List<Integer>>();
        itemPronun = new HashMap<String, List<String>>();

        categories.add("Breads");
        categories.add("Cheese");

        List<String> breads = new ArrayList<String>();
        breads.add("Italian");
        breads.add("Flat Bread");
        breads.add("Italian Herbs and Cheese");
        breads.add("9-Grain Wheat");
        breads.add("9-Grain Honey Oat");
        List<Integer> breadImgs = new ArrayList<Integer>();
//        breadImgs.add(R.drawable.o_BreadItalian);
//        breadImgs.add(R.drawable.o_BreadFlat);
//        breadImgs.add(R.drawable.o_BreadItalianHerb);
//        breadImgs.add(R.drawable.o_BreadWheat);
//        breadImgs.add(R.drawable.o_BreadHOWheat);
        List<String> bPronun = new ArrayList<String>();
        bPronun.add("/Pronun /ciation");
        bPronun.add("/Pronun /ciation");
        bPronun.add("/Pronun /ciation");
        bPronun.add("/Pronun /ciation");
        bPronun.add("/Pronun /ciation");

        List<String> cheese = new ArrayList<String>();
        cheese.add("White American");
        cheese.add("Monterey Cheddar (Shredded)");
        cheese.add("Provolone");
        cheese.add("Swiss");
        cheese.add("Pepper Jack");
        cheese.add("Shredded Mozzarella");
        cheese.add("Feta");
        List<Integer> cheeseImgs = new ArrayList<Integer>();
//        cheeseImgs.add(R.drawable.o_CheeseAmerWhite);
//        cheeseImgs.add(R.drawable.o_CheeseMonterery);
//        cheeseImgs.add(R.drawable.o_CheeseProvolone);
//        cheeseImgs.add(R.drawable.o_CheeseSwiss);
//        cheeseImgs.add(R.drawable.o_CheeseMozza);
//        cheeseImgs.add(R.drawable.o_CheeseFeta);

        List<String> chPronun = new ArrayList<String>();
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");
        chPronun.add("/Pronun /ciation");

        items.put(categories.get(0), breads);
        items.put(categories.get(1), cheese);

        itemPronun.put(categories.get(0), bPronun);
        itemPronun.put(categories.get(1), chPronun);
    }
}
