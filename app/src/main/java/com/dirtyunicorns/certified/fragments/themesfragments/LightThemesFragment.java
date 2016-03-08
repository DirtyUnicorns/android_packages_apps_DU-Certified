package com.dirtyunicorns.certified.fragments.themesfragments;

import com.dirtyunicorns.certified.themes.Theme;
import com.dirtyunicorns.certified.fragments.AbstractThemesListFragment;
import com.dirtyunicorns.certified.themes.light.Coalfield;
import com.dirtyunicorns.certified.themes.light.Heiva;
import com.dirtyunicorns.certified.themes.light.Liv;
import com.dirtyunicorns.certified.themes.light.OutlitePro;
import com.dirtyunicorns.certified.themes.light.Spring;

import java.util.ArrayList;
import java.util.List;

public class LightThemesFragment extends AbstractThemesListFragment {

    @Override
    protected List<Theme> getThemeList() {
        List<Theme> themes = new ArrayList<>();

        themes.add(new Coalfield());
        themes.add(new Heiva());
        themes.add(new Liv());
        themes.add(new OutlitePro());
        themes.add(new Spring());

        return themes;
    }
}
