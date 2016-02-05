package com.dirtyunicorns.certified.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dirtyunicorns.certified.fragments.themesfragments.ClearThemesFragment;
import com.dirtyunicorns.certified.fragments.themesfragments.DarkThemesFragment;
import com.dirtyunicorns.certified.fragments.themesfragments.LightThemesFragment;

public class ThemesPagerAdapter extends FragmentStatePagerAdapter {

    public ThemesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Light";
            case 1:
                return "Dark";
            case 2:
                return "Clear";
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new LightThemesFragment();
            case 1:
                return new DarkThemesFragment();
            case 2:
                return new ClearThemesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}

