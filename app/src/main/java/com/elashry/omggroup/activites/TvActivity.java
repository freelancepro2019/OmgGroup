package com.elashry.omggroup.activites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.elashry.omggroup.Api;
import com.elashry.omggroup.R;
import com.elashry.omggroup.Services;
import com.elashry.omggroup.models.AdsDataModel;
import com.elashry.omggroup.models.VideoAdsModel;
import com.elashry.omggroup.preference.Preference;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TvActivity extends AppCompatActivity {

    private String url, bg = "";
    private Boolean ads;
    private VideoView videoViewAds;//,videoView
    private WebView webView;

    private FrameLayout flAd;
    private RelativeLayout app_video_box;
    private ImageView image, imgOrientation;
    private ProgressBar progBar, progBarLoad,progBarLoad2;
    private TextView tvCounter;
    private MediaPlayer mp,mp2;
    private CountDownTimer adsTime, timer;
    private MediaController mediaController;
    private boolean isNormal = true;
    private AdView adView,adView2;

    private ProgressBar progBarAd,progBarAd2;
    private TextView tvAd,tvAd2,tvTitleAd,tvTitleAd2;
    private ImageView imageAd,imageAd2;
    private Preference preference;
    private FrameLayout flAd1;
    private AdsDataModel.ItemModel itemModel=null;
    private ImageView imgDelete1,imgDelete2;
    private RewardedVideoAd mRewardedVideoAd;
    private final String VIDEO = "http://admin.omgchannel.net/storage/";
    private List<VideoAdsModel.Video> videoList;
    private int period =20;
    private int currentVideoIndex = 0;
    private boolean isVideoStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        getDataFromIntent();

        initView();
//        adView = findViewById(R.id.adView);
//        AdRequest request = new AdRequest.Builder().build();
//        adView.loadAd(request);
//
//        adView2 = findViewById(R.id.adView2);
//        AdRequest request2 = new AdRequest.Builder().addTestDevice("fa8dbbcb682699544e4e8f2212115f73")
//                .build();
//        adView2.loadAd(request2);

    }

    private void initView() {

        videoList = new ArrayList<>();
        preference = Preference.newInstance();

        mediaController = new MediaController(this);

        flAd = findViewById(R.id.flAd);
        app_video_box = findViewById(R.id.app_video_box);
        image = findViewById(R.id.image);
        imgOrientation = findViewById(R.id.imgOrientation);


        progBar = findViewById(R.id.progBar);
        progBarLoad = findViewById(R.id.progBarLoad);
        progBarLoad.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.blue), PorterDuff.Mode.SRC_IN);

        tvCounter = findViewById(R.id.tvCounter);

        //videoView = findViewById(R.id.videoView);
        webView = findViewById(R.id.videoView);

        progBarLoad2 = findViewById(R.id.progBarLoad2);
        videoViewAds = findViewById(R.id.videoViewAds);


        Log.e("url",url);
        /*mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);*////////////
        image.setImageResource(R.drawable.bg);

        /////videoView.measure(videoView.getMeasuredWidth() + 100, videoView.getMeasuredHeight() + 100);

        progBarAd = findViewById(R.id.progBarAd);
       // progBarAd2 = findViewById(R.id.progBarAd2);

        tvAd = findViewById(R.id.tvAd);
     //   tvAd2 = findViewById(R.id.tvAd2);

       // tvTitleAd = findViewById(R.id.tvTitleAd);
       // tvTitleAd2 = findViewById(R.id.tvTitleAd2);

        imageAd = findViewById(R.id.imageAd);
      //  imageAd2 = findViewById(R.id.imageAd2);

        imgDelete1 = findViewById(R.id.imgDelete1);
       // imgDelete2 = findViewById(R.id.imgDelete2);

        flAd1 = findViewById(R.id.flAd1);
      //  flAd2 = findViewById(R.id.flAd2);
        flAd1.setVisibility(View.GONE);
        MobileAds.initialize(this, "ca-app-pub-1001110363789350~7595359427");

        //RewardedVideoAd
      /*  mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.loadAd("ca-app-pub-1001110363789350/5402443645", new AdRequest.Builder().build());
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);
*/
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

      /*  flAd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemModel!=null)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(itemModel.getEvents().getClick().get(0).getValue().getUrl()));
                    startActivity(intent);
                }
            }
        });*/

        imgDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flAd1.setVisibility(View.GONE);
              //  tvTitleAd.setVisibility(View.GONE);
            }
        });

     /*   imgDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  flAd2.setVisibility(View.GONE);
                tvTitleAd2.setVisibility(View.GONE);


            }
        });*/




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
        if (ads==false){
            flAd1.setVisibility(View.GONE);
        }
        else {
        getAds();
        }

        getVideoAds();

       /* currentVideoIndex++;

        videoViewAds.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                currentVideoIndex++;

                if (currentVideoIndex>=videoList.size())
                {

                    currentVideoIndex = 0;
                    startTimer(period);

                }

                videoViewAds.setVideoURI(Uri.parse(VIDEO+videoList.get(currentVideoIndex).getDownloadLink()));
                videoViewAds.start();

            }
        });*/



        videoViewAds.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progBarLoad2.setVisibility(View.GONE);
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                            progBarLoad2.setVisibility(View.VISIBLE);
                        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                            progBarLoad2.setVisibility(View.GONE);

                        }


                        return false;
                    }
                });


            }
        });


        //startTimer();}
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

        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progBarLoad.setVisibility(View.GONE);
                webView.onResume();
                if (videoList.size()>0)
                {
                    startTimer(period);

                }
            }
        });



       /* videoView.setVideoURI(Uri.parse(url));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!isVideoStarted)
                {
                    videoView.start();

                }else
                    {
                        videoView.resume();
                    }

                if (videoList.size()>0)
                {
                    startTimer(period);

                }

                isVideoStarted = true;
                TvActivity.this.mp = mp;

                videoViewAds.setVisibility(View.GONE);
                progBarLoad.setVisibility(View.GONE);

               *//* float ratio = (float) videoView.getWidth() / videoView.getHeight();

                videoView.measure(videoView.getWidth(), (int) (videoView.getHeight() * ratio));
          *//*      mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                            progBarLoad.setVisibility(View.GONE);
                        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                            progBarLoad.setVisibility(View.GONE);

                        }


                        return false;
                    }
                });


            }
        });*/
    }
    private void playVideoAds() {


        videoViewAds.setVisibility(View.VISIBLE);
        currentVideoIndex = 0;
        webView.setVisibility(View.GONE);
        //videoView.setVisibility(View.GONE);
        progBarLoad.setVisibility(View.GONE);
        webView.onPause();
        //videoView.pause();
        String urlVideo = VIDEO+videoList.get(currentVideoIndex).getDownloadLink();
        videoViewAds.setVideoURI(Uri.parse(urlVideo));
        videoViewAds.start();

        videoViewAds.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                currentVideoIndex++;

                if (currentVideoIndex<videoList.size())
                {
                    String urlVideo = VIDEO+videoList.get(currentVideoIndex).getDownloadLink();
                    videoViewAds.setVideoURI(Uri.parse(urlVideo));
                    videoViewAds.start();
                }else
                {
                    currentVideoIndex = 0;
                    videoViewAds.setVisibility(View.GONE);
                    progBarLoad2.setVisibility(View.GONE);

                    Log.e("1","1");
                    webView.setVisibility(View.VISIBLE);
                    //videoView.setVisibility(View.VISIBLE);

                    //videoView.resume();




                    if (videoList.size()>0)
                    {
                        startTimer(period);

                    }
                }
            }
        });
    }

    private void startTimer(int time) {
        Log.e("time",time+"__");

        videoViewAds.setVisibility(View.GONE);
        progBarLoad2.setVisibility(View.GONE);

        timer = new CountDownTimer(1000*time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (videoList.size()>0)
                {
                    playVideoAds();
                }




            }
        };

        timer.start();

    }





    public void showRewardedAd(View view) {
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }
    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {
            Toast.makeText(TvActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            Toast.makeText(TvActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            Toast.makeText(TvActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            Toast.makeText(TvActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {
            String msg = "onRewarded! currency: " + rewardItem.getType() + "  amount: " + rewardItem.getAmount();
            Toast.makeText(TvActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            Toast.makeText(TvActivity.this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            Toast.makeText(TvActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            Toast.makeText(TvActivity.this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();

        }
    };

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



                                flAd1.setVisibility(View.VISIBLE);

                                //  tvAd2.setVisibility(View.GONE);

                         //   tvTitleAd.setText(response.body().getPayload().getItems().get(0).getTitle());
                           // tvTitleAd2.setText(response.body().getPayload().getItems().get(0).getTitle());

                            Picasso.with(TvActivity.this).load(Uri.parse(response.body().getPayload().getItems().get(0).getMedia().getUrl())).fit().into(imageAd, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progBarAd.setVisibility(View.GONE);
                                    imgDelete1.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError() {
                                    progBarAd.setVisibility(View.GONE);

                                }
                            });

                           /* Picasso.with(TvActivity.this).load(Uri.parse(response.body().getPayload().getItems().get(0).getMedia().getUrl())).fit().into(imageAd2, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                 //   progBarAd2.setVisibility(View.GONE);
                                   // imgDelete2.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onError() {
                                  //  progBarAd2.setVisibility(View.GONE);

                                }
                            });
*/


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
            ads=intent.getBooleanExtra("ads",false);

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
