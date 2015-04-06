package com.example.rkmalik.resto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.FoodItem;

import java.util.List;
import java.util.Locale;

/**
 * Created by shashankranjan on 4/6/15.
 */
public class MyOrdersListAdapter extends BaseExpandableListAdapter implements TextToSpeech.OnInitListener {
    private Context _context;
    private List<Category> _orderCategory;
    private int restId;
    private TextToSpeech tts;

    public MyOrdersListAdapter(Context context, List<Category> _orderCategory, int restId, TextToSpeech tts)
    {
        this._orderCategory = _orderCategory;
        this._context = context;
        this.restId = restId;
        this.tts = tts;
    }

    @Override
    public int getGroupCount() {
        return this._orderCategory.size();
    }

    @Override
    public int getChildrenCount(int grpPos) {
        if(this._orderCategory.size() > 0) {
            return this._orderCategory.get(grpPos).getFoodItems().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        if(this._orderCategory.size() > 0) {
            return this._orderCategory.get(i).getName();
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int i, int i2) {
        if(!this.isListEmpty()) {
            return this._orderCategory.get(i).getFoodItems().get(i2).getName();
        } else {
            return null;
        }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(!isListEmpty()) {
            final String headerText = (String) getGroup(i);

            if (view == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerText);
        }
        return view;
    }

    @Override
    public View getChildView(int grpPos, int childPos, boolean b, View convertView, ViewGroup viewGroup) {
        if(!isListEmpty()) {
            final FoodItem foodItem = _orderCategory.get(grpPos).getFoodItems().get(childPos);

            if (convertView == null) {
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
            txtListChild.setOnClickListener(new View.OnClickListener() {
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

            if (foodItem.getServingSize() != null) {
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
            imgVegNonVeg.setImageResource(foodItem.isVeg() ? R.drawable.veg_icon : R.drawable.non_veg_icon);

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
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
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

    private boolean isListEmpty(){
        return (this._orderCategory.size() <= 0);
    }
}
