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

    // A/B test values
    private Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Show ads details", false);
    private Tweak<Boolean> showWarning = MixpanelAPI.booleanTweak("Show warning details", false);

    private TextView mShowAdsTV;
    private TextView mShowWarningsTV;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Link the views
        mTitleTextView = (TextView) findViewById(R.id.title);
        mShowAdsTV = (TextView) findViewById(R.id.show_ads_tv);
        mShowWarningsTV = (TextView) findViewById(R.id.show_warnings_tv);

        getABTest();
    }


    private void getABTest() {
        Log.d(TAG, "Checking AB test values: ShowAds details: " + showAds.get() + ", showWarning details: " +
                showWarning.get());

        mShowAdsTV.setText("Show ads details: " + showAds.get());
        mShowWarningsTV.setText("Show warnings details: " + showWarning.get());

        if (showAds.get()) {
            mTitleTextView.setText("Buy 100g of Chicke for 20AED!");
        } else if (showWarning.get()) {
            mTitleTextView.setText("Warning! You don't have more money!");
        } else {
            mTitleTextView.setText("Have a good day!");
        }
    }

}
