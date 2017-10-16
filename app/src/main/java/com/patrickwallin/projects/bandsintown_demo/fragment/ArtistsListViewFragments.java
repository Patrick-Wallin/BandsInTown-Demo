package com.patrickwallin.projects.bandsintown_demo.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patrickwallin.projects.bandsintown_demo.R;
import com.patrickwallin.projects.bandsintown_demo.adapter.ArtistsAdapter;
import com.patrickwallin.projects.bandsintown_demo.model.ArtistsSearchResults;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by piwal on 10/11/2017.
 */

public class ArtistsListViewFragments extends Fragment {
    @BindView(R.id.artists_recycler_view)
    RecyclerView mArtistsRecyclerView;

    private Context mContext;

    private List<ArtistsSearchResults> mArtistsSearchResultsList;
    private ArtistsAdapter mArtistsAdapter;

    public ArtistsListViewFragments() {}

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
        View view = inflater.inflate(R.layout.artists_list_view_fragment,container,false);
        ButterKnife.bind(this,view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mArtistsRecyclerView.setLayoutManager(linearLayoutManager);
        mArtistsRecyclerView.setHasFixedSize(true);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mArtistsRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mArtistsRecyclerView.addItemDecoration(mDividerItemDecoration);

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(mContext.getString(R.string.favorite_data))) {
            mArtistsSearchResultsList = Parcels.unwrap(bundle.getParcelable(mContext.getString(R.string.favorite_data)));
        }else {
            setUpData();
        }

        setUpAdapter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(mContext.getString(R.string.favorite_data))) {
            mArtistsSearchResultsList = Parcels.unwrap(bundle.getParcelable(mContext.getString(R.string.favorite_data)));
        }else {
            setUpData();
        }
    }

    private void setUpData() {
        mArtistsSearchResultsList = new ArrayList<>();
    }

    private void setUpAdapter() {
        mArtistsAdapter = new ArtistsAdapter(mArtistsSearchResultsList,mContext);
        mArtistsRecyclerView.setAdapter(mArtistsAdapter);
    }

    public void loadData(List<ArtistsSearchResults> artistsSearchResultsList) {
        mArtistsSearchResultsList = artistsSearchResultsList;
        if(mArtistsAdapter != null)
            mArtistsAdapter.setFavoriteData(artistsSearchResultsList);

    }
}
