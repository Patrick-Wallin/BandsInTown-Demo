package com.patrickwallin.projects.bandsintown_demo.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.patrickwallin.projects.bandsintown_demo.data.ArtistsFavoritesContract;

import org.parceler.Parcel;

/**
 * Created by piwal on 10/12/2017.
 */
@Parcel
public class ArtistsSearchResults {
    @SerializedName("name")
    private String mName;
    @SerializedName("media_id")
    private Integer mMediaId;

    public ArtistsSearchResults() {}

    public ArtistsSearchResults(Cursor cursorData) {
        if(cursorData != null) {
            setName(cursorData.getString(ArtistsFavoritesContract.ArtistsFavoritesEntry.COL_ARTISTS_FAVORITES_NAME));
            setMediaId(cursorData.getInt(ArtistsFavoritesContract.ArtistsFavoritesEntry.COL_ARTISTS_FAVORITES_MEDIA_ID));
        }
    }

    public ArtistsSearchResults(String name, Integer mediaId) {
        setName(name);
        setMediaId(mediaId);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getMediaId() {
        return mMediaId;
    }

    public void setMediaId(int mediaId) {
        mMediaId = mediaId;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ArtistsFavoritesContract.ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_NAME, getName());
        contentValues.put(ArtistsFavoritesContract.ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_MEDIA_ID, getMediaId());

        return contentValues;
    }

}
