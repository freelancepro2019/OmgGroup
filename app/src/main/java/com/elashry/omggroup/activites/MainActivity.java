package com.elashry.omggroup.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elashry.omggroup.Api;
import com.elashry.omggroup.BuildConfig;
import com.elashry.omggroup.R;
import com.elashry.omggroup.Services;
import com.elashry.omggroup.models.responseModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.elashry.omggroup.Api.BASE_URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView image_tv, image_radio, image_share, image_youtube, image_linkedin, image_facebook;
    String Tvurl = "", AudioURL = "", bg = "";
    private AdView adView,adView2,adView3;
    private ImageView image;
    LinearLayout  nav_tv,nav_radio,nav_about,nav_advertise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        adView2 = findViewById(R.id.adView2);
        AdRequest request2 = new AdRequest.Builder().addTestDevice("fa8dbbcb682699544e4e8f2212115f73")
                .build();
        adView2.loadAd(request2);

        adView3 = findViewById(R.id.adView3);
        AdRequest request3 = new AdRequest.Builder().addTestDevice("fa8dbbcb682699544e4e8f2212115f73")
                .build();
        adView3.loadAd(request3);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);


            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
      //  navigationView.setNavigationItemSelectedListener(this);


    }


    private void initView() {
        image_tv = findViewById(R.id.image_tv);
        image_radio = findViewById(R.id.image_radio);
        image_share = findViewById(R.id.image_share);
        image_youtube = findViewById(R.id.image_youtube);
        image_linkedin = findViewById(R.id.image_linkedin);
        image_facebook = findViewById(R.id.image_facebook);
        nav_tv=findViewById(R.id.nav_tv);
        nav_radio=findViewById(R.id.nav_radio);
        nav_about=findViewById(R.id.nav_about);
        nav_advertise=findViewById(R.id.nav_advertise);

        image = findViewById(R.id.image);


        image_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Tvurl)) {
                    Toast.makeText(MainActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(MainActivity.this, TvActivity.class);
                    intent.putExtra("url", Tvurl);
                    intent.putExtra("bg", bg);
                    startActivity(intent);
                }


            }
        });
        image_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(AudioURL)) {
                    Toast.makeText(MainActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(MainActivity.this, AudioActivity.class);
                    intent.putExtra("url", AudioURL);
                    intent.putExtra("bg", bg);
                    startActivity(intent);
                }

            }
        });
        image_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/Omg-Channel-1687614811490132/"));
                startActivity(intent);
            }
        });
        image_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://m.youtube.com/channel/UCqc1YeG_iEwmrXVe4wPUhTg"));
                startActivity(intent);
            }
        });
        image_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/company/omg-channel"));
                startActivity(intent);
            }
        });
        image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Omg Group");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.elashry.omggroup" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        nav_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler()
                        .postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, TvActivity.class);
                                intent.putExtra("url", Tvurl);
                                startActivity(intent);
                            }
                        }, 300);
            }
        });
        nav_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler()
                        .postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, AudioActivity.class);
                                intent.putExtra("url", AudioURL);
                                startActivity(intent);
                            }
                        }, 300);
            }
        });
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler()
                        .postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://omgchannel.net/OMG/About-Us"));
                                startActivity(intent);
                            }
                        }, 300);
            }
        });
        nav_advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler()
                        .postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://omgchannel.net/OMG?journal_blog_post_id=75"));
                                startActivity(intent);
                            }
                        }, 300);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Log.e("subscribed", "true");
                        }
                    }
                });


        getData();


    }

    private void getData() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("إنتظر قليلا...");
        dialog.show();

        Retrofit retrofit = Api.getClient(BASE_URL);
        Services services = retrofit.create(Services.class);
        Call<responseModel> call = services.getData();
        call.enqueue(new Callback<responseModel>() {
            @Override
            public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {
                    Tvurl = response.body().getTv_url();
                    AudioURL = response.body().getRadio_url();
                    bg = response.body().getApp_background();
                    Picasso.with(MainActivity.this).load(Uri.parse("http://admin.omgchannel.net/storage/" + bg)).fit().into(image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            image.setImageResource(R.drawable.bg);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "خطأ حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(MainActivity.this, "تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       /* int id = item.getItemId();

        if (id == R.id.nav_tv) {
            new Handler()
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, TvActivity.class);
                            intent.putExtra("url", Tvurl);
                            startActivity(intent);
                        }
                    }, 300);


        } else if (id == R.id.nav_radio) {
            new Handler()
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, AudioActivity.class);
                            intent.putExtra("url", AudioURL);
                            startActivity(intent);
                        }
                    }, 300);


        } else if (id == R.id.nav_about) {
            new Handler()
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://omgchannel.net/OMG/About-Us"));
                            startActivity(intent);
                        }
                    }, 300);


        } else if (id == R.id.nav_advertise) {
            new Handler()
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://omgchannel.net/OMG?journal_blog_post_id=75"));
                            startActivity(intent);
                        }
                    }, 300);


        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
