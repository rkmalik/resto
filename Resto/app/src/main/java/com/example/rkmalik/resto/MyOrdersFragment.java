package com.example.rkmalik.resto;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.example.rkmalik.data.Order;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyOrdersFragment extends Fragment implements TextToSpeech.OnInitListener{
    MyOrdersListAdapter listAdapter;
    ExpandableListView listView;
    ImageButton order_add_button;
    List<Order> orderList;
    Activity fragActivity;
    DBHelper dbHelper;
    int id;
    int openGroupIndex = 0;
    private TextToSpeech tts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);
        fragActivity = this.getActivity();

        Intent intent = this.getActivity().getIntent();
        id = intent.getIntExtra("restId", 0);

        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        orderList = RestaurantModel.getOrdersForRestaurant(database, id);
        System.out.println("orderlist size = " + orderList.size());
        database.close();

        tts = new TextToSpeech(this.getActivity(), this);

        listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView3);

        listAdapter = new MyOrdersListAdapter(fragActivity, orderList, id, tts);
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

        order_add_button = (ImageButton) rootView.findViewById(R.id.order_add_button);
        order_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragActivity, BuildOrderPage.class);
                intent.putExtra("restId", id);
                fragActivity.startActivity(intent);
            }
        });

        if(openGroupIndex > -1) {
            listView.expandGroup(openGroupIndex);
        }
        return rootView;
    }

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

    @Override
    public void onResume()
    {
        super.onResume();
//        dbHelper = new DBHelper(this.getActivity().getApplicationContext());
//        SQLiteDatabase database = dbHelper.openDatabase();
//        ordercategoryList = RestaurantModel.getCategoriesBasedOnRestaurant(database, id);
//
//        for(int i=0; i<ordercategoryList.size(); i++)
//        {
//            Category category = ordercategoryList.get(i);
//            category.setFoodItems(RestaurantModel.getFoodItemsBasedOnCategory(database, category.getId()));
//        }
//
//        database.close();
      /*  orderList = new ArrayList<Order>();

        listAdapter = new MyOrdersListAdapter(fragActivity, orderList, id, tts);
        listView.setAdapter(listAdapter);
        if(openGroupIndex > -1){
            listView.expandGroup(openGroupIndex);
        }*/

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
