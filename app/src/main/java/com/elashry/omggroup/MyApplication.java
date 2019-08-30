package com.elashry.omggroup;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;


/**
 * Created by ravi on 25/12/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
    }
}
