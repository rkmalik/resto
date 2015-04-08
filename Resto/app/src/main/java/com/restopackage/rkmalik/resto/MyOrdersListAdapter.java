package com.restopackage.rkmalik.resto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.restopackage.rkmalik.data.FoodItem;
import com.restopackage.rkmalik.data.Order;
import com.restopackage.rkmalik.model.DBHelper;
import com.restopackage.rkmalik.model.RestaurantModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by shashankranjan on 4/6/15.
 */
public class MyOrdersListAdapter extends BaseExpandableListAdapter implements TextToSpeech.OnInitListener {
    private Activity _activity;
    private List<Order> _orderList;
    private int restId;
    private TextToSpeech tts;
    DBHelper dbHelper;
    private Fragment _fragment;

    public MyOrdersListAdapter(Activity activity, Fragment fragment, List<Order> orderList, int restId, TextToSpeech tts)
    {
        this._orderList = orderList;
        this._activity = activity;
        this._fragment = fragment;
        this.restId = restId;
        this.tts = tts;
    }

    @Override
    public int getGroupCount() {
        return this._orderList.size();
    }

    @Override
    public int getChildrenCount(int grpPos) {
        if(this._orderList.size() > 0) {
            return this._orderList.get(grpPos).getIngredients().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        if(this._orderList.size() > 0) {
            return this._orderList.get(i).getName();
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int i, int i2) {
        if(!this.isListEmpty()) {
            return this._orderList.get(i).getIngredients().get(i2).getName();
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
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        //if(!isListEmpty()) {
            final String headerText = (String) getGroup(i);

            if (view == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = infalInflater.inflate(R.layout.orders_list_group, null);
            }

            TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerText);

            ImageButton delButton = (ImageButton) view.findViewById(R.id.del_button);
            delButton.setImageResource(R.mipmap.ic_delete);
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelete(i);
                }
            });
            delButton.setFocusable(false);
       // }
        return view;
    }

    private void confirmDelete(final int orderIndex){
        new AlertDialog.Builder(_activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Order")
                .setMessage("Do you want to delete order '" + _orderList.get(orderIndex).getName() +"'")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                          deleteOrder(orderIndex);
                    }
                }).setNegativeButton("No", null)
                .show();
    }

    private void deleteOrder(int orderIndex){
        dbHelper = new DBHelper(_activity.getApplicationContext());
        SQLiteDatabase database = dbHelper.openDatabase();
        Order order = _orderList.get(orderIndex);
        RestaurantModel.removeOrder(database, order.getId());
        database.close();
        Toast.makeText(_activity, "Order " + order.getName() + " is deleted", Toast.LENGTH_SHORT).show();
        _fragment.onResume();
    }

    @Override
    public View getChildView(int grpPos, int childPos, boolean b, View convertView, ViewGroup viewGroup) {
        //if(!isListEmpty()) {
            final FoodItem foodItem = _orderList.get(grpPos).getIngredients().get(childPos);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            ImageView imgListChild = (ImageView) convertView.findViewById(R.id.imageView);
            imgListChild.setImageBitmap(foodItem.getMainImage());

            imgListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(_activity, FoodItemDetailActivity.class);
                    detailIntent.putExtra("id", foodItem.getId());
                    detailIntent.putExtra("restId", restId);
                    _activity.startActivity(detailIntent);

                }
            });

            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(foodItem.getName());
            txtListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(_activity, FoodItemDetailActivity.class);
                    detailIntent.putExtra("id", foodItem.getId());
                    detailIntent.putExtra("restId", restId);
                    _activity.startActivity(detailIntent);
                }
            });

            TextView txtListAlias = (TextView) convertView.findViewById(R.id.aliasName);
            txtListAlias.setText(foodItem.getLocalName());
            txtListAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(_activity, FoodItemDetailActivity.class);
                    detailIntent.putExtra("id", foodItem.getId());
                    detailIntent.putExtra("restId", restId);
                    _activity.startActivity(detailIntent);
                }
            });

            if (foodItem.getServingSize() != null) {
                TextView txtListCalorie = (TextView) convertView.findViewById(R.id.calorieInfo);
                txtListCalorie.setText(foodItem.getCalories() + " calories per " + foodItem.getServingSize());
                txtListCalorie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent detailIntent = new Intent(_activity, FoodItemDetailActivity.class);
                        detailIntent.putExtra("id", foodItem.getId());
                        detailIntent.putExtra("restId", restId);
                        _activity.startActivity(detailIntent);
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
                    Intent detailIntent = new Intent(_activity, FoodItemDetailActivity.class);
                    detailIntent.putExtra("id", foodItem.getId());
                    detailIntent.putExtra("restId", restId);
                    _activity.startActivity(detailIntent);
                }
            });
        //}
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
        AudioManager am = (AudioManager)_activity.getSystemService(Context.AUDIO_SERVICE);
        int amStreamMusicMaxVol = am.getStreamVolume(am.STREAM_VOICE_CALL);
        am.setStreamVolume(am.STREAM_VOICE_CALL, amStreamMusicMaxVol, 0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    private boolean isListEmpty(){
        return (this._orderList.size() <= 0);
    }
}
