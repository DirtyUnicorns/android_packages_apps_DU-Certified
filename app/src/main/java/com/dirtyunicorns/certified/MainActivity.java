package com.dirtyunicorns.certified;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dirtyunicorns.certified.activities.SettingsActivity;
import com.dirtyunicorns.certified.activities.SlidesActivity;
import com.dirtyunicorns.certified.fragments.FaqFragment;
import com.dirtyunicorns.certified.fragments.ThemesTabsFragment;
import com.dirtyunicorns.certified.utils.Preferences;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.RecyclerViewCacheUtil;

import static com.dirtyunicorns.certified.utils.Preferences.themeToolbarAndNavBar;

public class MainActivity extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        com.dirtyunicorns.certified.utils.Preferences preferences = new Preferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        themeToolbarAndNavBar(this, toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getBoolean(com.dirtyunicorns.certified.utils.Preferences.FIRST_RUN, true)) {
            startActivity(new Intent(this, SlidesActivity.class));
            finish();
        }

        String versionName;

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final IProfile profile = new ProfileDrawerItem().withName("Alex Cruz").withEmail("mazdarider23@gmail.com").withIcon(R.drawable.alex_avatar).withIdentifier(100);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withSelectionFirstLine(getResources().getString(R.string.app_long_name) + versionName)
                .withSelectionSecondLine(getResources().getString(R.string.app_dev_name))
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withSliderBackgroundColor(preferences.Drawer())
                .withStatusBarColor(preferences.StatusBarTint() ? tint(preferences.Theme(), 0.8) : preferences.Theme())
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.home).withIconTintingEnabled(true).withSelectedIconColor(preferences.SelectedIcon()).withIconColor(preferences.NormalIcon()).withSelectedTextColor(tint(preferences.SelectedDrawerText(), 1.0)).withSelectedColor(tint(preferences.DrawerSelector(), 1.0)).withTextColor(preferences.DrawerText()).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.faq).withIcon(R.drawable.faqs).withIconTintingEnabled(true).withSelectedIconColor(preferences.SelectedIcon()).withIconColor(preferences.NormalIcon()).withSelectedTextColor(tint(preferences.SelectedDrawerText(), 1.0)).withSelectedColor(tint(preferences.DrawerSelector(), 1.0)).withTextColor(preferences.DrawerText()).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.settings).withIconTintingEnabled(true).withSelectedIconColor(preferences.SelectedIcon()).withIconColor(preferences.NormalIcon()).withSelectedTextColor(tint(preferences.SelectedDrawerText(), 1.0)).withSelectedColor(tint(preferences.DrawerSelector(), 1.0)).withTextColor(preferences.DrawerText()).withIdentifier(3),
                        new SectionDrawerItem().withName(R.string.social_header_title).withTextColor(preferences.DrawerText()).withIdentifier(4).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.googleplus_title).withIcon(R.drawable.googleplus).withIconTintingEnabled(true).withSelectedIconColor(preferences.SelectedIcon()).withIconColor(preferences.NormalIcon()).withSelectedTextColor(tint(preferences.SelectedDrawerText(), 1.0)).withSelectedColor(tint(preferences.DrawerSelector(), 1.0)).withTextColor(preferences.DrawerText()).withDescription(R.string.googleplus_summary).withIdentifier(5).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.twitter_title).withIcon(R.drawable.twitter).withIconTintingEnabled(true).withSelectedIconColor(preferences.SelectedIcon()).withIconColor(preferences.NormalIcon()).withSelectedTextColor(tint(preferences.SelectedDrawerText(), 1.0)).withSelectedColor(tint(preferences.DrawerSelector(), 1.0)).withTextColor(preferences.DrawerText()).withDescription(R.string.twitter_summary).withIdentifier(6).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {

                            FragmentManager fragmentManager =
                                    getSupportFragmentManager();

                            switch (drawerItem.getIdentifier()) {

                                case 1:
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.main_content, new ThemesTabsFragment())
                                            .commit();
                                    break;
                                case 2:
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.main_content, new FaqFragment())
                                            .commit();
                                    break;
                                case 3:
                                    Intent SettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                                    startActivityForResult(SettingsIntent, 0);
                                    break;
                                //4 is section title
                                case 5:
                                    Intent googleplus = new Intent();
                                    googleplus.setAction(Intent.ACTION_VIEW);
                                    googleplus.setData(Uri.parse(getString(R.string.googleplus_community)));
                                    startActivity(googleplus);
                                    break;
                                case 6:
                                    Intent twitter = new Intent();
                                    twitter.setAction(Intent.ACTION_VIEW);
                                    twitter.setData(Uri.parse(getString(R.string.twitter)));
                                    startActivity(twitter);
                                    break;
                                default:
                                    break;
                            }

                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);

        if (savedInstanceState == null) {
            result.setSelection(1);

            headerResult.setActiveProfile(profile);
            headerResult.getHeaderBackgroundView().setImageResource(R.drawable.header);
        }
    }

    public static int tint(int color, double factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.hide_launcher).setChecked(!isLauncherIconEnabled());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hide_launcher:
                boolean checked = item.isChecked();
                item.setChecked(!checked);
                setLauncherIconEnabled(checked);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setLauncherIconEnabled(boolean enabled) {
        int newState;
        PackageManager pm = getPackageManager();
        if (enabled) {
            newState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }
        pm.setComponentEnabledSetting(new ComponentName(this, com.dirtyunicorns.certified.LauncherActivity.class), newState, PackageManager.DONT_KILL_APP);
    }

    public boolean isLauncherIconEnabled() {
        PackageManager pm = getPackageManager();
        return (pm.getComponentEnabledSetting(new ComponentName(this, com.dirtyunicorns.certified.LauncherActivity.class)) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (result != null && result.getCurrentSelection() != 1) {
            result.setSelection(1);
        } else {
            super.onBackPressed();
        }
    }
}