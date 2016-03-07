package com.dirtyunicorns.certified.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.adapters.ThemesPagerAdapter;
import com.dirtyunicorns.certified.slidingtab.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dirtyunicorns.certified.utils.Preferences.themePager;

public class ThemesTabsFragment extends Fragment {

    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    @Bind(R.id.pager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.themes_tab_fragment, container, false);

        ButterKnife.bind(this, view);

        PagerAdapter pagerAdapter = new ThemesPagerAdapter(getActivity(), getFragmentManager());

        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(pagerAdapter);

        mSlidingTabLayout.setCustomTabView(R.layout.toolbar_tab, R.id.toolbar_tab_txtCaption);
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getActivity(), R.color.tab_indicator_color));
        mSlidingTabLayout.setViewPager(mViewPager);

        themePager(getContext(), mSlidingTabLayout);

        return view;

    }

}
