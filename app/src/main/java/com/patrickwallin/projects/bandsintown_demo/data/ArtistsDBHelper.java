package com.patrickwallin.projects.bandsintown_demo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by piwal on 10/13/2017.
 */

public class ArtistsDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "artistsinfo.db";
    private static int DATABASE_VERSION = 1;

    public ArtistsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArtistsFavoritesContract.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ArtistsFavoritesContract.ArtistsFavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
