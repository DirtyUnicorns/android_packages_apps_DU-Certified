package com.dirtyunicorns.certified;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

public class Preferences extends Activity {

    private SharedPreferences sharedPreferences;
    public static Context context;

    public static final String FIRST_RUN = "first_run";

    public static final String Theme = "Theme";
    public static final String NavBarTheme = "NavBarTheme";
    public static final String NavigationTint = "NavigationTint";

    public int Theme() {
        return sharedPreferences.getInt(Theme, ContextCompat.getColor(context, R.color.primary));
    }

    public int NavBarTheme() {
        return sharedPreferences.getInt(NavBarTheme, ContextCompat.getColor(context, R.color.primary));
    }

    public Boolean getNavigationTint() {
        return sharedPreferences.getBoolean(NavigationTint, false);
    }

    public Preferences(Context context) {
        Preferences.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void themeMe(Activity activity) {

        Preferences preferences = new Preferences(activity);

        activity.getWindow().setStatusBarColor(preferences.Theme());

        if (preferences.getNavigationTint()) {
            activity.getWindow().setNavigationBarColor(preferences.NavBarTheme());
        } else {
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(context, (R.color.navigation_drawer_color)));
        }

        activity.getWindow().setStatusBarColor(tint(preferences.Theme(), 0.8));
    }

    public static int tint(int color, double factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }

    public static String getVersionName(Context context) {
        String res = "0.0.0";
        try {
            res = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
