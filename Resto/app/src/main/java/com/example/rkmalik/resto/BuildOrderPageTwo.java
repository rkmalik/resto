package com.example.rkmalik.resto;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.Order;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.List;
import java.util.Locale;


public class BuildOrderPageTwo extends ActionBarActivity {
    BuildOrderPageTwoAdapter listAdapter;
    ExpandableListView listView;
    Button finishBtn;
    List<Category> categoryList;
    DBHelper dbHelper;
    int id;
    String collectionName;
    int openGroupIndex = 0;
    private TextToSpeech tts;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_page_two);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("restId", 0);
        collectionName = intent.getStringExtra("name");

        dbHelper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        categoryList = RestaurantModel.getCategory(database, id, false);

        for(int i=0; i<categoryList.size(); i++)
        {
            Category category = categoryList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        database.close();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language is not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization Failed");
                }
            }
        });

        listView = (ExpandableListView) this.findViewById(R.id.buildorder_expandableListView2);

        listAdapter = new BuildOrderPageTwoAdapter(this, categoryList, id,tts);
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

        finishBtn = (Button) this.findViewById(R.id.order_add_finishbutton);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setName(collectionName);
                order.setIngredients(listAdapter.getIngredientsList());
                order.setRestId(id);
                SQLiteDatabase database = dbHelper.openDatabase();
                RestaurantModel.addOrder(database, order);
                database.close();
                Intent intent = new Intent(activity, FoodItems.class);
                intent.putExtra("restId", id);
                startActivity(intent);
            }
        });

        if(openGroupIndex > -1) {
            listView.expandGroup(openGroupIndex);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_build_order_page_two, menu);
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
    public void onResume()
    {
        super.onResume();
        if(dbHelper==null)
        {
            dbHelper = new DBHelper(this.getApplicationContext());
            SQLiteDatabase database = dbHelper.openDatabase();
            categoryList = RestaurantModel.getCategory(database, id, false);

            for(int i=0; i<categoryList.size(); i++)
            {
                Category category = categoryList.get(i);
                category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
            }

            database.close();
            listAdapter = new BuildOrderPageTwoAdapter(this, categoryList, id, tts);
            listView.setAdapter(listAdapter);
            if(openGroupIndex > -1){
                listView.expandGroup(openGroupIndex);
            }
        }

    }

    @Override
    public void onDestroy()
    {
        if(tts != null) {

            tts.stop();
            tts.shutdown();
            Log.d("TTS", "TTS Destroyed");
        }
        super.onDestroy();
    }
}
