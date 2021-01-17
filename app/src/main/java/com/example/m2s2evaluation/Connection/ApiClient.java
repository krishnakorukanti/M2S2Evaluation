package com.example.m2s2evaluation.Connection;

import com.example.m2s2evaluation.Model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("/data/2.5/weather")
    Call<ResponseData> getData(@Query("q") String cityName,@Query("appid") String apiId,@Query("units") String units );
}
