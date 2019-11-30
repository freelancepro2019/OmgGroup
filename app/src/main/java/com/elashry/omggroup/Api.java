package com.elashry.omggroup;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://admin.omgchannel.net/";
    public static final String ADS_URL = "https://recommendation.speakol.com/api/v2/widgets/";

    public static Retrofit getClient(String BaseUrl) {
        if (retrofit==null) {
            OkHttpClient client =new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


        }
        return retrofit;
    }

}
