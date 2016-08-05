package com.dirtyunicorns.certified.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.dirtyunicorns.certified.LauncherActivity;
import com.dirtyunicorns.certified.Preferences;
import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.fragments.DarkThemesFragment;
import com.dirtyunicorns.certified.fragments.LightThemesFragment;
import com.dirtyunicorns.certified.fragments.SettingsFragment;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

import static com.dirtyunicorns.certified.Preferences.tint;

public class MainActivity extends AppCompatActivity {

    Drawer result = null;
    private static final int REQUEST_CODE = 1234;
    private static final String PREFS = "MainActivity";
    private CheckBox dontShowAgain;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs1.getBoolean(com.dirtyunicorns.certified.Preferences.FIRST_RUN, true)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().putBoolean(com.dirtyunicorns.certified.Preferences.FIRST_RUN, false).apply();
            Intent intent = new Intent(MainActivity.this, MaterialTutorialActivity.class);
            intent.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems());
            startActivityForResult(intent, REQUEST_CODE);
        }

        ConnectivityManager connManager2 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager2.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isConnected = isConnected(MainActivity.this);
        if (isConnected) {
            if (!mWifi.isConnected()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.do_not_show_again, null);
                dontShowAgain = (CheckBox) customView.findViewById(R.id.skip);

                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                String skipMessage = settings.getString(getString(R.string.skipMessage), getString(R.string.not_checked));
                if (!skipMessage.equals(getString(R.string.checked))) {
                    new BottomDialog.Builder(context)
                            .setCustomView(customView)
                            .setIcon(R.drawable.warning)
                            .setTitle(getString(R.string.warning_title))
                            .setContent(getString(R.string.warning_message))
                            .setPositiveText(getString(R.string.load_anyways))
                            .setNegativeText(getString(R.string.alright))
                            .setCancelable(false)
                            .onPositive(new BottomDialog.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog dialog) {
                                    String checkBoxResult = getString(R.string.not_checked);
                                    if (dontShowAgain.isChecked())
                                        checkBoxResult = getString(R.string.checked);
                                    SharedPreferences settings = getSharedPreferences(PREFS, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(getString(R.string.skipMessage), checkBoxResult);
                                    editor.apply();
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new BottomDialog.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog dialog) {
                                    String checkBoxResult = getString(R.string.not_checked);
                                    if (dontShowAgain.isChecked())
                                        checkBoxResult = getString(R.string.checked);
                                    SharedPreferences settings = getSharedPreferences(PREFS, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(getString(R.string.skipMessage), checkBoxResult);
                                    editor.apply();
                                    finish();
                                }
                            }).show();
                }
            }
        }

        Preferences preferences = new Preferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
        }

        com.dirtyunicorns.certified.Preferences.themeMe(this);

        if (toolbar != null) result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(1)
                .withHeader(R.layout.header)
                .withStatusBarColor(preferences.StatusBarTint() ? tint(preferences.Theme(), 0.8) : preferences.Theme())
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.light_themes)).withIcon(R.drawable.light_themes).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName(getString(R.string.dark_themes)).withIcon(R.drawable.dark_themes).withIdentifier(2).withSelectable(true),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName(getString(R.string.about_app)).withDivider(false),
                        new PrimaryDrawerItem().withName(getString(R.string.libraries)).withIcon(R.drawable.libraries).withIdentifier(3).withSelectable(true),
                        new PrimaryDrawerItem().withName(getString(R.string.settings)).withIcon(R.drawable.settings).withIdentifier(4).withSelectable(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.contact_alex)).withIdentifier(5).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                changeFragment(new LightThemesFragment());
                            } else if (drawerItem.getIdentifier() == 2) {
                                changeFragment(new DarkThemesFragment());
                            } else if (drawerItem.getIdentifier() == 3) {
                                changeFragment(new LibsBuilder()
                                        .withActivityTitle(getResources().getString(R.string.libraries))
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT)
                                        .withAboutIconShown(true)
                                        .supportFragment());
                            } else if (drawerItem.getIdentifier() == 4) {
                                changeFragment(new SettingsFragment());
                            } else if (drawerItem.getIdentifier() == 5) {
                                ContactMe();
                                return true;
                            }
                        }
                        return false;
                    }
                }).withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();

        if (result != null) {
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }

        Fragment fragment = new LightThemesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentHolder, fragment);
        transaction.commit();

    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, fragment).commit();
    }

    private void ContactMe() {
        String[] TO = {"mazdarider23@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(android.net.Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_about_du_certified));

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose_email_app)));
        } catch (android.content.ActivityNotFoundException ex) {
            new BottomDialog.Builder(this)
                    .setTitle(getString(R.string.no_email_app_found_title))
                    .setContent(getString(R.string.no_email_app_found_message))
                    .setPositiveText(getString(R.string.download_gmail))
                    .setNegativeText(getString(R.string.ok))
                    .setCancelable(false)
                    .onPositive(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(@NonNull BottomDialog dialog) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(android.net.Uri.parse(getResources().getString(R.string.gmail_link)));
                            startActivity(i);
                        }
                    })
                    .onNegative(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(@NonNull BottomDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (result.getCurrentSelection() != 1) {
            result.setSelection(1);
            return;
        }

        super.onBackPressed();
    }

    public ArrayList<TutorialItem> getTutorialItems() {
        TutorialItem tutorialItem1 = new TutorialItem(R.string.first_slide_title, R.string.first_slide_description,
                R.color.slide_1_background, R.drawable.blank, R.drawable.slide_1_drawable);

        TutorialItem tutorialItem2 = new TutorialItem(R.string.second_slide_title, R.string.second_slide_description,
                R.color.slide_2_background, R.drawable.blank, R.drawable.slide_2_drawable);

        TutorialItem tutorialItem3 = new TutorialItem(R.string.third_slide_title, R.string.third_slide_description,
                R.color.slide_3_background, R.drawable.blank, R.drawable.slide_3_drawable);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.last_slide_title, R.string.last_slide_description,
                R.color.slide_4_background, R.drawable.blank, R.drawable.slide_4_drawable);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);

        return tutorialItems;
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.hide_app_icon).setChecked(!isLauncherIconEnabled());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hide_app_icon:
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
        pm.setComponentEnabledSetting(new ComponentName(this, LauncherActivity.class), newState, PackageManager.DONT_KILL_APP);
    }

    public boolean isLauncherIconEnabled() {
        PackageManager pm = getPackageManager();
        return (pm.getComponentEnabledSetting(new ComponentName(this, LauncherActivity.class)) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }
}
