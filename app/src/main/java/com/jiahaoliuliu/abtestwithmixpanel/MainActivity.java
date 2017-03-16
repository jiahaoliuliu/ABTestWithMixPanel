package com.jiahaoliuliu.abtestwithmixpanel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ABTestWithMixPanel";
    private MixpanelAPI mixpanel;
    private Context mContext;

    // A/B test values
    private Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Show ads", false);
    private Tweak<Boolean> showWarning = MixpanelAPI.booleanTweak("Show warning", false);

    // Views
    private TextView mShowAdsTV;
    private TextView mShowWarningsTV;
    private TextView mTitleTextView;
    private Button mRefreshButton;
    private Button mDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init variables
        mixpanel = MixpanelAPI.getInstance(this, APIToken.MIX_PANEL_TOKEN);
        mContext = this;

        // Link the views
        mTitleTextView = (TextView) findViewById(R.id.title);
        mShowAdsTV = (TextView) findViewById(R.id.show_ads_tv);
        mShowWarningsTV = (TextView) findViewById(R.id.show_warnings_tv);
        mRefreshButton = (Button) findViewById(R.id.refresh_btn);
        mRefreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Refresh the content
                refreshContent();
            }
        });

        mDetailsButton = (Button) findViewById(R.id.details_btn);
        mDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDetails();
            }
        });

//        sendDummyEvent();

        getABTest();
    }

    private void sendDummyEvent() {
        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("MainActivity - onCreate called", props);
        } catch (JSONException e) {
            Log.e(TAG, "Unable to add properties to JSONObject", e);
        }
    }

    private void refreshContent() {
        showAds = MixpanelAPI.booleanTweak("Show ads", false);
        showWarning = MixpanelAPI.booleanTweak("Show warning", false);
        getABTest();
    }

    private void getABTest() {
        Log.d(TAG, "Checking AB test values: ShowAds: " + showAds.get() + ", showWarning: " +
            showWarning.get());

        mShowAdsTV.setText("Show ads: " + showAds.get());
        mShowWarningsTV.setText("Show warnings: " + showWarning.get());

        if (showAds.get()) {
            mTitleTextView.setText("Buy 100g of Chicke for 20AED!");
        } else if (showWarning.get()) {
            mTitleTextView.setText("Warning! You don't have more money!");
        } else {
            mTitleTextView.setText("Have a good day!");
        }
    }

    private void openDetails() {
        Intent startDetailsIntent = new Intent(mContext, DetailsActivity.class);
        startActivity(startDetailsIntent);
    }
}
