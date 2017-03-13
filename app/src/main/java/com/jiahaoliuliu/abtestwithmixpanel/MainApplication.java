package com.jiahaoliuliu.abtestwithmixpanel;

import android.app.Application;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * Created by jiahaoliu on 3/13/17.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init MixPanel
        MixpanelAPI.getInstance(this, APIToken.MIX_PANEL_TOKEN);
    }
}
