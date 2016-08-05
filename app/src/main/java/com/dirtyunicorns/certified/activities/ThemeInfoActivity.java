package com.dirtyunicorns.certified.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.animation.OvershootInterpolator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.data.Theme;
import com.squareup.picasso.Picasso;

import at.blogc.android.views.ExpandableTextView;

import static com.dirtyunicorns.certified.R.color;
import static com.dirtyunicorns.certified.R.drawable;
import static com.dirtyunicorns.certified.R.id;
import static com.dirtyunicorns.certified.R.string;
import static com.dirtyunicorns.certified.R.style;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ThemeInfoActivity extends AppCompatActivity {

    private Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_info);
        setSupportActionBar((Toolbar) findViewById(id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        theme = (Theme) getIntent().getSerializableExtra("theme");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(id.collapsing_toolbar);

        HorizontalScrollView sView = (HorizontalScrollView) findViewById(id.hsv);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        ImageView iv = (ImageView) findViewById(id.image);
        ImageView arcus = (ImageView) findViewById(id.arcus_indicator);
        ImageView s1 = (ImageView) findViewById(id.screenshot1);
        ImageView s2 = (ImageView) findViewById(id.screenshot2);
        ImageView s3 = (ImageView) findViewById(id.screenshot3);
        ImageView s4 = (ImageView) findViewById(id.screenshot4);
        ImageView s5 = (ImageView) findViewById(id.screenshot5);
        ImageView s6 = (ImageView) findViewById(id.screenshot6);

        ImageView cb = (ImageView) findViewById(id.contactbackground);
        ImageView ci = (ImageView) findViewById(id.contactimage);

        TextView paid = (TextView) findViewById(id.paid);
        TextView arcusindicator = (TextView) findViewById(id.arcus_text);
        TextView theme_long_summary = (TextView) findViewById(id.theme_long_summary);
        TextView themeready = (TextView) findViewById(id.themeready);
        TextView themeauthor = (TextView) findViewById(id.themeauthor);

        final com.dirtyunicorns.certified.data.Uri uri = theme.getUri();

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s1");
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s2");
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s3");
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s4");
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s5");
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.launch(ThemeInfoActivity.this, (ImageView) view, "s6");
            }
        });

        final Button playstorebutton = (Button) findViewById(id.playstore_button);

        if (isPackageInstalled(this, toPkg())) {
            playstorebutton.setText(R.string.installed_button);
            playstorebutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("org.cyanogenmod.theme.chooser", "org.cyanogenmod.theme.chooser.ChooserActivity");
                    intent.putExtra("pkgName", toPkg());
                    ThemeInfoActivity.this.startActivity(intent);
                }
            });
        } else {
            playstorebutton.setText(R.string.playstore_button);
            playstorebutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent =
                            new Intent(Intent.ACTION_VIEW, Uri.parse(uri.getPlaystore()));
                    startActivity(browserIntent);
                }
            });
        }

        CardView contactbutton = (CardView) findViewById(id.themer_card);
        contactbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(uri.getContact()));
                startActivity(browserIntent);
            }
        });

        Picasso.with(getApplicationContext()).load(uri.getCollapsingToolbarThumbnail()).into(iv);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot1()).into(s1);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot2()).into(s2);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot3()).into(s3);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot4()).into(s4);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot5()).into(s5);
        Picasso.with(getApplicationContext()).load(uri.getScreenshot6()).into(s6);
        Picasso.with(getApplicationContext()).load(uri.getContactBackground()).into(cb);
        Picasso.with(getApplicationContext()).load(uri.getContactImage()).into(ci);

        paid.setText(theme.getPaid());
        themeready.setText(theme.getThemeready());
        themeauthor.setText(theme.getThemeAuthor());
        theme_long_summary.setText(theme.getThemeLongSummary());
        arcusindicator.setText(theme.getArcus());

        if (paid.getText().toString().equals("true")) paid.setText(string.paid_theme_true);
        if (paid.getText().toString().equals("false")) paid.setText(string.paid_theme_false);
        if (themeready.getText().toString().equals("true"))
            themeready.setText(string.themeready_gapps);
        if (themeready.getText().toString().equals("false")) themeready.setText("");
        if (themeready.getText().toString().equals("")) themeready.setText(string.themeready);

        if (arcusindicator.getText().toString().equals("true")) {
            arcusindicator.setText("");
            arcus.setImageDrawable(getResources().getDrawable(drawable.arcus));
        } else {
            arcusindicator.setText("");
        }

        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setTitle(theme.getThemeName());

        collapsingToolbarLayout.setExpandedTitleTextAppearance(style.expanded_toolbar_text);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(style.collapsed_toolbar_text);

        final Drawable upArrow = ContextCompat.getDrawable(this, (drawable.abc_ic_ab_back_material));
        assert upArrow != null;
        upArrow.setColorFilter(ContextCompat.getColor(this, color.arrow_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        com.dirtyunicorns.certified.Preferences.themeMe(this);

        final ExpandableTextView expandableTextView = (ExpandableTextView) this.findViewById(R.id.theme_long_summary);
        final Button buttonToggle = (Button) this.findViewById(R.id.theme_long_summary_button);

        expandableTextView.setAnimationDuration(0L);
        expandableTextView.setInterpolator(new OvershootInterpolator());
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View v) {
                expandableTextView.toggle();
                buttonToggle.setText(expandableTextView.isExpanded() ? R.string.readmore : R.string.close);
            }
        });

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                if (expandableTextView.isExpanded()) {
                    expandableTextView.collapse();
                    buttonToggle.setText(string.readmore);
                } else {
                    expandableTextView.expand();
                    buttonToggle.setText(R.string.close);
                }
            }
        });

        expandableTextView.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(final ExpandableTextView view) {
            }

            @Override
            public void onCollapse(final ExpandableTextView view) {
            }
        });
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
        String playStoreLink = theme.getUri().getPlaystore();
        return substringAfter(playStoreLink, "details?id=");
    }
}
