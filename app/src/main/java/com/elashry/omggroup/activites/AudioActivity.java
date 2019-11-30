package com.elashry.omggroup.activites;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.elashry.omggroup.Api;
import com.elashry.omggroup.R;
import com.elashry.omggroup.RadioService;
import com.elashry.omggroup.Services;
import com.elashry.omggroup.models.AdsDataModel;
import com.elashry.omggroup.preference.Preference;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AudioActivity extends AppCompatActivity {
    private ImageView image_play ,image_gif;
    private String  AudioURL;
    private TextView tv_1,tv_2;
    private Typeface typeface;

    private AdView adView;

    private MediaPlayer mediaplayer;

    private ProgressBar progBarAd;
    private TextView tvAd,tvTitleAd;
    private ImageView imageAd;
    private Preference preference;
    private AdsDataModel.ItemModel itemModel=null;
    private FrameLayout flAd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        getDataFromIntent();
        typeface = Typeface.createFromAsset(getAssets(),"ar.otf");
        initView();

        adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);



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
        preference = Preference.newInstance();
        image_play = findViewById(R.id.image_play);
        image_gif = findViewById(R.id.image_gif);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);

        tv_1.setTypeface(typeface);
        tv_2.setTypeface(typeface);


        progBarAd = findViewById(R.id.progBarAd);
        tvAd = findViewById(R.id.tvAd);
        tvTitleAd = findViewById(R.id.tvTitleAd);
        imageAd = findViewById(R.id.imageAd);
        flAd1 = findViewById(R.id.flAd1);
        flAd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel!=null)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(itemModel.getEvents().getClick().get(0).getValue().getUrl()));
                    startActivity(intent);
                }
            }
        });

        Glide.with(this)
                .asGif()
                .load("https://raw.githubusercontent.com/AhmedMohamedAllam/Omg-Group/master/OMG%20Group/app/waves.gif")
                .into(image_gif);

        //play();
        new MyTask().execute();
        image_play.setImageResource(R.drawable.pause);
        image_gif.setVisibility(View.VISIBLE);


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
                    new MyTask().execute();
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

        getAds();

    }


    private void getAds()
    {
        String id = preference.getUserId(this);
        Retrofit retrofit = Api.getClient("https://recommendation.speakol.com/");
        Services services = retrofit.create(Services.class);
        Call<AdsDataModel> call = services.getAdsData("wi-4071","fa8dbbcb682699544e4e8f2212115f73",id);
        call.enqueue(new Callback<AdsDataModel>() {
            @Override
            public void onResponse(Call<AdsDataModel> call, Response<AdsDataModel> response) {

                if (response.isSuccessful()&&response.body()!=null) {

                    if (response.body().getPayload()!=null&&response.body().getPayload().getItems()!=null)
                    {
                        if (response.body().getPayload().getItems().get(0)!=null)
                        {
                            itemModel = response.body().getPayload().getItems().get(0);

                            tvAd.setVisibility(View.GONE);
                            tvTitleAd.setText(response.body().getPayload().getItems().get(0).getTitle());

                            Picasso.with(AudioActivity.this).load(Uri.parse(response.body().getPayload().getItems().get(0).getMedia().getUrl())).fit().into(imageAd, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progBarAd.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    progBarAd.setVisibility(View.GONE);

                                }
                            });



                        }
                    }

                } else {
                    Toast.makeText(AudioActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AdsDataModel> call, Throwable t) {

                Toast.makeText(AudioActivity.this, "تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void play()
    {

        Intent intent = new Intent(AudioActivity.this, RadioService.class);
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

    private class MyTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            play();
            return null;
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
