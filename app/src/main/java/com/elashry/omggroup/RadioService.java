package com.elashry.omggroup;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

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
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            mp.setDataSource(url);
            mp.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();

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
        if (mp!=null)
        {
            mp.stop();

        }
        stopSelf();
    }
}
