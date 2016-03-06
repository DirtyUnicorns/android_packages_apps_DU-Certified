package com.dirtyunicorns.certified.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dirtyunicorns.certified.R;

import static com.dirtyunicorns.certified.utils.SystemUtils.isPackageInstalled;

public class ScreenShotFragment extends Fragment {

    String packagename;
    View view;
    int screenshot1, screenshot2, screenshot3, playstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.screenshot_layout, container, false);

        view.setBackgroundResource(R.color.cardview_light_background);

        screenshot1 = getArguments().getInt("screenshot1");
        screenshot2 = getArguments().getInt("screenshot2");
        screenshot3 = getArguments().getInt("screenshot3");
        packagename = getArguments().getString("package");
        playstore = getArguments().getInt("playstore");

        Button button= (Button) view.findViewById(R.id.back);
        button.setText(R.string.dialog_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(ScreenShotFragment.this).commit();
            }
        });

        if (isPackageInstalled(getActivity(), packagename)) {
            Button installButton= (Button) view.findViewById(R.id.action);
            installButton.setText(R.string.dialog_apply);
            installButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity");
                    intent.putExtra("pkgName", packagename);
                    getActivity().startActivity(intent);
                }
            });


        } else {
            Button installButton= (Button) view.findViewById(R.id.action);
            installButton.setText(R.string.dialog_playstore);
            installButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(getActivity().getString(playstore)));
                    getActivity().startActivity(intent);
                }
            });

        }

        ImageView image1 = (ImageView) view.findViewById(R.id.image1);
        ImageView image2 = (ImageView) view.findViewById(R.id.image2);
        ImageView image3 = (ImageView) view.findViewById(R.id.image3);

        image1.setImageResource(screenshot1);
        image2.setImageResource(screenshot2);
        image3.setImageResource(screenshot3);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPackageInstalled(getActivity(), packagename)) {
            Button installButton = (Button) view.findViewById(R.id.action);
            installButton.setText(R.string.dialog_apply);
            installButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity");
                    intent.putExtra("pkgName", packagename);
                    getActivity().startActivity(intent);
                }
            });
        }
    }

}
