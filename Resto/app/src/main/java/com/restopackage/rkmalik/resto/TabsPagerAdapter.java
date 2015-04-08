package com.restopackage.rkmalik.resto;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by shashankranjan on 3/13/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new MenuItemsFragment();
            case 1:
                return new FavoritesFragment();
            case 2:
                return new MyOrdersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
