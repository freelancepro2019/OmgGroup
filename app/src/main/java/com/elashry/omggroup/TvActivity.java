package com.elashry.omggroup;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class TvActivity extends AppCompatActivity {

    private String url,bg="";
    private JCVideoPlayerStandard videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        getDataFromIntent();

        final ProgressBar progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.gray), PorterDuff.Mode.SRC_IN);

        videoView = findViewById(R.id.videoView);

        videoView.setUp(url,"");
        videoView.setStateAndUi(JCVideoPlayer.CURRENT_STATE_NORMAL);




    }

    private void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent!=null)
        {
            url =  intent.getStringExtra("url");
            bg = intent.getStringExtra("bg");

        }
    }

    @Override
    protected void onPause() {
        JCVideoPlayer.releaseAllVideos();

        super.onPause();
    }




    @Override
    protected void onDestroy() {
        JCVideoPlayer.releaseAllVideos();

        super.onDestroy();




    }
}
