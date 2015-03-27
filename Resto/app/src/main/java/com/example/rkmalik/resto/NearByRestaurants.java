package com.example.rkmalik.resto;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.rkmalik.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NearByRestaurants extends Activity {
    private         DrawerLayout                    mDrawerLayout;
    private         LinearLayout                    mDrawer ;
    private         ListView                        mDrawerList;
    private         ActionBarDrawerToggle           mDrawerToggle;
    private         CharSequence                    mDrawerTitle;
    private         CharSequence                    mTitle;
    private         String[]                        mNavigationTitles;
    private         List<HashMap<String,String>>    mList ;
    final private   String                          ICONNAME="ICONNAME";
    final private   String                          ICONIMG = "ICONIMG";
    private         SimpleAdapter                   mAdapter;
    private         ListView                        restaurantsList;
    public static   String                          KEY_RESTAURANT_NAME = "KEY_TITLE";
    public static   String                          KEY_RESTAURANT_ICON = "KEY_ICON";



    Integer[] imageId = {
            R.drawable.subway,
            R.drawable.chipotle,
            R.drawable.polotropical,
            R.drawable.wendys,
            R.drawable.mcdonals
    };


    Integer [] drawerIconId = {
            R.drawable.ic_action_location_searching,
            R.drawable.ic_action_settings,
            R.drawable.ic_action_help,
            R.drawable.ic_action_favorite
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mTitle = getTitle();
        mDrawerTitle = "Settings";

        mNavigationTitles = getResources().getStringArray(R.array.navigation_array);

        // Loading the full drawer Layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Getting a reference to the sidebar drawer ( Title + ListView )
        mDrawer = ( LinearLayout) findViewById(R.id.drawer);

        // Loading the Drawer list this will hold all the list items in the drawer
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();


        for(int i=0;i<4;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(ICONNAME, mNavigationTitles[i]);
            //hm.put(COUNT, mCount[i]);
            hm.put(ICONIMG, Integer.toString(drawerIconId[i]) );
            mList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { ICONIMG,ICONNAME};

        // Ids of views in listview_layout
        int[] to = { R.id.iconimage , R.id.iconname};

        // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);


        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(R.color.gray));
        //selector.addState(new int[]{-android.R.attr.state_pressed}, new ColorDrawable(R.color.red));

        mDrawerList.setSelector(selector);


        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Setting the adapter with the Titles
        mDrawerList.setAdapter(mAdapter);
        /*mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationTitles));
*/
        // Setting the drawer listner when some item is clicked in the list.
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

  //      List<Restaurant> restaurantList = RestaurantModel.getRestList(database);

        // Initilializing the adapter information for restaurant list
        ArrayList<HashMap<String, String>> restaurantlistdetails = new ArrayList<HashMap<String, String>>();
        String [] key_array =  getResources().getStringArray(R.array.restaurant_list_key);
        String [] restaurantlist_array =  getResources().getStringArray(R.array.restaurant_list);

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(key_array[0], imageId[i].toString());
            map.put(key_array[1], restaurantlist_array[i]);
            restaurantlistdetails.add(map);
        }

        showFragment ();

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    public void showFragment() {

        // Initilializing the adapter information for restaurant list
        ArrayList<HashMap<String, String>> restaurantlistdetails = new ArrayList<HashMap<String, String>>();
        String [] key_array =  getResources().getStringArray(R.array.restaurant_list_key);
        String [] restaurantlist_array =  getResources().getStringArray(R.array.restaurant_list);

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(key_array[0], imageId[i].toString());
            map.put(key_array[1], restaurantlist_array[i]);
            restaurantlistdetails.add(map);
        }

        // Creating a fragment object
        Fragment detail;
        detail = new RestaurantListViewFragment ();
         // Creating a Bundle object
        Bundle data = new Bundle();
        data.putSerializable("data", restaurantlistdetails);
        // Setting the position to the fragment
        detail.setArguments(data);
        // Getting reference to the FragmentManager
        FragmentManager fragmentManager = getFragmentManager();
        // Creating a fragment transaction
        fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    // This show which Item is selected in the Navigation Bar.
    private void selectItem(int position) {

        if(position != 0) {
            Toast.makeText(this, "This functionality is not currently supported.", Toast.LENGTH_SHORT).show();
        }

        /*if (position == 1) {

            Intent settingsintent = new Intent(NearByRestaurants.this, SettingsActivity.class);
            settingsintent.putExtra("key", "Rohit");
            startActivity(settingsintent);

            //return;
        }*/

        //mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_near_by_restaurants, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Handle action buttons
        switch(item.getItemId()) {

            case R.id.icon:
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
                if (drawerOpen) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else  {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;

            case R.id.action_settings:
                Toast.makeText(NearByRestaurants.this, "You Clicked at ", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_websearch:
                // create intent to perform web search for this planet
                /*Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }*/
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(mDrawer)){
                    mDrawerLayout.closeDrawer(mDrawer);
                } else {
                    mDrawerLayout.openDrawer(mDrawer);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
