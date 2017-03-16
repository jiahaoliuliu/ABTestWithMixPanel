package com.jiahaoliuliu.abtestwithmixpanel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private TextView mTitleTextView;

    private AbTestManager mAbTestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Link the views
        mTitleTextView = (TextView) findViewById(R.id.title);

        mAbTestManager = new AbTestManager();

        getABTest();
    }


    private void getABTest() {
        AbTestManager.AbTestResult abTestResult = mAbTestManager.getAbTestResult();

        Log.d(TAG, "The ab test result is " + abTestResult);

        switch (abTestResult) {
            case SHOW_ADS_DETAILS:
                mTitleTextView.setText("Buy 100g of Chicke for 20AED!");
                break;
            case SHOW_WARNING:
                mTitleTextView.setText("Warning! You don't have more money!");
                break;
            default:
            case DEFAULT:
                mTitleTextView.setText("Have a good day!");
                break;
        }
    }

}
