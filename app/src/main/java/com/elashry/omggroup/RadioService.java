package com.elashry.omggroup;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class RadioService extends Service {

    private String url;
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    private void setUpMediaPlayer(String url)
    {
        Log.e("url",url);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        setUpMediaPlayer(url);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
