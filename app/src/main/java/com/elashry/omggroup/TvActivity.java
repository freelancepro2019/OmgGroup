package com.elashry.omggroup;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.khizar1556.mkvideoplayer.MKPlayer;

public class TvActivity extends AppCompatActivity {

    private String url,bg="";
    private MKPlayer mkPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        getDataFromIntent();

        final ProgressBar progBar = findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.gray), PorterDuff.Mode.SRC_IN);

        mkPlayer = new MKPlayer(this);
        mkPlayer.play(url);
        mkPlayer.onInfo(new MKPlayer.OnInfoListener() {
            @Override
            public void onInfo(int i, int i1) {

            }
        });
        mkPlayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                Log.e("fff","fff");
            }

            @Override
            public void onPreviousClick() {
                Log.e("fff","ggg");

            }
        });




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
        super.onPause();
        mkPlayer.onPause();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mkPlayer.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mkPlayer.onDestroy();




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mkPlayer.onBackPressed();
    }
}
