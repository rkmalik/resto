package com.example.rkmalik.resto;

import java.util.List;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;
import com.example.rkmalik.resto.FavoritesFragment;

/**
 * Created by shashankranjan on 3/14/15.
 */
public class ListViewAdapter implements ListAdapter{
    private Context _context;
    private List<String> _listData;

    public ListViewAdapter(Context context, List<String> listData){
        this._context = context;
        this._listData = listData;
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

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String dataText = (String) getItem(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.favorite_item, null);
        }

        TextView txtItem = (TextView) view.findViewById(R.id.lblListItem);
        txtItem.setText(dataText);

        return view;
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
