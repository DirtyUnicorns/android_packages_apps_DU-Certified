package com.dirtyunicorns.certified.fragments;

import android.support.v7.preference.PreferenceManager;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.data.ItemDownloader;
import com.dirtyunicorns.certified.data.Theme;

import retrofit2.Call;

public class MultiColorThemesFragment extends AbstractThemeFragment {
    @Override
    public Call<java.util.List<Theme>> getThemeCall() {
        if (PreferenceManager.getDefaultSharedPreferences(getContext())
                .getBoolean(getString(R.string.freethemes_switch), false)) {
            return ItemDownloader.getThemesApi().getFreeMultiColorThemes();
        } else {
            return ItemDownloader.getThemesApi().getMultiColorThemes();
        }
    }
}