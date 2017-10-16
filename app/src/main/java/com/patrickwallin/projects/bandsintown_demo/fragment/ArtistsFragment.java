package com.patrickwallin.projects.bandsintown_demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.patrickwallin.projects.bandsintown_demo.MainActivity;
import com.patrickwallin.projects.bandsintown_demo.R;
import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResults;
import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResultsResponse;
import com.patrickwallin.projects.bandsintown_demo.rest.RetrofitApiClient;
import com.patrickwallin.projects.bandsintown_demo.rest.RetrofitSearchApiInterface;
import com.patrickwallin.projects.bandsintown_demo.utilities.NetworkUtils;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by piwal on 10/11/2017.
 */

public class ArtistsFragment extends Fragment {
    @BindView(R.id.artists_search_view)
    SearchView mArtistsSearchView;

    private Context mContext;
    private List<ArtistsSearchResults> mArtistsSearchResultsList;

    public static ArtistsFragment newInstance() {
        ArtistsFragment artistsFragment = new ArtistsFragment();
        return artistsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artists_fragment,container,false);
        ButterKnife.bind(this,view);

        mArtistsSearchView.setBackgroundResource(R.color.colorSearchBackground);

        ArtistsListViewFragments artistsListViewFragments = (ArtistsListViewFragments)getFragmentManager().findFragmentById(R.id.artists_frame_layout);
        if(artistsListViewFragments == null || !artistsListViewFragments.isInLayout()) {
            artistsListViewFragments = new ArtistsListViewFragments();
            if(savedInstanceState != null) {
                mArtistsSearchResultsList = Parcels.unwrap(savedInstanceState.getParcelable(mContext.getString(R.string.favorite_data)));
            }
            if(mArtistsSearchResultsList != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(mContext.getString(R.string.favorite_data),Parcels.wrap(mArtistsSearchResultsList));
                artistsListViewFragments.setArguments(bundle);
            }
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.artists_frame_layout,artistsListViewFragments);
            fragmentTransaction.commit();
        }

        final ArtistsListViewFragments finalArtistsListViewFragments = artistsListViewFragments;
        mArtistsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                NetworkUtils networkUtils = new NetworkUtils(mContext);
                if (networkUtils.isNetworkConnected()) {
                    RetrofitSearchApiInterface searchApiService =
                            RetrofitApiClient
                                    .getSearchClient(mContext)
                                    .create(RetrofitSearchApiInterface.class);

                    Call<ArtistsSearchResultsResponse> call = searchApiService.getSearchResults(s);
                    call.enqueue(new Callback<ArtistsSearchResultsResponse>() {
                        @Override
                        public void onResponse(Call<ArtistsSearchResultsResponse> call, Response<ArtistsSearchResultsResponse> response) {
                            mArtistsSearchResultsList = response.body().getData();
                            if(mArtistsSearchResultsList != null) {
                                finalArtistsListViewFragments.loadData(mArtistsSearchResultsList);
                            }
                        }

                        @Override
                        public void onFailure(Call<ArtistsSearchResultsResponse> call, Throwable t) {
                            NetworkUtils networkUtils = new NetworkUtils(mContext);
                            networkUtils.showAlertMessageBasedOnErrorMessage(t.getMessage());
                        }
                    });

                }else {
                    networkUtils.showAlertMessageAboutNoInternetConnection(false);
                }

                mArtistsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(mContext.getString(R.string.favorite_data), Parcels.wrap(mArtistsSearchResultsList));
    }
}
