package com.jiahaoliuliu.abtestwithmixpanel;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

/**
 * Created by jiahaoliu on 3/16/17.
 */

public class AbTestManager {

    public enum AbTestResult {
        DEFAULT, SHOW_ADS_DETAILS, SHOW_WARNING
    }

    // A/B test values
    private Tweak<Boolean> showAdsDetails = MixpanelAPI.booleanTweak("Show ads details", false);
    private Tweak<Boolean> showWarningDetails = MixpanelAPI.booleanTweak("Show warning details", false);

    public AbTestManager() {}

    public AbTestResult getAbTestResult() {
        if (showAdsDetails.get()) {
            return AbTestResult.SHOW_ADS_DETAILS;
        } else if (showWarningDetails.get()) {
            return AbTestResult.SHOW_WARNING;
        }

        return AbTestResult.DEFAULT;
    }


}
