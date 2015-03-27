package com.example.rkmalik.resto;

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rkmalik.data.Restaurant;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListViewFragment extends ListFragment {
    private List<Rest_ListViewItem_Holder> mItems;        // ListView items list
    private SQLiteDatabase database = null;
    private List<Restaurant> restaurantListFromDB;
    private Location location = null;
    private final String GOOGLE_KEY = "AIzaSyAyd5ykRddddp8ROOFa14rUVExQygBQ-dA";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBHelper dbHelper = new DBHelper(this.getActivity().getApplicationContext());
        //database = dbHelper.openDatabase();
       // dbHelper.onUpgrade(database, 3,4);
        database = dbHelper.openDatabase();
        restaurantListFromDB = RestaurantModel.getRestList(database);
        database.close();

        LocationSettings gps = new LocationSettings(this.getActivity());
        location = gps.getLastLocation();

        new googleplaces().execute();

        // initialize and set the list adapter
       // setListAdapter(new RestaurantListAdapter(getActivity(), restaurantListFromDB));
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
       // Rest_ListViewItem_Holder item = mItems.get(position);

        Restaurant selectedRestaurant = restaurantListFromDB.get(position);
        //Bundle data;
        //data.pu
        
        // do something
       // Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
        Intent foodItemsIntent = new Intent(getActivity(), FoodItems.class);
        foodItemsIntent.putExtra("restId", selectedRestaurant.getId());
       // foodItemsIntent.putExtra("restName", selectedRestaurant.getName());
        //foodItemsIntent.putParcelableArrayListExtra("restaurant", selectedRestaurant);
        startActivity(foodItemsIntent);
    }

    private class googleplaces extends AsyncTask<View, Void, String>
    {
        String temp;

        @Override
        protected String doInBackground(View... urls) {
            temp = makeHttpCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()) + "&rankby=distance&types=restaurant&key=" + GOOGLE_KEY);
            return "";
        }

        @Override
        protected void onPreExecute()
        {}

        @Override
        protected void onPostExecute(String result)
        {
            List<Restaurant> finalRestList = new ArrayList<>();
            List<String> nearByRestFromGoogle = new ArrayList<>();
            if(temp != null)
            {
              /*  nearByRestFromGoogle = parseGooglePlacesResults(temp);

                for(int i=0; i<nearByRestFromGoogle.size(); i++)
                {
                    for(int j=0; j<restaurantListFromDB.size(); j++)
                    {
                        if(nearByRestFromGoogle.get(i).contains(restaurantListFromDB.get(j).getName()))
                            finalRestList.add(restaurantListFromDB.get(j));

                    }
                } */

                setListAdapter(new RestaurantListAdapter(getActivity(), restaurantListFromDB));
            }
        }
    }

    public static String makeHttpCall(String url)
    {
        StringBuffer stringBuffer = new StringBuffer(url);
        String replyString = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(stringBuffer.toString());

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(20);
            int current = 0;
            while((current = bufferedInputStream.read()) != -1)
                byteArrayBuffer.append((byte)current);
            replyString = new String(byteArrayBuffer.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(replyString);
        return replyString.trim();
    }

    private static List<String> parseGooglePlacesResults(final String response)
    {
        List<String> restList = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has("results"))
            {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    if(jsonObject1.has("name"))
                    {
                        restList.add(jsonObject1.optString("name"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return restList;
    }


}
