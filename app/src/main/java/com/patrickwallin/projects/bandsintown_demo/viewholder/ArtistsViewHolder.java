package com.patrickwallin.projects.bandsintown_demo.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrickwallin.projects.bandsintown_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by piwal on 10/12/2017.
 */

public class ArtistsViewHolder extends ViewHolder {
    @Nullable
    @BindView(R.id.artist_image_view) public CircleImageView mArtistImageView;
    @BindView(R.id.artist_name_text_view) public TextView mArtistNameTextView;
    @BindView(R.id.favorite_image_view) public ImageView mFavoriteImageView;

    public ArtistsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
