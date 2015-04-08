package com.resto.rkmalik.resto;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by rkmalik on 3/15/2015.
 */
public class SettingsActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.ic_drawer);
        getActionBar().setDisplayUseLogoEnabled(true);

    }

}
