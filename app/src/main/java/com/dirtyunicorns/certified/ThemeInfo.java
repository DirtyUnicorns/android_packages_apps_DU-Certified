package com.dirtyunicorns.certified;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.dirtyunicorns.certified.R.*;
import static org.apache.commons.lang3.StringUtils.substringAfter;

@SuppressWarnings({"ConstantConditions", "deprecation"})
public class ThemeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.theme_info);
        setSupportActionBar((Toolbar) findViewById(id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Context context;

        context = this;

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(id.collapsing_toolbar);

        ImageView iv = (ImageView) findViewById(id.image);
        ImageView s1 = (ImageView) findViewById(id.screenshot1);
        ImageView s2 = (ImageView) findViewById(id.screenshot2);
        ImageView s3 = (ImageView) findViewById(id.screenshot3);

        TextView paid = (TextView) findViewById(id.paid);
        TextView themeready = (TextView) findViewById(id.themeready);

        final Button playstorebutton = (Button) findViewById(id.playstore_button);

        if (isPackageInstalled(context, toPkg())) {
            playstorebutton.setText(R.string.installed_button);
            playstorebutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity");
                    intent.putExtra("pkgName", toPkg());
                    context.startActivity(intent);
                }
            });
        } else {
            playstorebutton.setText(R.string.playstore_button);
            playstorebutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent =
                            new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("playstoreUri")));
                    startActivity(browserIntent);
                }
            });
        }

        Button contactbutton = (Button) findViewById(id.contact_button);
        contactbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("contactUri")));
                startActivity(browserIntent);
            }
        });

        Intent intent = getIntent();
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("collapsing_toolbar_thumbnail")).into(iv);

        Intent screenshot1 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot1.getStringExtra("screenshot1Uri")).into(s1);

        Intent screenshot2 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot2.getStringExtra("screenshot2Uri")).into(s2);

        Intent screenshot3 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot3.getStringExtra("screenshot3Uri")).into(s3);

        paid.setText(intent.getStringExtra("paid"));
        themeready.setText(intent.getStringExtra("themeready"));

        if (paid.getText().toString().equals("true")) paid.setText(string.paid_theme_true);
        if (paid.getText().toString().equals("false")) paid.setText(string.paid_theme_false);
        if (themeready.getText().toString().equals("true")) themeready.setText(string.themeready_gapps);
        if (themeready.getText().toString().equals("false")) themeready.setText("");
        if (themeready.getText().toString().equals("")) themeready.setText(string.themeready);

        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setTitle(intent.getStringExtra("theme_name"));
        collapsingToolbarLayout.setExpandedTitleTextAppearance(style.expanded_toolbar_text);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(style.collapsed_toolbar_text);

        final Drawable upArrow = getResources().getDrawable(drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(color.arrow_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        com.dirtyunicorns.certified.Preferences.themeMe(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case id.share:
                Intent intent = getIntent();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        getString(string.theme_share_first_part) + " "
                                + intent.getStringExtra("theme_name") + "!" + "\n\n" + intent.getStringExtra("playstoreUri") + "\n\n" + getString(string.theme_share_second_part) + "\n\n" + getString(string.theme_share_stay_dirty));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isPackageInstalled(Context context, String pkg) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(pkg, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public String toPkg() {
        String playStoreLink = getIntent().getStringExtra("playstoreUri");
        return substringAfter(playStoreLink, "details?id=");
    }
}