package com.restopackage.rkmalik.resto;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.restopackage.rkmalik.data.FoodItem;
import com.restopackage.rkmalik.model.DBHelper;
import com.restopackage.rkmalik.model.RestaurantModel;

/**
 * Created by shashankranjan on 3/14/15.
 */
public class ListViewAdapter extends ArrayAdapter<FoodItem> implements ListAdapter, TextToSpeech.OnInitListener{
    private Context _context;
    private List<FoodItem> favItems;
    //private RestoSoundPlayer player;
    private int restId;
    DBHelper dbHelper;
    private Fragment fragment;
    private TextToSpeech tts;

    public ListViewAdapter(Context context, int restId, List<FoodItem> favItems, Fragment fragment, TextToSpeech tts){
        super(context, restId, favItems);
        this._context = context;
        this.favItems = favItems;
        //player = new RestoSoundPlayer();
        this.restId = restId;
        this.fragment = fragment;
        this.tts = tts;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return this.favItems.size();
    }

    @Override
    public FoodItem getItem(int i) {
        return this.favItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final FoodItem foodItem = favItems.get(i);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_item, null);
        }

        ImageView imgListChild = (ImageView) convertView.findViewById(R.id.favimageView);
        imgListChild.setImageBitmap(foodItem.getMainImage());
        imgListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem1);
        txtListChild.setText(foodItem.getName());
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListAlias = (TextView) convertView.findViewById(R.id.aliasName1);
        txtListAlias.setText(foodItem.getLocalName());
        txtListAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        if(foodItem.getServingSize()!=null)
        {
            TextView txtListCalorie = (TextView) convertView.findViewById(R.id.calorieInfo1);
            txtListCalorie.setText(foodItem.getCalories() + " calories per " + foodItem.getServingSize());
            txtListCalorie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                    detailIntent.putExtra("id", foodItem.getId());
                    detailIntent.putExtra("restId", restId);
                    _context.startActivity(detailIntent);
                }
            });
        }

        ImageView imgVegNonVeg = (ImageView) convertView.findViewById(R.id.image_vegnoveg1);
        imgVegNonVeg.setImageResource(foodItem.isVeg()?R.drawable.veg_icon:R.drawable.non_veg_icon);

        ImageView imgSpkButton = (ImageView) convertView.findViewById(R.id.imageBtn_speaker1);
        imgSpkButton.setImageResource(R.drawable.icon_audio);
        imgSpkButton.setClickable(true);
        imgSpkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut(foodItem.getName());
            }
        });

        ImageView deleteButton = (ImageView) convertView.findViewById(R.id.imageBtn_delete);
        deleteButton.setImageResource(R.mipmap.ic_delete);
        deleteButton.setClickable(true);
        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(_context);
                SQLiteDatabase database = dbHelper.openDatabase();
                foodItem.setFav(false);
                RestaurantModel.updateIsFavFoodItem(database, foodItem);
                database.close();
                favItems.remove(i);
                Toast.makeText(_context, foodItem.getName() + " is removed from favorites", Toast.LENGTH_SHORT).show();
                fragment.onResume();
            }
        });

        ImageButton forwardBtn = (ImageButton) convertView.findViewById(R.id.forwardButton1);
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        int sz = this.favItems.size();
        if(sz > 0){
            return true;
        }
        return false;
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

    private void speakOut(String text) {
        AudioManager am = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        int amStreamMusicMaxVol = am.getStreamVolume(am.STREAM_VOICE_CALL);
        am.setStreamVolume(am.STREAM_VOICE_CALL, amStreamMusicMaxVol, 0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
