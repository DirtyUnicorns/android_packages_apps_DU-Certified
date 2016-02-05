package com.dirtyunicorns.certified.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.dirtyunicorns.certified.R;
import com.dirtyunicorns.certified.slidingtab.SlidingTabLayout;

public class Preferences {

    private SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

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
        return sharedPreferences.getInt(Theme, context.getResources().getColor(R.color.primary));
    }

    public int NavBarTheme() {
        return sharedPreferences.getInt(NavBarTheme, context.getResources().getColor(R.color.primary));
    }

    public boolean StatusBarTint() {
        return sharedPreferences.getBoolean(StatusBarTint, true);
    }

    public Boolean getNavigationTint() {
        return sharedPreferences.getBoolean(NavigationTint, false);
    }

    public int Drawer() {
        return sharedPreferences.getInt(Drawer, context.getResources().getColor(R.color.navdrawer_background));
    }

    public int SelectedIcon() {
        return sharedPreferences.getInt(SelectedIcon, context.getResources().getColor(R.color.drawer_selected_icon_color));
    }

    public int NormalIcon() {
        return sharedPreferences.getInt(NormalIcon, context.getResources().getColor(R.color.drawer_normal_icon_color));
    }

    public int SelectedDrawerText() {
        return sharedPreferences.getInt(SelectedDrawerText, context.getResources().getColor(R.color.drawer_selected_drawer_text_color));
    }

    public int DrawerSelector() {
        return sharedPreferences.getInt(DrawerSelector, context.getResources().getColor(R.color.material_drawer_selected));
    }

    public int DrawerText() {
        return sharedPreferences.getInt(DrawerText, context.getResources().getColor(R.color.drawer_text_color));
    }

    private Context context;

    public Preferences(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
    }

    public static void resetPrefs(Activity context) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putInt(Theme, context.getResources().getColor(R.color.primary))
                .putInt(NavBarTheme, context.getResources().getColor(R.color.primary))
                .putBoolean(NavigationTint, false)
                .putBoolean(StatusBarTint, true)
                .putInt(Drawer, context.getResources().getColor(R.color.navdrawer_background))
                .putInt(SelectedIcon, context.getResources().getColor(R.color.drawer_selected_icon_color))
                .putInt(NormalIcon, context.getResources().getColor(R.color.drawer_normal_icon_color))
                .putInt(SelectedDrawerText, context.getResources().getColor(R.color.drawer_selected_drawer_text_color))
                .putInt(DrawerSelector, context.getResources().getColor(R.color.material_drawer_selected))
                .putInt(DrawerText, context.getResources().getColor(R.color.drawer_text_color))
                .commit();

    }

    public static final String FIRST_RUN = "first_run";

    public static void themeToolbarAndNavBar(Activity activity, Toolbar toolbar) {

        Preferences preferences = new Preferences(activity);

        toolbar.setBackgroundColor(preferences.Theme());

        activity.getWindow().setStatusBarColor(preferences.Theme());

        if (preferences.getNavigationTint()) {
            activity.getWindow().setNavigationBarColor(preferences.NavBarTheme());
        } else {
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.md_black_1000));
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