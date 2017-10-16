package com.patrickwallin.projects.bandsintown_demo.rest;

import com.patrickwallin.projects.bandsintown_demo.model.ArtistsDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by piwal on 10/12/2017.
 */

public interface RetrofitDetailApiInterface {
    @GET("artists/{name}")
    Call<ArtistsDetailResponse> getDetailResult(@Path("name") String name, @Query("app_id") int appId);

}
