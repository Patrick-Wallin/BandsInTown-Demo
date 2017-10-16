package com.patrickwallin.projects.bandsintown_demo.model;

import com.google.gson.annotations.SerializedName;
import com.patrickwallin.projects.bandsintown_demo.data.ArtistsFavoritesContract;

/**
 * Created by piwal on 10/12/2017.
 */

public class ArtistsDetailResponse {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("image_url")
    private String mImageUrl;
    @SerializedName("thumb_url")
    private String mThumbUrl;
    @SerializedName("facebook_page_url")
    private String mFacebookPageUrl;
    @SerializedName("mbid")
    private String mMbid;
    @SerializedName("tracker_count")
    private Integer mTrackerCount;
    @SerializedName("upcoming_event_count")
    private Integer mUpcomingEventCount;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        mThumbUrl = thumbUrl;
    }

    public String getFacebookPageUrl() {
        return mFacebookPageUrl;
    }

    public void setFacebookPageUrl(String facebookPageUrl) {
        mFacebookPageUrl = facebookPageUrl;
    }

    public String getMbid() {
        return mMbid;
    }

    public void setMbid(String mbid) {
        mMbid = mbid;
    }

    public Integer getTrackerCount() {
        return mTrackerCount;
    }

    public void setTrackerCount(Integer trackerCount) {
        mTrackerCount = trackerCount;
    }

    public Integer getUpcomingEventCount() {
        return mUpcomingEventCount;
    }

    public void setUpcomingEventCount(Integer upcomingEventCount) {
        mUpcomingEventCount = upcomingEventCount;
    }

}
