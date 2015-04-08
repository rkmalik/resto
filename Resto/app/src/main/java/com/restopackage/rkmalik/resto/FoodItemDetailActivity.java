package com.restopackage.rkmalik.resto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by shashankranjan on 3/16/15.
 */
public class FoodItemDetailActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            int restId = getIntent().getIntExtra("restId", 0);

            String restName = getIntent().getStringExtra("restName");
            arguments.putInt("restId", restId);
            arguments.putInt("id", getIntent().getIntExtra("id", 0));

            Intent intent = new Intent();
            intent.putExtra("restId", restId);
            setResult(RESULT_OK, intent);

            setTitle(restName);

            FoodItemDetailFragment fragment = new FoodItemDetailFragment();
            fragment.setArguments(arguments);
              getSupportFragmentManager().beginTransaction()
                      .add(R.id.fooditem_detail_container, fragment)
                      .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
