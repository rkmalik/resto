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
import android.widget.ExpandableListView;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.List;
import java.util.Locale;


public class BuildOrderPageTwo extends ActionBarActivity {
    BuildOrderPageTwoAdapter listAdapter;
    ExpandableListView listView;
    List<Category> categoryList;
    DBHelper dbHelper;
    int id;
    int collectionId;
    int openGroupIndex = 0;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_page_two);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("restId", 0);
        collectionId = intent.getIntExtra("id", 0);

        dbHelper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        categoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);

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

                    // tts.setPitch(5); // set pitch level

                    // tts.setSpeechRate(2); // set speech speed rate

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language is not supported");
                    }
                } else {
                    Log.e("TTS", "Initilization Failed");
                }
            }
        });

        listView = (ExpandableListView) this.findViewById(R.id.buildorder_expandableListView2);

        listAdapter = new BuildOrderPageTwoAdapter(this, categoryList, id, collectionId,tts);
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
        dbHelper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        categoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);

        for(int i=0; i<categoryList.size(); i++)
        {
            Category category = categoryList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        database.close();
        listAdapter = new BuildOrderPageTwoAdapter(this, categoryList, id, collectionId, tts);
        listView.setAdapter(listAdapter);
        if(openGroupIndex > -1){
            listView.expandGroup(openGroupIndex);
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
