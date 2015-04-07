package com.example.rkmalik.resto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

/**
 * Created by shashankranjan on 4/7/15.
 */
public class BuildOrderPageOneAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<Category> _orderCategory;
    private int _restId;

    public BuildOrderPageOneAdapter(Context context, List<Category> orderCategory, int restId){
        _context = context;
        _orderCategory = orderCategory;
        _restId = restId;
    }

    @Override
    public int getGroupCount() {
        return this._orderCategory.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this._orderCategory.get(i).getFoodItems().size();
    }

    @Override
    public Object getGroup(int i) {
        return this._orderCategory.get(i).getName();
    }

    @Override
    public Object getChild(int i, int i2) {
        return this._orderCategory.get(i).getFoodItems().get(i2).getName();
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup){
        if(!this.isListEmpty()){
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
                convertView = infalInflater.inflate(R.layout.buildorder_page_one_item, null);
            }

            ImageView imgListChild = (ImageView) convertView.findViewById(R.id.buildorder_imageView);
            imgListChild.setImageBitmap(foodItem.getMainImage());

            imgListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent step2Intent = new Intent(_context, BuildOrderPageTwo.class);
                    step2Intent.putExtra("id", foodItem.getId());
                    step2Intent.putExtra("restId", _restId);
                    _context.startActivity(step2Intent);

                }
            });

            TextView txtListChild = (TextView) convertView.findViewById(R.id.buildorder_ListItem);
            txtListChild.setText(foodItem.getName());
            txtListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent step2Intent = new Intent(_context, BuildOrderPageTwo.class);
                    step2Intent.putExtra("id", foodItem.getId());
                    step2Intent.putExtra("restId", _restId);
                    _context.startActivity(step2Intent);
                }
            });

            TextView txtListAlias = (TextView) convertView.findViewById(R.id.buildorder_aliasName);
            txtListAlias.setText(foodItem.getLocalName());
            txtListAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent step2Intent = new Intent(_context, BuildOrderPageTwo.class);
                    step2Intent.putExtra("id", foodItem.getId());
                    step2Intent.putExtra("restId", _restId);
                    _context.startActivity(step2Intent);
                }
            });


            ImageView imgVegNonVeg = (ImageView) convertView.findViewById(R.id.buildorder_image_vegnoveg);
            imgVegNonVeg.setImageResource(foodItem.isVeg() ? R.drawable.veg_icon : R.drawable.non_veg_icon);

            ImageButton forwardBtn = (ImageButton) convertView.findViewById(R.id.buildorder_forwardButton);
            forwardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent step2Intent = new Intent(_context, BuildOrderPageTwo.class);
                    step2Intent.putExtra("id", foodItem.getId());
                    step2Intent.putExtra("restId", _restId);
                    _context.startActivity(step2Intent);
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    private boolean isListEmpty(){
        return (this._orderCategory.size() <= 0);
    }
}