package com.resto.rkmalik.resto;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.resto.rkmalik.data.Category;
import com.resto.rkmalik.model.DBHelper;
import com.resto.rkmalik.model.RestaurantModel;

import java.util.List;


public class BuildOrderPage extends ActionBarActivity {
    BuildOrderPageOneAdapter listAdapter;
    ExpandableListView listView;
    Button cancelBtn;
    List<Category> collectionList;
    DBHelper dbHelper;
    int id;
    int openGroupIndex = 0;
    String callingactivity;
    static final int END_ACTIVITY_REQUEST = 0;
    static final int END_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_page);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("restId", 0);

        dbHelper = new DBHelper(this.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        collectionList = RestaurantModel.getCategory(database, id, true);

        for(int i=0; i<collectionList.size(); i++)
        {
            Category category = collectionList.get(i);
            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
        }

        database.close();

        listView = (ExpandableListView) this.findViewById(R.id.buildorder_expandableListView);

        listAdapter = new BuildOrderPageOneAdapter(this, collectionList, id);
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

        cancelBtn = (Button) this.findViewById(R.id.button3);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        if(openGroupIndex > -1) {
            listView.expandGroup(openGroupIndex);
        }
    }

    private void cancel(){
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_build_order_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id == android.R.id.home){
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(dbHelper == null) {
            dbHelper = new DBHelper(this.getApplicationContext());
            SQLiteDatabase database = dbHelper.openDatabase();
            collectionList = RestaurantModel.getCategory(database, id, true);

            for (int i = 0; i < collectionList.size(); i++) {
                Category category = collectionList.get(i);
                category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
            }

            database.close();
        }
        listAdapter = new BuildOrderPageOneAdapter(this, collectionList, id);
        listView.setAdapter(listAdapter);
        if(openGroupIndex > -1){
            listView.expandGroup(openGroupIndex);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == END_ACTIVITY_REQUEST){
            if(resultCode == END_ACTIVITY_RESULT){
                this.finish();
            }
        }
    }
}
