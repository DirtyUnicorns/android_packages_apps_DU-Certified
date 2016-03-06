package com.dirtyunicorns.certified.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.fragments.ScreenShotFragment;
import com.dirtyunicorns.certified.themes.Theme;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dirtyunicorns.certified.utils.SystemUtils.isPackageInstalled;

public class ThemesRecyclerAdapter extends RecyclerView.Adapter<ThemesRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Theme> themes;
    Theme theme;

    public ThemesRecyclerAdapter(Context context, List<Theme> themes) {
        this.context = context;
        this.themes = themes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        theme = themes.get(position);

        holder.title.setText(theme.getName());
        holder.author.setText(theme.getAuthor());
        holder.icon.setImageResource(theme.getIcon());

        if (isPackageInstalled(context, theme.toPkg(context))) {
            holder.price.setText(R.string.listing_installed);
        } else {
            holder.price.setText(theme.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.theme_title)
        TextView title;

        @Bind(R.id.theme_about)
        TextView author;

        @Bind(R.id.theme_price)
        TextView price;

        @Bind(R.id.theme_icon)
        ImageView icon;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Theme theme = themes.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putInt("screenshot1", theme.getScreenshot1());
            bundle.putInt("screenshot2", theme.getScreenshot2());
            bundle.putInt("screenshot3", theme.getScreenshot3());
            bundle.putString("package", theme.toPkg(context));
            bundle.putInt("playstore", theme.getPlayStoreLink());

            ScreenShotFragment screenFrag= new ScreenShotFragment();
            screenFrag.setArguments(bundle);
            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, screenFrag)
                    .addToBackStack(null)
                    .commit();
        }

    }

}
