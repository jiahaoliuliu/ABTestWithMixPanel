package com.jiahaoliuliu.abtestwithmixpanel;

import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

/**
 * Created by jiahaoliu on 3/16/17.
 */

public class AbTestManager {

    private static final String TAG = "AbTestManager";

    public enum AbTestResult {
        DEFAULT, SHOW_ADS_DETAILS, SHOW_WARNING
    }

    // A/B test values
    private static Tweak<Boolean> isAbTestRunning = MixpanelAPI.booleanTweak("Is ab test running", false);
    private static Tweak<Boolean> showAdsDetails = MixpanelAPI.booleanTweak("Show ads details", false);
    private static Tweak<Boolean> showWarningDetails = MixpanelAPI.booleanTweak("Show warning details", false);

    public AbTestManager() {}

    public AbTestResult getAbTestResult() {
        Log.v(TAG, "Show Ads details " + showAdsDetails.get() + ", Show warning details: " + showWarningDetails.get());

        // Check if the AB test is running. If not, do not show any of the variants
        if (!isAbTestRunning.get()) {
            return AbTestResult.DEFAULT;
        }

        if (showAdsDetails.get()) {
            return AbTestResult.SHOW_ADS_DETAILS;
        } else if (showWarningDetails.get()) {
            return AbTestResult.SHOW_WARNING;
        }

        return AbTestResult.DEFAULT;
    }


}
