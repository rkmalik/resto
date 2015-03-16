package com.example.rkmalik.resto;

import java.util.List;
import java.util.HashMap;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

/**
 * Created by shashankranjan on 3/12/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listChildData;
    private HashMap<String, List<Integer>> _listImageIds;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData,
                                 HashMap<String, List<Integer>> listImageIds){
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listChildData = listChildData;
        this._listImageIds = listImageIds;
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int grpPos) {
        return this._listChildData.get(this._listDataHeader.get(grpPos)).size();
    }

    @Override
    public Object getGroup(int grpPos) {
        return this._listDataHeader.get(grpPos);
    }

    @Override
    public Object getChild(int grpPos, int childPos) {
        return this._listChildData.get(this._listDataHeader.get(grpPos)).get(childPos);
    }

    public Object getChildImage(int grpPos, int childPos){
        return this._listImageIds.get(this._listDataHeader.get(grpPos)).get(childPos);
    }

    @Override
    public long getGroupId(int grpPos) {
        return grpPos;
    }

    @Override
    public long getChildId(int grpPos, int childPos) {
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
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

    @Override
    public View getChildView(int grpPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(grpPos, childPos);
        //final Integer childImg = (Integer) getChildImage(grpPos, childPos);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        //ImageView imgListChild = (ImageView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        //imgListChild.setImageResource(childImg);
        
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}
