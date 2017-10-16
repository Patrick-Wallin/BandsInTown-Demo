package com.patrickwallin.projects.bandsintown_demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piwal on 10/12/2017.
 */

public class ArtistsSearchResultsResponse {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("data")
    private List<ArtistsSearchResults> mData;

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setData(List<ArtistsSearchResults> data) {
        mData = data;
    }

    public List<ArtistsSearchResults> getData() {
        return mData;
    }
}
