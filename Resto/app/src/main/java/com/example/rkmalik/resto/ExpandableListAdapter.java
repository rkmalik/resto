package com.example.rkmalik.resto;

import java.util.List;
import java.util.HashMap;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.model.DBHelper;
import com.example.rkmalik.model.RestaurantModel;

import org.w3c.dom.Text;

/**
 * Created by shashankranjan on 3/12/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private List<Category> _listCategory;
    private int restId;

    //public ExpandableListAdapter(Context context, List<Category> _listCategory){
      //  this._context = context;
        //this._listCategory = _listCategory;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listChildData;
    private HashMap<String, List<Integer>> _listImageIds;
    private HashMap<String, List<String>> _listChildPronun;
    private RestoSoundPlayer player;

    public ExpandableListAdapter(Context context, List<Category> _listCategory, int restId)
    {
       this._listCategory = _listCategory;
        this._context = context;
        this.restId = restId;
        player = new RestoSoundPlayer();
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

        TextView txtListPronun = (TextView) convertView.findViewById(R.id.listItemPronun);
        txtListPronun.setText(foodItem.getPhoneticName());
        txtListPronun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

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
                    Toast.makeText(_context, "Added to Favorites", Toast.LENGTH_SHORT);
                else
                    Toast.makeText(_context, "Removed from Favorites", Toast.LENGTH_SHORT);
            }
        });

        ImageView imgSpkButton = (ImageView) convertView.findViewById(R.id.imageBtn_speaker);
        imgSpkButton.setImageResource(R.drawable.icon_audio);
        imgSpkButton.setClickable(true);
        imgSpkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Speaker Clicked");
                Toast.makeText(_context, "Speaker Clicked", Toast.LENGTH_SHORT);
                player.playSound(_context, R.raw.croissant);
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
}
