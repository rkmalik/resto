package com.example.rkmalik.resto;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rkmalik.data.Restaurant;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.List;

public class NearByRestaurants extends ActionBarActivity {

    ListView list;
    String[] web = {
            "Tick",
            "Tock",
    } ;

    Integer[] imageId = {
            R.drawable.chipotle,
            R.drawable.subway
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_restaurants);

        DBHelper dbHelper = new DBHelper(this);

        //SQLiteDatabase database = dbHelper.getDatabase();
        SQLiteDatabase database = dbHelper.openDatabase();

        List<Restaurant> restaurantList = RestaurantModel.getRestList(database);




        web = new String[restaurantList.size()];
        for(int i=0; i<restaurantList.size(); i++)
            web[i] = restaurantList.get(i).getName();


        Bitmap[] images = new Bitmap[restaurantList.size()];
        for(int i=0; i<restaurantList.size(); i++)
            images[i] = restaurantList.get(i).getLogo();
        customlistview adapter = new
                customlistview(NearByRestaurants.this, images);

        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(NearByRestaurants.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_near_by_restaurants, menu);
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
            Toast.makeText(NearByRestaurants.this, "You Clicked at ", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
