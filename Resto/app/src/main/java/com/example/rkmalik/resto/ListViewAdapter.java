package com.example.rkmalik.resto;

import java.util.List;

import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkmalik.resto.FavoritesFragment;

/**
 * Created by shashankranjan on 3/14/15.
 */
public class ListViewAdapter implements ListAdapter{
    private Context _context;
    private List<String> _listData;
    private List<String> _listPronun;

    public ListViewAdapter(Context context, List<String> listData, List<String> listPronun){
        this._context = context;
        this._listData = listData;
        this._listPronun = listPronun;
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
        return this._listData.size();
    }

    @Override
    public Object getItem(int i) {
        return this._listData.get(i);
    }

    public String getPronun(int i) { return  this._listPronun.get(i); }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final String dataText = (String) getItem(i);
        final String pronunText = (String) getPronun(i);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_item, null);
        }

        ImageView imgListChild = (ImageView) convertView.findViewById(R.id.favimageView);
        imgListChild.setImageResource(R.drawable.chipotle);
        imgListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra(FoodItemDetailFragment.ARG_ITEM_ID, dataText);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem1);
        txtListChild.setText(dataText);
        txtListChild.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra(FoodItemDetailFragment.ARG_ITEM_ID, dataText);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListPronun = (TextView) convertView.findViewById(R.id.listItemPronun1);
        txtListPronun.setText(pronunText);
        txtListPronun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra(FoodItemDetailFragment.ARG_ITEM_ID, dataText);
                _context.startActivity(detailIntent);
            }
        });

        ImageView imgVegNonVeg = (ImageView) convertView.findViewById(R.id.image_vegnoveg1);
        imgVegNonVeg.setImageResource(R.drawable.no_image);

        ImageView imgFavButton = (ImageView) convertView.findViewById(R.id.imageBtn_favorite1);
        imgFavButton.setImageResource(R.drawable.ic_action_favorite);
        imgFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Favorite Clicked");
                Toast.makeText(_context, "Favorite Clicked", Toast.LENGTH_SHORT);
            }
        });

        ImageView imgSpkButton = (ImageView) convertView.findViewById(R.id.imageBtn_speaker1);
        imgSpkButton.setImageResource(R.drawable.no_image);
        imgSpkButton.setClickable(true);
        imgSpkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Speaker Clicked");
                Toast.makeText(_context, "Speaker Clicked", Toast.LENGTH_SHORT);
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
        int sz = this._listData.size();
        if(sz > 0){
            return true;
        }
        return false;
    }
}
