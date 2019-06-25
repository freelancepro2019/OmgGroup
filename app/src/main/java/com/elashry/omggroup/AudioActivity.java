package com.elashry.omggroup;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    private ImageView image_play ,image_gif;
    private String  AudioURL;
    private TextView tv_1,tv_2;
    private Typeface typeface;


    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        getDataFromIntent();
        typeface = Typeface.createFromAsset(getAssets(),"ar.otf");
        initView();







    }


    private void setupMedia() {
        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            mediaplayer.setDataSource(AudioURL);
            mediaplayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        image_play = findViewById(R.id.image_play);
        image_gif = findViewById(R.id.image_gif);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);

        tv_1.setTypeface(typeface);
        tv_2.setTypeface(typeface);

        Glide.with(this)
                .asGif()
                .load("https://raw.githubusercontent.com/AhmedMohamedAllam/Omg-Group/master/OMG%20Group/app/waves.gif")
                .into(image_gif);

        image_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isServiceRunning())
                {
                    stop();
                    image_play.setImageResource(R.drawable.play);
                    image_gif.setVisibility(View.INVISIBLE);

                }else
                {
                    play();
                    image_play.setImageResource(R.drawable.pause);
                    image_gif.setVisibility(View.VISIBLE);



                }
            }
        });



        if (isServiceRunning())
        {
            image_play.setImageResource(R.drawable.pause);
            image_gif.setVisibility(View.VISIBLE);



        }else
            {
                image_play.setImageResource(R.drawable.play);
                image_gif.setVisibility(View.INVISIBLE);

            }

    }

    private void play()
    {

        Intent intent = new Intent(this,RadioService.class);
        intent.putExtra("url",AudioURL);
        startService(intent);
    }

    private void stop()
    {
        Intent intent = new Intent(this,RadioService.class);
        stopService(intent);
    }

    private boolean isServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceInfo.service.getClassName().equals(RadioService.class.getName()))
            {
                return true;
            }
        }

        return false;
    }
    private void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent!=null)
        {
            AudioURL =  intent.getStringExtra("url");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaplayer!=null)
        {
            mediaplayer.release();
        }
    }
}
