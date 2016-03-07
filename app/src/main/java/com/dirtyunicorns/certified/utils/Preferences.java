package com.dirtyunicorns.certified.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.slidingtab.SlidingTabLayout;

public class Preferences {

    private SharedPreferences sharedPreferences;

    public static final String Theme = "Theme";
    public static final String NavBarTheme = "NavBarTheme";
    public static final String StatusBarTint = "StatusBarTint";
    public static final String NavigationTint = "NavigationTint";
    public static final String Drawer = "Drawer";
    public static final String SelectedIcon = "SelectedIcon";
    public static final String NormalIcon = "NormalIcon";
    public static final String SelectedDrawerText = "SelectedDrawerText";
    public static final String DrawerSelector = "DrawerSelector";
    public static final String DrawerText = "DrawerText";

    public int Theme() {
        return sharedPreferences.getInt(Theme, ContextCompat.getColor(context, R.color.primary));
    }

    public int NavBarTheme() {
        return sharedPreferences.getInt(NavBarTheme, ContextCompat.getColor(context, R.color.primary));
    }

    public boolean StatusBarTint() {
        return sharedPreferences.getBoolean(StatusBarTint, true);
    }

    public Boolean getNavigationTint() {
        return sharedPreferences.getBoolean(NavigationTint, false);
    }

    public int Drawer() {
        return sharedPreferences.getInt(Drawer, ContextCompat.getColor(context, R.color.navdrawer_background));
    }

    public int SelectedIcon() {
        return sharedPreferences.getInt(SelectedIcon, ContextCompat.getColor(context, R.color.drawer_selected_icon_color));
    }

    public int NormalIcon() {
        return sharedPreferences.getInt(NormalIcon, ContextCompat.getColor(context, R.color.drawer_normal_icon_color));
    }

    public int SelectedDrawerText() {
        return sharedPreferences.getInt(SelectedDrawerText, ContextCompat.getColor(context, R.color.drawer_selected_drawer_text_color));
    }

    public int DrawerSelector() {
        return sharedPreferences.getInt(DrawerSelector, ContextCompat.getColor(context, R.color.material_drawer_selected));
    }

    public int DrawerText() {
        return sharedPreferences.getInt(DrawerText, ContextCompat.getColor(context, R.color.drawer_text_color));
    }

    private Context context;

    public Preferences(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void resetPrefs(Activity context) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putInt(Theme, ContextCompat.getColor(context, R.color.primary))
                .putInt(NavBarTheme, ContextCompat.getColor(context, R.color.primary))
                .putBoolean(NavigationTint, false)
                .putBoolean(StatusBarTint, true)
                .putInt(Drawer, ContextCompat.getColor(context, R.color.navdrawer_background))
                .putInt(SelectedIcon, ContextCompat.getColor(context, R.color.drawer_selected_icon_color))
                .putInt(NormalIcon, ContextCompat.getColor(context, R.color.drawer_normal_icon_color))
                .putInt(SelectedDrawerText, ContextCompat.getColor(context, R.color.drawer_selected_drawer_text_color))
                .putInt(DrawerSelector, ContextCompat.getColor(context, R.color.material_drawer_selected))
                .putInt(DrawerText, ContextCompat.getColor(context, R.color.drawer_text_color))
                .apply();

    }

    public static final String FIRST_RUN = "first_run";

    public static void themeToolbarAndNavBar(Activity activity, Toolbar toolbar) {

        Preferences preferences = new Preferences(activity);

        toolbar.setBackgroundColor(preferences.Theme());

        activity.getWindow().setStatusBarColor(preferences.Theme());

        if (preferences.getNavigationTint()) {
            activity.getWindow().setNavigationBarColor(preferences.NavBarTheme());
        } else {
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.md_black_1000));
        }

        if (preferences.StatusBarTint()) {
            activity.getWindow().setStatusBarColor(tint(preferences.Theme(), 0.8));
        } else {
            activity.getWindow().setStatusBarColor(preferences.Theme());
        }
    }

    public static void themePager(Context context, SlidingTabLayout slidingTabLayout) {

        Preferences preferences = new Preferences(context);

        slidingTabLayout.setBackgroundColor(preferences.Theme());
    }

    public static int tint(int color, double factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }
}