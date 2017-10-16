package com.patrickwallin.projects.bandsintown_demo.rest;

import android.content.Context;

import com.patrickwallin.projects.bandsintown_demo.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piwal on 10/12/2017.
 */

public class RetrofitApiClient {
    public static Retrofit getSearchClient(Context context) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_search_url_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getDetailClient(Context context) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_detail_url_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
