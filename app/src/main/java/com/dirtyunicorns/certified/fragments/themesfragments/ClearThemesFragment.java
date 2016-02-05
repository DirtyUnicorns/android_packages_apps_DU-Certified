package com.dirtyunicorns.certified.fragments.themesfragments;

import com.dirtyunicorns.certified.fragments.AbstractThemesListFragment;
import com.dirtyunicorns.certified.themes.Theme;
import com.dirtyunicorns.certified.themes.clear.BluXs;
import com.dirtyunicorns.certified.themes.clear.KlearKat;
import com.dirtyunicorns.certified.themes.clear.Sprite;

import java.util.ArrayList;
import java.util.List;

public class ClearThemesFragment extends AbstractThemesListFragment {

    @Override
    protected List<Theme> getThemeList() {
        List<Theme> themes = new ArrayList<>();

        themes.add(new BluXs());
        themes.add(new KlearKat());
        themes.add(new Sprite());

        return themes;
    }
}
