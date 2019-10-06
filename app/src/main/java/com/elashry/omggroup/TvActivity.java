package com.elashry.omggroup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class TvActivity extends AppCompatActivity {

    private String url, bg = "";
    private VideoView videoView;
    private FrameLayout flAd;
    private RelativeLayout app_video_box;
    private ImageView image, imgOrientation;
    private ProgressBar progBar, progBarLoad;
    private TextView tvCounter;
    private MediaPlayer mp;
    private CountDownTimer adsTime, timer;
    private MediaController mediaController;
    private boolean isNormal = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        getDataFromIntent();

        initView();


    }

    private void initView() {

        mediaController = new MediaController(this);
        flAd = findViewById(R.id.flAd);
        app_video_box = findViewById(R.id.app_video_box);
        image = findViewById(R.id.image);
        imgOrientation = findViewById(R.id.imgOrientation);

        progBar = findViewById(R.id.progBar);
        progBarLoad = findViewById(R.id.progBarLoad);
        progBarLoad.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.blue), PorterDuff.Mode.SRC_IN);

        tvCounter = findViewById(R.id.tvCounter);
        videoView = findViewById(R.id.videoView);

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        image.setImageResource(R.drawable.bg);

        videoView.measure(videoView.getMeasuredWidth() + 100, videoView.getMeasuredHeight() + 100);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                TvActivity.this.mp = mp;
                progBarLoad.setVisibility(View.GONE);
                float ratio = (float) videoView.getWidth() / videoView.getHeight();

                videoView.measure(videoView.getWidth(), (int) (videoView.getHeight() * ratio));
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                            progBarLoad.setVisibility(View.VISIBLE);
                        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                            progBarLoad.setVisibility(View.GONE);

                        }


                        return false;
                    }
                });
            }
        });


        imgOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNormal) {
                    isNormal = false;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    isNormal = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }
            }
        });

        //startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                app_video_box.setVisibility(View.GONE);
                flAd.setVisibility(View.VISIBLE);
                //startAdsTimer();


            }
        };

        //timer.start();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isNormal = true;
        } else {
            isNormal = false;
        }
    }

    private void startAdsTimer() {

        adsTime = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                int sec = (int) (((30000 - millisUntilFinished) / 1000) + 1);
                tvCounter.setText(String.valueOf(sec));
                long progress = Math.round((100 / 30.0) * sec);
                progBar.setProgress((int) progress);
            }

            @Override
            public void onFinish() {
                app_video_box.setVisibility(View.VISIBLE);
                flAd.setVisibility(View.GONE);
                //startTimer();

            }
        };

        adsTime.start();
    }


    private void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            bg = intent.getStringExtra("bg");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }

        if (adsTime != null) {
            adsTime.cancel();
        }


    }

    @Override
    public void onBackPressed() {
        if (isNormal) {
            super.onBackPressed();
        }else
            {
                isNormal =true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
    }
}
