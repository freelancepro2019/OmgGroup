package com.elashry.omggroup.activites;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.elashry.omggroup.Api;
import com.elashry.omggroup.R;
import com.elashry.omggroup.Services;
import com.elashry.omggroup.models.AdsDataModel;
import com.elashry.omggroup.models.ImageAdsModel;
import com.elashry.omggroup.models.VideoAdsModel;
import com.elashry.omggroup.preference.Preference;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvActivity extends AppCompatActivity {

    private String url, bg,hashtag = "";
    private Boolean ads,active_logo,active_image_ads,active_time,active_hash_tag;
    private VideoView videoViewAds;//,videoView
    private VideoView videoView;

    private FrameLayout flAd;
    private RelativeLayout app_video_box;
    private ImageView image, imgOrientation,imgLogo,imgads;
    private ProgressBar progBar, progBarLoad,progBarLoad2;
    private TextView tvCounter,tvHashtag;
    private MediaPlayer mp,mp2;
    private CountDownTimer adsTime, timer,imgTime;
    private MediaController mediaController;
    private boolean isNormal = true;
    private AdView adView,adView2;

    private ProgressBar progBarAd,progBarAd2;
    private TextView tvClock,tvAd,tvAd2,tvTitleAd,tvTitleAd2;
    private ImageView imageAd,imageAd2;
    private Preference preference;
    private FrameLayout flAd1;
    private AdsDataModel.ItemModel itemModel=null;
    private ImageView imgDelete1,imgDelete2;
    private RewardedVideoAd mRewardedVideoAd;
    private final String VIDEO = "http://admin.omgchannel.net/storage/";
    private List<VideoAdsModel.Video> videoList;
    private int period =20,periodImg=20;
    private int currentVideoIndex = 0,currentImgIndex=0;
    private boolean isTimerStarted = false,isImgTimerStarted=false;
    private LinearLayout Lin_imgAds;
    private List<String> imageAdsModelList;
    private View view;
    private int current_pos =0;
    private boolean isFirstTime = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        getDataFromIntent();
        initView();
        getData();

    }

    private void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            bg = intent.getStringExtra("bg");
            ads=intent.getBooleanExtra("ads",false);
            active_hash_tag=intent.getBooleanExtra("active_hash_tag",false);
            active_image_ads=intent.getBooleanExtra("active_image_ads",false);
            active_logo=intent.getBooleanExtra("active_logo",false);
            active_time=intent.getBooleanExtra("active_time",false);
            hashtag=intent.getStringExtra("hashtag");

        }
    }


    private void initView() {

        imageAdsModelList = new ArrayList<>();
        videoList = new ArrayList<>();
        preference = Preference.newInstance();

        mediaController = new MediaController(this);

        flAd = findViewById(R.id.flAd);
        app_video_box = findViewById(R.id.app_video_box);
        image = findViewById(R.id.image);
        view = findViewById(R.id.view);


        progBar = findViewById(R.id.progBar);
        progBarLoad = findViewById(R.id.progBarLoad);
        progBarLoad.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.blue), PorterDuff.Mode.SRC_IN);


        tvCounter = findViewById(R.id.tvCounter);

        videoView = findViewById(R.id.videoView);

        progBarLoad2 = findViewById(R.id.progBarLoad2);
        progBarLoad2.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.blue), PorterDuff.Mode.SRC_IN);

        videoViewAds = findViewById(R.id.videoViewAds);
        tvClock=findViewById(R.id.tvClock);

        Lin_imgAds=findViewById(R.id.Lin_imgAds);
        imgads=findViewById(R.id.imgads);
        tvHashtag=findViewById(R.id.tvHashtag);
        tvHashtag.setText(hashtag);

        if (!active_image_ads)
        {
            Lin_imgAds.setVisibility(View.GONE);
        }else {
            Lin_imgAds.setVisibility(View.VISIBLE);

        }
        if (!active_hash_tag)
        {
            tvHashtag.setVisibility(View.GONE);
        }else {
            tvHashtag.setVisibility(View.VISIBLE);

        }


        image.setImageResource(R.drawable.bg);

        imgLogo=findViewById(R.id.imgLogo);

        Glide.with(TvActivity.this)
                .load(R.drawable.logo_gif)
                .into(imgLogo);

        progBarAd = findViewById(R.id.progBarAd);

        tvAd = findViewById(R.id.tvAd);

        if (!ads){
            flAd1.setVisibility(View.GONE);
        }
        else {
        getAds();
        }
        if (!active_time)
        {
         tvClock.setVisibility(View.GONE);
        }else {
            tvClock.setVisibility(View.VISIBLE);

        }
        if (!active_logo)
        {
            imgLogo.setVisibility(View.GONE);
        }else {
            imgLogo.setVisibility(View.VISIBLE);

        }


        getVideoAds();




    }
    private void getData()
    {
        Retrofit retrofit = Api.getClient("http://admin.omgchannel.net/");
        Services services = retrofit.create(Services.class);
        Call<List<ImageAdsModel>> call = services.getImgAds();
        call.enqueue(new Callback<List<ImageAdsModel>>() {
            @Override
            public void onResponse(Call<List<ImageAdsModel>> call, Response<List<ImageAdsModel>> response) {

                if (response.isSuccessful()&&response.body()!=null&&response.body().size()>0) {

                    if (response.body().get(0).getImages().size()>0)
                    {
                        periodImg = response.body().get(0).getPeriod();
                        imageAdsModelList.addAll(response.body().get(0).getImages());
                        if (response.body().get(0).getImages().size()>1)
                        {
                            Picasso.with(TvActivity.this).load(Uri.parse(VIDEO+imageAdsModelList.get(currentImgIndex))).fit().into(imgads);

                            currentImgIndex++;
                            startTimerAds(response.body().get(0).getPeriod());


                        }
                    }

                } else {
                    Toast.makeText(TvActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<ImageAdsModel>> call, Throwable t) {

                Toast.makeText(TvActivity.this, "تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
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




                        }
                    }

                } else {
                    Toast.makeText(TvActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AdsDataModel> call, Throwable t) {

                Toast.makeText(TvActivity.this, "تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getVideoAds()
    {

        Retrofit retrofit = Api.getClient("http://admin.omgchannel.net/");
        Services services = retrofit.create(Services.class);
        Call<List<VideoAdsModel>> call = services.getVideoAds();
        call.enqueue(new Callback<List<VideoAdsModel>>() {
            @Override
            public void onResponse(Call<List<VideoAdsModel>> call, Response<List<VideoAdsModel>> response) {
                if (response.isSuccessful()&&response.body()!=null) {


                    period = response.body().get(0).getPeriod();
                    videoList.clear();


                    videoList.addAll(response.body().get(0).getVideos());
                    playVideo();




                } else {

                    playVideo();
                    Toast.makeText(TvActivity.this,"فشل", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VideoAdsModel>> call, Throwable t) {
                playVideo();
                Log.e("error",t.getMessage()+"__");
                Toast.makeText(TvActivity.this, "تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void playVideo()
    {

        view.setVisibility(View.GONE);
        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("fff","gggg");

                progBarLoad.setVisibility(View.GONE);
                videoView.start();
                currentVideoIndex =0;

                if (videoList.size()>0)
                {

                    startTimer(period);
                }


            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("vidcpmp","commmpppp");
                progBarLoad.setVisibility(View.VISIBLE);
                if (timer!=null)
                {
                    timer.cancel();

                }
                playVideo();
            }
        });


    }
    private void playVideoAds() {

        Log.e("mmm","lll");
        String urlVideo = VIDEO+videoList.get(currentVideoIndex).getDownloadLink();
        videoViewAds.setVideoPath(urlVideo);
        videoViewAds.requestFocus();
        videoViewAds.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("fff","gggg");
                progBarLoad2.setVisibility(View.GONE);
                videoViewAds.setAlpha(1.0f);
                videoView.pause();
                current_pos = videoView.getCurrentPosition();


                videoViewAds.start();

            }
        });


        videoViewAds.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {

                Log.e("complete","complete");
                //playVideoAds();

                Log.e("pos",currentVideoIndex+"_");
                currentVideoIndex++;


                if (currentVideoIndex<videoList.size())
                {
                    progBarLoad2.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                    String urlVideo = VIDEO+videoList.get(currentVideoIndex).getDownloadLink();
                    videoViewAds.setVideoURI(Uri.parse(urlVideo));
                }else
                {

                    videoViewAds.setVisibility(View.GONE);
                    currentVideoIndex = 0;
                    progBarLoad2.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    videoView.resume();

                    Log.e("gg","oo");
                    if (videoList.size()>0)
                    {
                        startTimer(period);

                    }

                }

            }
        });
    }

    private void startTimer(int time) {
        Log.e("dddd","ttt");
        isTimerStarted =true;

        timer = new CountDownTimer(1000*time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                progBarLoad2.setVisibility(View.VISIBLE);
                videoViewAds.setVisibility(View.VISIBLE);
                isTimerStarted = false;
                timer.cancel();
                playVideoAds();






            }
        };

        timer.start();

    }

    private void startTimerAds(int time) {
        isImgTimerStarted =true;
        imgTime = new CountDownTimer(1000*time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                isImgTimerStarted = false;


                if (currentImgIndex<imageAdsModelList.size())
                {
                    Picasso.with(TvActivity.this).load(Uri.parse(VIDEO+imageAdsModelList.get(currentImgIndex))).fit().into(imgads);
                    imgTime.start();
                    currentImgIndex++;
                }else {

                    currentImgIndex= 0;
                    Picasso.with(TvActivity.this).load(Uri.parse(VIDEO+imageAdsModelList.get(currentImgIndex))).fit().into(imgads);
                    imgTime.start();
                }


            }
        };

        imgTime.start();

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



    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        videoViewAds.pause();
        view.setVisibility(View.VISIBLE);
        videoViewAds.setAlpha(0.0f);
        progBarLoad2.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        videoView.resume();
        if (videoList.size()>0)
        {
            if (!isTimerStarted){
                startTimer(period);

            }

        }

        if (imageAdsModelList.size()>0)
        {
            if (!isImgTimerStarted){
                startTimerAds(periodImg);

            }

        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }

        if (imgTime != null) {
            imgTime.cancel();
        }

        if (adsTime != null) {
            adsTime.cancel();
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }




}
