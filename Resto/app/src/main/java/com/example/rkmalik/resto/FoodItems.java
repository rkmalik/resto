package com.example.rkmalik.resto;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import static android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS;


public class FoodItems extends ActionBarActivity implements ActionBar.TabListener, android.app.ActionBar.TabListener{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    FavoritesFragment favoritesFragment;
    MenuItemsFragment menuItemsFragment;
    MyOrdersFragment ordersFragment;

    //Tab titles
    private String[] tabs = {"Menu", "Favorites", "My Orders"};

    static final int REFRESH_PAGE_MYORDERS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar(); // requires API 11
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false); //requires API 14
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //requires API 11

        //String restName = savedInstanceState.get("restName").toString();

        //setTitle("Restaurant");

      //  getActionBar().setTitle();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                System.out.println("Page is selected");
                if(position == 1) //fav frag pos
                {
                    favoritesFragment = (FavoritesFragment) mAdapter.instantiateItem(viewPager, position);
                    favoritesFragment.onResume();
                } else if(position == 0) {
                    menuItemsFragment = (MenuItemsFragment) mAdapter.instantiateItem(viewPager, position);
                    menuItemsFragment.onResume();
                } else {
                    ordersFragment = (MyOrdersFragment) mAdapter.instantiateItem(viewPager, position);
                    ordersFragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Add tabs
        for(String tab_name : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_items, menu);
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
            NavUtils.navigateUpTo(this, new Intent(this, NearByRestaurants.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_food_items, container, false);
            return rootView;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println("Food Items page, request Code = " + requestCode + ", resultCode = " + resultCode);
        if(requestCode == REFRESH_PAGE_MYORDERS){
            viewPager.setCurrentItem(2);
            ordersFragment.onResume();
        }
    }
}
