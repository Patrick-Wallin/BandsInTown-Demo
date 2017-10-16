package com.patrickwallin.projects.bandsintown_demo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.patrickwallin.projects.bandsintown_demo.R;
import com.patrickwallin.projects.bandsintown_demo.fragment.ArtistsFragment;
import com.patrickwallin.projects.bandsintown_demo.fragment.FavoritesFragment;

import timber.log.Timber;

/**
 * Created by piwal on 10/11/2017.
 */

public class MainTabFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mMainMenuTabTitle = null;
    private Context mContext;

    public MainTabFragmentPagerAdapter(FragmentManager fm, String[] mainMenuTabTitle, Context context) {
        super(fm);
        mMainMenuTabTitle = mainMenuTabTitle;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(mMainMenuTabTitle != null && mMainMenuTabTitle.length > position) {
            if(mMainMenuTabTitle[position].equalsIgnoreCase(mContext.getString(R.string.artists))) {
                return ArtistsFragment.newInstance();
            }else {
                return FavoritesFragment.newInstance();
            }
        }
        return FavoritesFragment.newInstance(); // default
    }

    @Override
    public int getCount() {
        return (mMainMenuTabTitle != null ? mMainMenuTabTitle.length : 0);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
