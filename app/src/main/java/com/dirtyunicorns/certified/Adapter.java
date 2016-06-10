package com.dirtyunicorns.certified;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Item> thisItems;
    Context context;
    Item item;

    public Adapter(List<Item> items) {
        thisItems = items;
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View artistView = inflater.inflate(R.layout.theme_card_view, parent, false);
        return new ViewHolder(artistView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        item = thisItems.get(position);

        ImageView iv = holder.uri;
        Picasso.with(context).load(item.uri.getCard_thumbnail()).into(iv);


        TextView theme_name = holder.theme_name;
        theme_name.setText(item.theme_name);

        TextView theme_author = holder.theme_author;
        theme_author.setText(item.theme_author);

        TextView theme_summary = holder.theme_summary;
        theme_summary.setText(item.theme_summary);
    }

    @Override
    public int getItemCount() {
        return thisItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView uri;
        public TextView theme_name;
        public TextView theme_author;
        public TextView theme_summary;

        public ViewHolder(View itemView) {
            super(itemView);
            uri = (ImageView) itemView.findViewById(R.id.image);
            theme_name = (TextView) itemView.findViewById(R.id.theme_name);
            theme_author = (TextView) itemView.findViewById(R.id.theme_author);
            theme_summary = (TextView) itemView.findViewById(R.id.theme_summary);
        }
    }
}
