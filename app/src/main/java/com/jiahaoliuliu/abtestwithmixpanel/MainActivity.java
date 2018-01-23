package com.jiahaoliuliu.abtestwithmixpanel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ABTestWithMixPanel";

    private static final int REQUEST_READ_PHONE_STATE = 1;

    private MixpanelAPI mixpanel;
    private Context mContext;

    // Views
    private Button mSendEventButton;
    private Button mDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init variables
        mixpanel = MixpanelAPI.getInstance(this, APIToken.MIX_PANEL_TOKEN);

        mContext = this;

        // Link the views
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

    private void openDetails() {
        Intent startDetailsIntent = new Intent(mContext, DetailsActivity.class);
        startActivity(startDetailsIntent);
    }

    @SuppressLint("MissingPermission")
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
