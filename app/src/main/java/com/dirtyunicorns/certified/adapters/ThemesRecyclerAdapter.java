package com.dirtyunicorns.certified.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.themes.Theme;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dirtyunicorns.certified.utils.SystemUtils.isPackageInstalled;

public class ThemesRecyclerAdapter extends RecyclerView.Adapter<ThemesRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Theme> themes;

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
        Theme theme = themes.get(position);

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
            getDialog().show();
        }


        private AlertDialog getDialog() {

            final Theme theme = themes.get(getAdapterPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setNegativeButton(context.getString(R.string.dialog_back), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            if (isPackageInstalled(context, theme.toPkg(context))) {
                builder.setPositiveButton(context.getString(R.string.dialog_apply), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity");
                        intent.putExtra("pkgName", theme.toPkg(context));
                        context.startActivity(intent);
                    }
                });

            } else {
                builder.setPositiveButton(context.getString(R.string.dialog_playstore), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(context.getString(theme.getPlayStoreLink())));
                        context.startActivity(intent);
                    }
                });
            }

            final AlertDialog dialog = builder.create();

            LayoutInflater inflater = LayoutInflater.from(context);

            View dialogLayout = inflater.inflate(R.layout.screenshots_layout, null);

            ImageView image1 = (ImageView) dialogLayout.findViewById(R.id.image1);
            ImageView image2 = (ImageView) dialogLayout.findViewById(R.id.image2);
            ImageView image3 = (ImageView) dialogLayout.findViewById(R.id.image3);

            image1.setImageResource(theme.getScreenshot1());
            image2.setImageResource(theme.getScreenshot2());
            image3.setImageResource(theme.getScreenshot3());

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);


            image1.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getHeight(), metrics);
            image2.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getHeight(), metrics);
            image3.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getHeight(), metrics);

            image1.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getWidth(), metrics);
            image2.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getWidth(), metrics);
            image3.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, theme.getWidth(), metrics);


            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            return dialog;

        }

    }

}
