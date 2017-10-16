package com.patrickwallin.projects.bandsintown_demo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.patrickwallin.projects.bandsintown_demo.adapter.MainTabFragmentPagerAdapter;
import com.patrickwallin.projects.bandsintown_demo.fragment.FavoritesFragment;
import com.patrickwallin.projects.bandsintown_demo.utilities.NetworkUtils;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private MainTabFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        createTabs(tabLayout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        createViewPager(viewPager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NetworkUtils networkUtils = new NetworkUtils(MainActivity.this);
        if (networkUtils.isNetworkConnected()) {

        }else {
            networkUtils.showAlertMessageAboutNoInternetConnection(false);
        }
    }

    private void createTabs(TabLayout tabLayout) {
        String[] mainMenuTabTitle = getResources().getStringArray(R.array.main_menu_tab_title);

        for(int i = 0; i < mainMenuTabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mainMenuTabTitle[i]),(i == 0));
        }
    }

    private void createViewPager(ViewPager viewPager) {
        String[] mainMenuTabTitle = getResources().getStringArray(R.array.main_menu_tab_title);

        mAdapter = new MainTabFragmentPagerAdapter(getSupportFragmentManager(),mainMenuTabTitle,this);
        viewPager.setAdapter(mAdapter);

    }
}
