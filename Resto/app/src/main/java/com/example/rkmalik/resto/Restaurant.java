package com.example.rkmalik.resto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import  java.util.List;


public class Restaurant extends ActionBarActivity implements OnClickListener{
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> categories;
    HashMap<String, List<String>> items;
    HashMap<String, List<Integer>> itemImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        View tab1 = findViewById(R.id.tab1);
        View tab2 = findViewById(R.id.tab1);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        listView = (ExpandableListView) findViewById(R.id.expandableListView);

        prepareDataList();

        listAdapter = new ExpandableListAdapter(this, categories, items, itemImages);

        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                System.out.println("Clicking the child!!");
                Toast.makeText(getApplicationContext(),
                                categories.get(i)
                                + " : "
                                + items.get(i).get(i2), Toast.LENGTH_SHORT);
                return false;
            }
        });
    }

    void prepareDataList(){
        categories = new ArrayList<String>();
        items = new HashMap<String, List<String>>();
        itemImages = new HashMap<String, List<Integer>>();

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

        items.put(categories.get(0), breads);
        items.put(categories.get(1), cheese);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
