package com.dirtyunicorns.certified.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.adapters.ThemesRecyclerAdapter;
import com.dirtyunicorns.certified.themes.Theme;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class AbstractThemesListFragment extends Fragment {

    @Bind(R.id.theme_list)
    RecyclerView recyclerView;

    protected abstract List<Theme> getThemeList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.themes_list_fragment, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ThemesRecyclerAdapter(getContext(), getThemeList()));

        return view;
    }

}
