package com.dirtyunicorns.certified;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;

public class Preferences extends Activity {

    private SharedPreferences sharedPreferences;
    public static Context context;

    public static final String FIRST_RUN = "first_run";

    public ProgressBar pbar;

    public static final String Theme = "Theme";
    public static final String NavBarTheme = "NavBarTheme";
    public static final String StatusBarTint = "StatusBarTint";
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

    public boolean StatusBarTint() {
        return sharedPreferences.getBoolean(StatusBarTint, true);
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

        if (preferences.StatusBarTint()) {
            activity.getWindow().setStatusBarColor(tint(preferences.Theme(), 0.8));
        } else {
            activity.getWindow().setStatusBarColor(preferences.Theme());
        }
    }

    public static int tint(int color, double factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }

    public ArrayList<TutorialItem> getTutorialItems() {
        TutorialItem tutorialItem1 = new TutorialItem(R.string.first_slide_title, R.string.first_slide_description,
                R.color.slide_1_background, R.drawable.blank, R.drawable.slide_1_drawable);

        TutorialItem tutorialItem2 = new TutorialItem(R.string.second_slide_title, R.string.second_slide_description,
                R.color.slide_2_background,  R.drawable.blank,  R.drawable.slide_2_drawable);

        TutorialItem tutorialItem3 = new TutorialItem(R.string.third_slide_title, R.string.third_slide_description,
                R.color.slide_3_background,  R.drawable.blank,  R.drawable.slide_3_drawable);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.last_slide_title, R.string.last_slide_description,
                R.color.slide_4_background,  R.drawable.blank, R.drawable.slide_4_drawable);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);

        return tutorialItems;
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
