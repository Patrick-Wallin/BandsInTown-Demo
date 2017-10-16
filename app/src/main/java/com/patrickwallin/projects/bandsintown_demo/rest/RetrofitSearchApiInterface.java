package com.patrickwallin.projects.bandsintown_demo.rest;

import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResultsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piwal on 10/12/2017.
 */

public interface RetrofitSearchApiInterface {
    @GET("searchArtists")
    Call<ArtistsSearchResultsResponse> getSearchResults(@Query("search") String searchArtistsInput);
}
