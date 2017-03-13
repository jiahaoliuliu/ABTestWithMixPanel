package com.jiahaoliuliu.abtestwithmixpanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ABTestWithMixPanel";
    private MixpanelAPI mixpanel;

    // A/B test values
    private static Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Show ads", false);

    // Views
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mixpanel = MixpanelAPI.getInstance(this, APIToken.MIX_PANEL_TOKEN);

        // Link the views
        mTitleTextView = (TextView) findViewById(R.id.title);

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

    private void getABTest() {
        if (showAds.get()) {
            mTitleTextView.setText("Buy 100g of Chicke for 20AED!");
        } else {
            mTitleTextView.setText("Have a good day!");
        }
    }
}
