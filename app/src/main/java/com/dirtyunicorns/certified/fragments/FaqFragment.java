package com.dirtyunicorns.certified.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dirtyunicorns.certified.R;

/**
 * Created by mazda on 12/31/16.
 */
public class FaqFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.faq_layout, null);

        Button google_button = (Button) root.findViewById(R.id.google_button);
        google_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent google_plus_link = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.google_plus_link)));
                startActivity(google_plus_link);
            }
        });
        Button form_button = (Button) root.findViewById(R.id.form_button);
        form_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent json_link = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.form_link)));
                startActivity(json_link);
            }
        });
        Button resources_button = (Button) root.findViewById(R.id.resources_button);
        resources_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resources_link = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.resources_link)));
                startActivity(resources_link);
            }
        });
        return root;
    }
}