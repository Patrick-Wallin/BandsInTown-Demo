package com.patrickwallin.projects.bandsintown_demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patrickwallin.projects.bandsintown_demo.R;
import com.patrickwallin.projects.bandsintown_demo.data.ArtistsFavoritesContract;
import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResults;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by piwal on 10/11/2017.
 */

public class FavoritesFragment extends Fragment {
    private Context mContext;

    private ArtistsListViewFragments mArtistsListViewFragments;

    public static FavoritesFragment newInstance() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        return favoritesFragment;
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
        View view = inflater.inflate(R.layout.favorites_fragment,container,false);

        List<ArtistsSearchResults> artistsSearchResultsList = ArtistsFavoritesContract.getFavoriteArtistData(mContext);
        Bundle bundle= new Bundle();
        bundle.putParcelable(mContext.getString(R.string.favorite_data), Parcels.wrap(artistsSearchResultsList));

        mArtistsListViewFragments = (ArtistsListViewFragments)getFragmentManager().findFragmentById(R.id.artists_favorite_frame_layout);
        if(mArtistsListViewFragments == null || !mArtistsListViewFragments.isInLayout()) {
            mArtistsListViewFragments = new ArtistsListViewFragments();
            mArtistsListViewFragments.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.artists_favorite_frame_layout,mArtistsListViewFragments);
            fragmentTransaction.commit();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
