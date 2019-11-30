package com.elashry.omggroup;



import com.elashry.omggroup.models.AdsDataModel;
import com.elashry.omggroup.models.responseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Services {

    @GET("api/tv-radio-urls")
    Call<responseModel> getData();

    @GET("api/v2/widgets/{widget_id}")
    Call<AdsDataModel> getAdsData(@Path("widget_id") String widget_id,
                                  @Query("token") String token,
                                  @Query("user_id") String user_id
                                  );


}
