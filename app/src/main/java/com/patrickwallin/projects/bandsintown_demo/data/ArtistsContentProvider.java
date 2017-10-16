package com.patrickwallin.projects.bandsintown_demo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by piwal on 10/13/2017.
 */

public class ArtistsContentProvider extends ContentProvider {
    public static final int ARTISTS_FAVORITE = 100;
    public static final int ARTISTS_FAVORITE_WITH_ID = 101;

    private ArtistsDBHelper mArtistsDBHelper;

    private static UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ArtistsFavoritesContract.CONTENT_AUTHORITY, ArtistsFavoritesContract.PATH_FAVORITES, ARTISTS_FAVORITE);
        uriMatcher.addURI(ArtistsFavoritesContract.CONTENT_AUTHORITY, ArtistsFavoritesContract.PATH_FAVORITES + "/#", ARTISTS_FAVORITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mArtistsDBHelper = new ArtistsDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mArtistsDBHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);

        Cursor cursor = null;

        switch(match) {
            case ARTISTS_FAVORITE:
                cursor = db.query(ArtistsFavoritesContract.ArtistsFavoritesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ARTISTS_FAVORITE_WITH_ID:
                break;
            default:
                throw  new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(cursor != null)
            cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mArtistsDBHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case ARTISTS_FAVORITE:
                long artistsId = db.insert(ArtistsFavoritesContract.ArtistsFavoritesEntry.TABLE_NAME, null, contentValues);
                if(artistsId > 0) {
                    returnUri = ContentUris.withAppendedId(ArtistsFavoritesContract.ArtistsFavoritesEntry.CONTENT_URI,artistsId);
                }else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mArtistsDBHelper.getWritableDatabase();
        int rowsDeleted = 0;

        int match = uriMatcher.match(uri);

        switch (match) {
            case ARTISTS_FAVORITE:
                rowsDeleted = db.delete(ArtistsFavoritesContract.ArtistsFavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsUpdated = 0;

        SQLiteDatabase db = mArtistsDBHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case ARTISTS_FAVORITE:
                rowsUpdated = db.update(ArtistsFavoritesContract.ArtistsFavoritesEntry.TABLE_NAME, contentValues, selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return rowsUpdated;
    }
}
