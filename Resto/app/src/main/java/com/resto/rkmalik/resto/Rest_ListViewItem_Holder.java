package com.resto.rkmalik.resto;

import android.graphics.drawable.Drawable;

public class Rest_ListViewItem_Holder {
    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    
    public Rest_ListViewItem_Holder(Drawable icon, String title, String description) {
    //public ListViewItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }
}