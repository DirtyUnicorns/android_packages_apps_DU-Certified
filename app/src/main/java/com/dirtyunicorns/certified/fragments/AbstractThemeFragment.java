package com.dirtyunicorns.certified.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dirtyunicorns.certified.Adapter;
import com.dirtyunicorns.certified.ClickUtils;
import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.activities.ThemeInfoActivity;
import com.dirtyunicorns.certified.data.Theme;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public abstract class AbstractThemeFragment extends Fragment implements ClickUtils.OnItemClickListener {

    static SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<Theme> list = new ArrayList<>();
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_themes, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler1);

        ClickUtils.addTo(recyclerView).setOnItemClickListener(this);
        adapter = new Adapter(list);

        assert recyclerView != null;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);

        SwipeRefresh();

        downloadThemes();

        return rootView;
    }

    public abstract Call<List<Theme>> getThemeCall();

    public void downloadThemes() {

        list.clear();
        adapter.notifyDataSetChanged();

        //We're showing it during initial download
        showRefresh();

        getThemeCall().enqueue(new Callback<java.util.List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {

                if (!response.isSuccessful()) {
                    onFailure(null, null);
                } else {
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    hideRefresh();
                }

            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {
                showNotConnectedDialog();
                hideRefresh();
            }
        });
    }

    private void showRefresh() {

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void hideRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void SwipeRefresh() {
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.red,
                R.color.blue,
                R.color.yellow,
                R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadThemes();
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        Log.d("OnClick", valueOf(position));

        Theme theme = list.get(position);

        Intent intent = new Intent(getActivity(), ThemeInfoActivity.class);
        intent.putExtra("theme", theme);
        startActivity(intent);
    }

    public void showNotConnectedDialog() {
        new SnackBar.Builder(getActivity())
                .withMessageId(R.string.no_conn_content)
                .withActionMessageId(R.string.ok)
                .withStyle(SnackBar.Style.ALERT)
                .withDuration(SnackBar.MED_SNACK)
                .show();
    }
}
