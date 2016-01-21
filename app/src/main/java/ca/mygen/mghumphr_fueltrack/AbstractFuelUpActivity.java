package ca.mygen.mghumphr_fueltrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by martin on 20/01/16.
 */
public abstract class AbstractFuelUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FuelUp.load(getApplicationContext());
    }

    @Override
    protected void onResume(){
        super.onResume();
        FuelUp.load(getApplicationContext());
    }

    @Override
    protected void onPause(){
        FuelUp.save(getApplicationContext());
        super.onPause();
    }

}
