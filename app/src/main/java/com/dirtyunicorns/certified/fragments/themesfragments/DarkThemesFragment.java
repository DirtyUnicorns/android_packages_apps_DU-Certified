package com.dirtyunicorns.certified.fragments.themesfragments;

import com.dirtyunicorns.certified.fragments.AbstractThemesListFragment;
import com.dirtyunicorns.certified.themes.Theme;
import com.dirtyunicorns.certified.themes.dark.Akzent;
import com.dirtyunicorns.certified.themes.dark.BlakAzurro;
import com.dirtyunicorns.certified.themes.dark.Blakkat;
import com.dirtyunicorns.certified.themes.dark.BlueHydra;
import com.dirtyunicorns.certified.themes.dark.Bsc;
import com.dirtyunicorns.certified.themes.dark.Caffene;
import com.dirtyunicorns.certified.themes.dark.DarkMTRL;
import com.dirtyunicorns.certified.themes.dark.DeepDarkness;
import com.dirtyunicorns.certified.themes.dark.EuphoriaDark;
import com.dirtyunicorns.certified.themes.dark.Flux;
import com.dirtyunicorns.certified.themes.dark.KaiUI;
import com.dirtyunicorns.certified.themes.dark.LivDark;
import com.dirtyunicorns.certified.themes.dark.LunarUI;
import com.dirtyunicorns.certified.themes.dark.MaterialFadedBlue;
import com.dirtyunicorns.certified.themes.dark.MaterialFadedGold;
import com.dirtyunicorns.certified.themes.dark.MaterialFadedGreen;
import com.dirtyunicorns.certified.themes.dark.MaterialFadedRed;
import com.dirtyunicorns.certified.themes.dark.Mono;
import com.dirtyunicorns.certified.themes.dark.Outray;
import com.dirtyunicorns.certified.themes.dark.Phlat;
import com.dirtyunicorns.certified.themes.dark.PitchBlack;
import com.dirtyunicorns.certified.themes.dark.Radius;
import com.dirtyunicorns.certified.themes.dark.SteamworksUltimate;

import java.util.ArrayList;
import java.util.List;

public class DarkThemesFragment extends AbstractThemesListFragment {

    @Override
    protected List<Theme> getThemeList() {
        List<Theme> themes = new ArrayList<>();

        themes.add(new Akzent());
        themes.add(new BlakAzurro());
        themes.add(new Blakkat());
        themes.add(new BlueHydra());
        themes.add(new Bsc());
        themes.add(new Caffene());
        themes.add(new DarkMTRL());
        themes.add(new DeepDarkness());
        themes.add(new EuphoriaDark());
        themes.add(new Flux());
        themes.add(new KaiUI());
        themes.add(new LivDark());
        themes.add(new LunarUI());
        themes.add(new MaterialFadedBlue());
        themes.add(new MaterialFadedGold());
        themes.add(new MaterialFadedGreen());
        themes.add(new MaterialFadedRed());
        themes.add(new Mono());
        themes.add(new Outray());
        themes.add(new Phlat());
        themes.add(new PitchBlack());
        themes.add(new Radius());
        themes.add(new SteamworksUltimate());

        return themes;
    }
}