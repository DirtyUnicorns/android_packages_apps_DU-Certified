package com.dirtyunicorns.certified.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.fragments.themesfragments.ClearThemesFragment;
import com.dirtyunicorns.certified.fragments.themesfragments.DarkThemesFragment;
import com.dirtyunicorns.certified.fragments.themesfragments.LightThemesFragment;

public class ThemesPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public ThemesPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.category_light);
            case 1:
                return context.getString(R.string.category_dark);
            case 2:
                return context.getString(R.string.category_clear);
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

