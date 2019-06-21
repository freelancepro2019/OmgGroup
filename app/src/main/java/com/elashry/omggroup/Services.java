package com.elashry.omggroup;



import retrofit2.Call;
import retrofit2.http.GET;


public interface Services {



    @GET("api/tv-radio-urls")
    Call<responseModel> getData();


}
