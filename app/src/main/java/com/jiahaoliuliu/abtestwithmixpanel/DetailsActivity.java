package com.jiahaoliuliu.abtestwithmixpanel;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jiahaoliuliu.abtestwithmixpanel.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private AbTestManager mAbTestManager;
    private ActivityDetailsBinding activityDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        // Update the toolbar
        setSupportActionBar(activityDetailsBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAbTestManager = new AbTestManager();
        applyABTest();
    }


    private void applyABTest() {
        AbTestManager.AbTestResult abTestResult = mAbTestManager.getAbTestResult();

        Log.d(TAG, "The ab test result is " + abTestResult);

        switch (abTestResult) {
            case SHOW_ADS_DETAILS:
                activityDetailsBinding.title.setText("Buy 100g of Chicke for 20AED!");
                break;
            case SHOW_WARNING:
                activityDetailsBinding.title.setText("Warning! You don't have more money!");
                break;
            default:
            case DEFAULT:
                activityDetailsBinding.title.setText("Have a good day!");
                break;
        }
    }

    // Action bar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}