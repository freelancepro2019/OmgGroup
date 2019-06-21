package com.elashry.omggroup;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class TvActivity extends AppCompatActivity {

    VideoView vidView;
    private MediaController vidControl;
    private MediaPlayer mp;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        vidView = findViewById(R.id.myVideo);
        final ProgressBar progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.gray), PorterDuff.Mode.SRC_IN);

        getDataFromIntent();

        vidView.setVideoURI(Uri.parse(url));
        vidView.start();
        vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                TvActivity.this.mp = mp;
                progBar.setVisibility(View.GONE);

                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {

                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                            progBar.setVisibility(View.VISIBLE);
                            return true;
                        }

                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                        {
                            progBar.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });


    }

    private void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent!=null)
        {
            url =  intent.getStringExtra("url");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mp!=null)
        {
            mp.release();
        }
        vidView.stopPlayback();


    }
}
