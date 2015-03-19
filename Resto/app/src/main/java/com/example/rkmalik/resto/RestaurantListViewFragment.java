package com.example.rkmalik.resto;

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rkmalik.data.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListViewFragment extends ListFragment {
    private List<Rest_ListViewItem_Holder> mItems;        // ListView items list
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer[] imageId = {
                R.drawable.subway,
                R.drawable.chipotle,
                R.drawable.polotropical,
                R.drawable.wendys,
                R.drawable.mcdonals,
        };

        String [] key_array =  getResources().getStringArray(R.array.restaurant_list);
        
        // initialize the items list
        mItems = new ArrayList<Rest_ListViewItem_Holder>();
        Resources resources = getResources();
        // enable ActionBar app icon to behave as action to toggle nav drawer
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayUseLogoEnabled(true);
        /*mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(R.drawable.subway), key_array[0], "Rohit Kumar Malik"));
        mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(R.drawable.chipotle), key_array[1], "Rohit Kumar Malik"));
        mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(R.drawable.polotropical), key_array[2], "Rohit Kumar Malik"));
        mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(R.drawable.wendys), key_array[3], "Rohit Kumar Malik"));
        mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(imageId[4]), key_array[4], "Rohit Kumar Malik"));*/

        for (int i = 0; i < imageId.length; i++) {
            mItems.add(new Rest_ListViewItem_Holder(resources.getDrawable(imageId[i]), key_array[i], "Rohit Kumar Malik"));
        }

        // initialize and set the list adapter
        setListAdapter(new RestaurantListAdapter(getActivity(), mItems));
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }
 
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        Rest_ListViewItem_Holder item = mItems.get(position);
        
        // do something
       // Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
        Intent foodItemsIntent = new Intent(getActivity(), FoodItems.class);
        foodItemsIntent.putExtra("name", item.title);
        startActivity(foodItemsIntent);
    }
}