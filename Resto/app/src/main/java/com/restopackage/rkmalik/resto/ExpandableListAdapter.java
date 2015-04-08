package com.restopackage.rkmalik.resto;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.restopackage.rkmalik.data.Category;
import com.restopackage.rkmalik.data.FoodItem;
import com.restopackage.rkmalik.model.DBHelper;
import com.restopackage.rkmalik.model.RestaurantModel;

/**
 * Created by shashankranjan on 3/12/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter implements TextToSpeech.OnInitListener{
    private Context _context;
    private List<Category> _listCategory;
    private int restId;
    private TextToSpeech tts;

    public ExpandableListAdapter(Context context, List<Category> _listCategory, int restId, TextToSpeech tts)
    {
       this._listCategory = _listCategory;
        this._context = context;
        this.restId = restId;
        this.tts = tts;
    }

    @Override
    public int getGroupCount()
    {
        return this._listCategory.size();
    }

    @Override
    public int getChildrenCount(int grpPos)
    {
        return this._listCategory.get(grpPos).getFoodItems().size();
    }

    @Override
    public Object getGroup(int grpPos)
    {
        return this._listCategory.get(grpPos).getName();
    }

    @Override
    public Object getChild(int grpPos, int childPos) {
        return this._listCategory.get(grpPos).getFoodItems().get(childPos).getName();
    }

    public Object getChildImage(int grpPos, int childPos){
        return this._listCategory.get(grpPos).getFoodItems().get(childPos).getMainImage();
    }

    @Override
    public long getGroupId(int grpPos)
    {
        return grpPos;
    }

    @Override
    public long getChildId(int grpPos, int childPos)
    {
        return childPos;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int grpPos, boolean isExpanded, View convertView, ViewGroup parent) {
        final String headerText = (String) getGroup(grpPos);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerText);

        return convertView;
    }

    public String getChildPronun(int grpPos, int childPos){
        return String.valueOf(this._listCategory.get(grpPos).getFoodItems().get(childPos).getPhoneticName());
    }

    @Override
    public View getChildView(final int grpPos, final int childPos, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final FoodItem foodItem = _listCategory.get(grpPos).getFoodItems().get(childPos);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        ImageView imgListChild = (ImageView) convertView.findViewById(R.id.imageView);
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

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(foodItem.getName());
        txtListChild.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListAlias = (TextView) convertView.findViewById(R.id.aliasName);
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
            TextView txtListCalorie = (TextView) convertView.findViewById(R.id.calorieInfo);
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


        ImageView imgVegNonVeg = (ImageView) convertView.findViewById(R.id.image_vegnoveg);
        imgVegNonVeg.setImageResource(foodItem.isVeg()?R.drawable.veg_icon:R.drawable.non_veg_icon);

        CheckBox imgFavButton = (CheckBox) convertView.findViewById(R.id.imageBtn_favorite);
        imgFavButton.setChecked(foodItem.isFav()?true:false);
        imgFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodItem.setFav(!foodItem.isFav());
                UpdateFavourites(foodItem);
                if(foodItem.isFav())
                    Toast.makeText(_context, foodItem.getName() + " is added to favorites", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(_context, foodItem.getName() + " is removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imgSpkButton = (ImageView) convertView.findViewById(R.id.imageBtn_speaker);
        imgSpkButton.setImageResource(R.drawable.icon_audio);
        imgSpkButton.setClickable(true);
        imgSpkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut(foodItem.getName());
            }
        });

        ImageButton forwardBtn = (ImageButton) convertView.findViewById(R.id.forwardButton);
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
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    private void UpdateFavourites(FoodItem foodItem)
    {
        DBHelper dbHelper = new DBHelper(_context.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        RestaurantModel.updateIsFavFoodItem(database, foodItem);
        database.close();
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
