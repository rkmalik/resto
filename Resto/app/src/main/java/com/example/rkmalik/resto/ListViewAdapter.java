package com.example.rkmalik.resto;

import java.util.List;

import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.resto.FavoritesFragment;

/**
 * Created by shashankranjan on 3/14/15.
 */
public class ListViewAdapter implements ListAdapter{
    private Context _context;
    private List<FoodItem> favItems;
    private RestoSoundPlayer player;
    private int restId;

    public ListViewAdapter(Context context, List<FoodItem> favItems, int restId){
        this._context = context;
        this.favItems = favItems;
        player = new RestoSoundPlayer();
        this.restId = restId;
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
    public Object getItem(int i) {
        return this.favItems.get(i);
    }

    public String getPronun(int i) { return  this.favItems.get(i).getPhoneticName(); }

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
        txtListChild.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(_context, FoodItemDetailActivity.class);
                detailIntent.putExtra("id", foodItem.getId());
                detailIntent.putExtra("restId", restId);
                _context.startActivity(detailIntent);
            }
        });

        TextView txtListPronun = (TextView) convertView.findViewById(R.id.listItemPronun1);
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

        ImageView imgVegNonVeg = (ImageView) convertView.findViewById(R.id.image_vegnoveg1);
        imgVegNonVeg.setImageResource(foodItem.isVeg()?R.drawable.veg_icon:R.drawable.non_veg_icon);

       /* CheckBox imgFavButton = (CheckBox) convertView.findViewById(R.id.imageBtn_favorite);
        imgFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Favorite Clicked 1");
                Toast.makeText(_context, "Favorite Clicked", Toast.LENGTH_SHORT);
            }
        });*/

        ImageView imgSpkButton = (ImageView) convertView.findViewById(R.id.imageBtn_speaker1);
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
}
