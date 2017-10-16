package com.patrickwallin.projects.bandsintown_demo;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrickwallin.projects.bandsintown_demo.model.ArtistsDetailResponse;
import com.patrickwallin.projects.bandsintown_demo.rest.RetrofitApiClient;
import com.patrickwallin.projects.bandsintown_demo.rest.RetrofitDetailApiInterface;
import com.patrickwallin.projects.bandsintown_demo.rest.RetrofitSearchApiInterface;
import com.patrickwallin.projects.bandsintown_demo.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ArtistDetailActivity extends AppCompatActivity {
    @BindView(R.id.artist_name_text_view)
    TextView mArtistNameTextView;
    @BindView(R.id.artist_full_image_view)
    ImageView mArtistImageView;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @Nullable
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @Nullable
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.value_tracker_text_view)
    TextView mValueTrackerTextView;
    @BindView(R.id.value_upcoming_event_text_view)
    TextView mValueUpcomingEventTextView;

    private String mArtistName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
        ButterKnife.bind(this);

        if(mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }else {
            getSupportActionBar().setElevation(0);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.artist_name))) {
            mArtistName = getIntent().getStringExtra(getString(R.string.artist_name));
            mArtistNameTextView.setText(mArtistName);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                setTitle(mArtistName);

            NetworkUtils networkUtils = new NetworkUtils(this);
            if (networkUtils.isNetworkConnected()) {
                RetrofitDetailApiInterface detailApiService =
                        RetrofitApiClient
                                .getDetailClient(this)
                                .create(RetrofitDetailApiInterface.class);

                Call<ArtistsDetailResponse> call = detailApiService.getDetailResult(mArtistName,8888);
                call.enqueue(new Callback<ArtistsDetailResponse>() {
                    @Override
                    public void onResponse(Call<ArtistsDetailResponse> call, Response<ArtistsDetailResponse> response) {
                        String imageUrl = response.body().getImageUrl().trim();
                        int trackerCount = response.body().getTrackerCount();
                        int upcomingEventCount = response.body().getUpcomingEventCount();
                        mValueTrackerTextView.setText(String.valueOf(trackerCount));
                        mValueUpcomingEventTextView.setText(String.valueOf(upcomingEventCount));

                        if(!imageUrl.isEmpty()) {
                            Picasso.with(getApplicationContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.ic_no_image_available)
                                    .into(mArtistImageView, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            if(mAppBarLayout != null) {
                                                mAppBarLayout.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mAppBarLayout.setExpanded(false, true);
                                                    }
                                                }, 500L);
                                            }

                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }else {
                            Picasso.with(getApplicationContext())
                                    .load(R.drawable.ic_no_image_available)
                                    .into(mArtistImageView, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            if(mAppBarLayout != null) {
                                                mAppBarLayout.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mAppBarLayout.setExpanded(false, true);
                                                    }
                                                }, 500L);
                                            }
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onFailure(Call<ArtistsDetailResponse> call, Throwable t) {
                        NetworkUtils networkUtils = new NetworkUtils(ArtistDetailActivity.this);
                        networkUtils.showAlertMessageBasedOnErrorMessage(t.getMessage());
                    }
                });

            }else {
                networkUtils.showAlertMessageAboutNoInternetConnection(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
