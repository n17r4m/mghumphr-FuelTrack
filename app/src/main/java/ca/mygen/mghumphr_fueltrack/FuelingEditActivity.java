package ca.mygen.mghumphr_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class FuelingEditActivity extends AbstractFuelUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fueling_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        if (savedInstanceState == null) {
            // Create the edit fragment and add it to the activity
            // using a fragment transaction.
            FuelingEditFragment fragment = new FuelingEditFragment();

            Intent intent = getIntent();
            Bundle arguments = new Bundle();
            if (intent.hasExtra(FuelingEditFragment.ARG_ITEM_ID)) {
                arguments.putString(FuelingEditFragment.ARG_ITEM_ID, intent.getStringExtra(FuelingEditFragment.ARG_ITEM_ID));
            }
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fueling_edit_container, fragment)
                    .commit();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, FuelingListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
