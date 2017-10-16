package com.patrickwallin.projects.bandsintown_demo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patrickwallin.projects.bandsintown_demo.ArtistDetailActivity;
import com.patrickwallin.projects.bandsintown_demo.R;
import com.patrickwallin.projects.bandsintown_demo.data.ArtistsFavoritesContract;
import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResults;
import com.patrickwallin.projects.bandsintown_demo.viewholder.ArtistsViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

/**
 * Created by piwal on 10/12/2017.
 */

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsViewHolder> {
    private List<ArtistsSearchResults> mArtistsSearchResults;
    private Context mContext;

    public ArtistsAdapter(List<ArtistsSearchResults> artistsSearchResults, Context context) {
        mArtistsSearchResults = artistsSearchResults;
        mContext = context;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artist, parent, false);
        ArtistsViewHolder artistsViewHolder = new ArtistsViewHolder(v);
        return artistsViewHolder;
    }

    @Override
    public void onBindViewHolder(final ArtistsViewHolder holder, int position) {
        if(mArtistsSearchResults != null && position >= 0) {
            final int colorFavorite = ResourcesCompat.getColor(mContext.getResources(), R.color.colorFavorite,null);
            final int colorUnFavorite = ResourcesCompat.getColor(mContext.getResources(), R.color.colorUnfavorite,null);
            final ArtistsSearchResults artistsSearchResults = mArtistsSearchResults.get(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ArtistDetailActivity.class);
                    intent.putExtra(mContext.getString(R.string.artist_name),artistsSearchResults.getName());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, (View)holder.mArtistImageView, mContext.getResources().getString(R.string.image_transition));
                    mContext.startActivity(intent,options.toBundle());
                }
            });

            holder.mArtistNameTextView.setText(artistsSearchResults.getName());
            if(artistsSearchResults.getMediaId() > 0) {
                String photoUrlAddress = String.format(mContext.getString(R.string.photo_url_address),artistsSearchResults.getMediaId());
                Picasso.with(mContext)
                        .load(photoUrlAddress)
                        .placeholder(R.drawable.ic_no_image_available)
                        .into(holder.mArtistImageView);
            }else {
                Picasso.with(mContext)
                        .load(R.drawable.ic_no_image_available)
                        .into(holder.mArtistImageView);
            }
            holder.mFavoriteImageView.setTag(false);

            if(hasThisArtistBeenFavorite(artistsSearchResults.getName())) {
                holder.mFavoriteImageView.setTag(true);
                holder.mFavoriteImageView.setColorFilter(colorFavorite);
            }else {
                holder.mFavoriteImageView.setColorFilter(colorUnFavorite);
            }

            holder.mFavoriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorStateList colorStateList = holder.mFavoriteImageView.getImageTintList();
                    if(!((boolean)holder.mFavoriteImageView.getTag())) {
                        holder.mFavoriteImageView.setColorFilter(colorFavorite);
                        holder.mFavoriteImageView.setTag(true);
                        ContentValues cv = artistsSearchResults.getContentValues();
                        mContext.getContentResolver().insert(ArtistsFavoritesContract.ArtistsFavoritesEntry.CONTENT_URI,cv);
                    }else {
                        holder.mFavoriteImageView.setColorFilter(colorUnFavorite);
                        holder.mFavoriteImageView.setTag(false);
                        String sqlWhereDelete = ArtistsFavoritesContract.ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_NAME + " = " + android.database.DatabaseUtils.sqlEscapeString(String.valueOf(artistsSearchResults.getName()));
                        int numberDeleted = mContext.getContentResolver().delete(ArtistsFavoritesContract.ArtistsFavoritesEntry.CONTENT_URI,sqlWhereDelete,null);
                        mArtistsSearchResults = ArtistsFavoritesContract.getFavoriteArtistData(mContext);
                        notifyDataSetChanged();

                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return (mArtistsSearchResults == null ? 0 : mArtistsSearchResults.size());
    }

    public void setFavoriteData(List<ArtistsSearchResults> favoriteData) {
        mArtistsSearchResults = favoriteData;
        notifyDataSetChanged();
    }

    private boolean hasThisArtistBeenFavorite(String name) {
        boolean favorite = false;

        Cursor artistCursor = mContext.getContentResolver().query(
                ArtistsFavoritesContract.ArtistsFavoritesEntry.CONTENT_URI,
                new String[] {ArtistsFavoritesContract.ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_NAME},
                ArtistsFavoritesContract.ArtistsFavoritesEntry.COLUMN_ARTISTS_FAVORITES_NAME + " = " + android.database.DatabaseUtils.sqlEscapeString(name),
                null,
                null);

        if(artistCursor != null) {
            if(artistCursor.moveToFirst())
                favorite = true;
        }

        return favorite;
    }
}
