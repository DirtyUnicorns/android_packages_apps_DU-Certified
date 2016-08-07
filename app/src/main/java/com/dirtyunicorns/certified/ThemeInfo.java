package com.dirtyunicorns.certified;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.dirtyunicorns.certified.activities.Fullscreen;
import com.squareup.picasso.Picasso;

import at.blogc.android.views.ExpandableTextView;

import static com.dirtyunicorns.certified.R.*;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class ThemeInfo extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.theme_info);
        setSupportActionBar((Toolbar) findViewById(id.toolbar));
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final Context context;
        context = this;
        activity = this;

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(id.collapsing_toolbar);

        HorizontalScrollView sView = (HorizontalScrollView)findViewById(id.hsv);
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

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s1");
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s2");
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s3");
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s4");
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s5");
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fullscreen.launch(activity, (ImageView) view, "s6");
            }
        });

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

        CardView contactbutton = (CardView) findViewById(id.themer_card);
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

        Intent screenshot4 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot4.getStringExtra("screenshot4Uri")).into(s4);

        Intent screenshot5 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot5.getStringExtra("screenshot5Uri")).into(s5);

        Intent screenshot6 = getIntent();
        Picasso.with(getApplicationContext()).load(screenshot6.getStringExtra("screenshot6Uri")).into(s6);

        Intent contactbackground = getIntent();
        Picasso.with(getApplicationContext()).load(contactbackground.getStringExtra("contactBackgroundUri")).into(cb);

        Intent contactimage = getIntent();
        Picasso.with(getApplicationContext()).load(contactimage.getStringExtra("contactImageUri")).into(ci);

        paid.setText(intent.getStringExtra("paid"));
        theme_long_summary.setText(intent.getStringExtra("theme_long_summary"));
        arcusindicator.setText(intent.getStringExtra("arcus"));
        themeready.setText(intent.getStringExtra("themeready"));
        themeauthor.setText(intent.getStringExtra("theme_author"));

        if (paid.getText().toString().equals("true")) paid.setText(string.paid_theme_true);
        if (paid.getText().toString().equals("false")) paid.setText(string.paid_theme_false);
        if (themeready.getText().toString().equals("true")) themeready.setText(string.themeready_gapps);
        if (themeready.getText().toString().equals("false")) themeready.setText("");
        if (themeready.getText().toString().equals("")) themeready.setText(string.themeready);

        if (arcusindicator.getText().toString().equals("true")) {
            arcusindicator.setText("");
            arcus.setImageDrawable(getResources().getDrawable(drawable.arcus));
        } else {
            arcusindicator.setText("");
        }

        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setTitle(intent.getStringExtra("theme_name"));

        collapsingToolbarLayout.setExpandedTitleTextAppearance(style.expanded_toolbar_text);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(style.collapsed_toolbar_text);

        final Drawable upArrow = ContextCompat.getDrawable(context, (drawable.abc_ic_ab_back_material));
        assert upArrow != null;
        upArrow.setColorFilter(ContextCompat.getColor(context, color.arrow_color), PorterDuff.Mode.SRC_ATOP);
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
        String playStoreLink = getIntent().getStringExtra("playstoreUri");
        return substringAfter(playStoreLink, "details?id=");
    }
}