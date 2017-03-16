package com.jiahaoliuliu.abtestwithmixpanel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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

    private static final int REQUEST_READ_PHONE_STATE = 1;

    private MixpanelAPI mixpanel;
    private Context mContext;
    private AbTestManager mAbTestManager;

    // A/B test values
    private Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Show ads", false);
    private Tweak<Boolean> showWarning = MixpanelAPI.booleanTweak("Show warning", false);

    // Views
    private TextView mShowAdsTV;
    private TextView mShowWarningsTV;
    private TextView mTitleTextView;
    private Button mSendEventButton;
    private Button mDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init variables
        mixpanel = MixpanelAPI.getInstance(this, APIToken.MIX_PANEL_TOKEN);

        mContext = this;
        mAbTestManager = new AbTestManager();

        // Link the views
        mTitleTextView = (TextView) findViewById(R.id.title);
        mShowAdsTV = (TextView) findViewById(R.id.show_ads_tv);
        mShowWarningsTV = (TextView) findViewById(R.id.show_warnings_tv);
        mSendEventButton = (Button) findViewById(R.id.send_event_btn);
        mSendEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Refresh the content
                sendDummyEvent();
            }
        });

        mDetailsButton = (Button) findViewById(R.id.details_btn);
        mDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDetails();
            }
        });

        // Request for permission
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshContent();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                    mixpanel.identify(tManager.getDeviceId());
                    mixpanel.getPeople().identify(tManager.getDeviceId());
                }
                break;

            default:
                break;
        }
    }
}
