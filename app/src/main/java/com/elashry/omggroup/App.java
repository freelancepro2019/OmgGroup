package com.elashry.omggroup;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fonts.setDefaultFont(this, "DEFAULT", "ar.otf");
        Fonts.setDefaultFont(this, "MONOSPACE", "ar.otf");
        Fonts.setDefaultFont(this, "SERIF", "ar.otf");
        Fonts.setDefaultFont(this, "SANS_SERIF", "ar.otf");



    }
}
