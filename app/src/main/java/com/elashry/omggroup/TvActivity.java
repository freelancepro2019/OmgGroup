package com.elashry.omggroup;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

public class TvActivity extends AppCompatActivity {

    String url;
    private UniversalVideoView mVideoView;
    private UniversalMediaController mMediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        /*final ProgressBar progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.gray), PorterDuff.Mode.SRC_IN);
*/
        getDataFromIntent();

        mVideoView =  findViewById(R.id.videoView);
        mMediaController =  findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mVideoView.setFitXY(true);

        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {

            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading

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




    }
}
