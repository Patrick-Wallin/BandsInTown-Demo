package com.patrickwallin.projects.bandsintown_demo.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piwal on 10/12/2017.
 */

public class ArtistsFavoritesContract {
    public static final String SCHEME = "content://";
    public static final String CONTENT_AUTHORITY = "com.patrickwallin.projects.bandsintown_demo";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES = "artists_favorites";

    public static final class ArtistsFavoritesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = PATH_FAVORITES;

        public static final String COLUMN_ARTISTS_FAVORITES_NAME = "artists_favorites_name";
        public static final String COLUMN_ARTISTS_FAVORITES_MEDIA_ID = "artists_favorites_media_id";

        public static final int COL_ARTISTS_FAVORITES_NAME = 1;
        public static final int COL_ARTISTS_FAVORITES_MEDIA_ID = 2;
    }

    public static String getCreateTableStatement() {
        StringBuilder createTableTableStatement = new StringBuilder();

        createTableTableStatement.append("CREATE TABLE IF NOT EXISTS ");
        createTableTableStatement.append(PATH_FAVORITES);
        createTableTableStatement.append(" (");
        createTableTableStatement.append(ArtistsFavoritesEntry._ID);
        createTableTableStatement.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        createTableTableStatement.append(ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_NAME);
        createTableTableStatement.append(" TEXT NOT NULL, ");
        createTableTableStatement.append(ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_MEDIA_ID);
        createTableTableStatement.append(" INTEGER NOT NULL DEFAULT 0");
        createTableTableStatement.append(")");

        return createTableTableStatement.toString();
    }

    public static List<ArtistsSearchResults> getFavoriteArtistData(Context context) {
        List<ArtistsSearchResults> lFavoriteArtistData = new ArrayList<ArtistsSearchResults>();

        Cursor cursor = context.getContentResolver().query(
                ArtistsFavoritesEntry.CONTENT_URI,
                null, null, null, null);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                ArtistsSearchResults artistsSearchResults = new ArtistsSearchResults(cursor);
                lFavoriteArtistData.add(artistsSearchResults);
            }
            cursor.close();
        }

        return lFavoriteArtistData;
    }

}
