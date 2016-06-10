package com.dirtyunicorns.certified;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dirtyunicorns.certified.ClickUtils.OnItemClickListener;
import com.github.mrengineer13.snackbar.SnackBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

@SuppressWarnings("deprecation")
public class LightThemes extends AppCompatActivity implements OnItemClickListener {

    static SwipeRefreshLayout mSwipeRefreshLayout;

    private Drawer result = null;

    private static final int REQUEST_CODE = 1234;

    Preferences Preferences;

    final Context context = this;

    ArrayList<Item> List = new ArrayList<>();
    Adapter adapter;
    Item item;

    private static final String PREFS = "LightThemes";
    private CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.Preferences = new Preferences(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs1.getBoolean(com.dirtyunicorns.certified.Preferences.FIRST_RUN, true)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().putBoolean(com.dirtyunicorns.certified.Preferences.FIRST_RUN, false).apply();
            Intent intent = new Intent(LightThemes.this, MaterialTutorialActivity.class);
            intent.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, Preferences.getTutorialItems());
            startActivityForResult(intent, REQUEST_CODE);
        }

        Preferences.pbar = (ProgressBar) findViewById(R.id.progressBar2);
        RecyclerView Items = (RecyclerView) findViewById(R.id.recycler1);

        ClickUtils.addTo(Items).setOnItemClickListener(this);
        adapter = new Adapter(List);

        assert Items != null;
        Items.setAdapter(adapter);
        Items.setHasFixedSize(true);
        Items.setLayoutManager(new LinearLayoutManager(this));

        BackgroundTask bt = new BackgroundTask();
        bt.execute();

        SwipeRefresh();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
        }

        com.dirtyunicorns.certified.Preferences.themeMe(this);

        Drawer result = null;
        if (toolbar != null) result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(1)
                .withHeader(R.layout.header)
                .withStatusBarColor(Preferences.StatusBarTint() ? tint(Preferences.Theme(), 0.8) : Preferences.Theme())
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.light_themes)).withIcon(R.drawable.light_themes).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName(getString(R.string.dark_themes)).withIcon(R.drawable.dark_themes).withIdentifier(2).withSelectable(true),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName(getString(R.string.about_app)).withDivider(false),
                        new PrimaryDrawerItem().withName(getString(R.string.libraries)).withIcon(R.drawable.libraries).withIdentifier(3).withSelectable(true),
                        new PrimaryDrawerItem().withName(getString(R.string.settings)).withIcon(R.drawable.settings).withIdentifier(4).withSelectable(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.contact_alex)).withIdentifier(5).withSelectable(true)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        boolean isConnected = isConnected(LightThemes.this);

                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                if (isConnected) {
                                    Intent MainIntent = new Intent(LightThemes.this, LightThemes.class);
                                    startActivityForResult(MainIntent, 0);
                                } else {
                                    showNotConnectedDialog();
                                }
                                return true;
                            } else if (drawerItem.getIdentifier() == 2) {
                                Intent requestAlt = new Intent(LightThemes.this, DarkThemes.class);
                                startActivity(requestAlt);
                                return true;
                            } else if (drawerItem.getIdentifier() == 3) {
                                Intent MainIntent = new Intent(LightThemes.this, Libraries.class);
                                startActivityForResult(MainIntent, 0);
                            } else if (drawerItem.getIdentifier() == 4) {
                                Intent SettingsIntent = new Intent(LightThemes.this, Settings.class);
                                startActivityForResult(SettingsIntent, 0);
                                return true;
                            } else if (drawerItem.getIdentifier() == 5) {
                                if (isConnected) {
                                    ContactMe();
                                } else {
                                    showNotConnectedDialog();
                                }
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
    }

    public void SwipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.red,
                R.color.blue,
                R.color.yellow,
                R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List.clear();
                        new BackgroundTask().execute();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        item = List.get(position);

        Intent intent = new Intent(this, ThemeInfo.class);
        intent.putExtra("collapsing_toolbar_thumbnail", item.uri.getCollapsing_toolbar_thumbnail());
        intent.putExtra("screenshot1Uri", item.uri.getScreenshot1());
        intent.putExtra("screenshot2Uri", item.uri.getScreenshot2());
        intent.putExtra("screenshot3Uri", item.uri.getScreenshot3());
        intent.putExtra("theme_name", item.theme_name);
        intent.putExtra("theme_author", item.theme_author);
        intent.putExtra("theme_summary", item.theme_summary);
        intent.putExtra("playstoreUri", item.uri.getPlaystore());
        intent.putExtra("contactUri", item.uri.getContact());
        intent.putExtra("paid", item.paid);
        intent.putExtra("themeready", item.themeready);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            boolean isConnected = isConnected(LightThemes.this);
            if (isConnected) {
                super.onPreExecute();
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
                Preferences.pbar.setVisibility(View.VISIBLE);
            } else {
                showNotConnectedDialog();
                Preferences.pbar.setVisibility(View.GONE);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            boolean isConnected = isConnected(LightThemes.this);
            String URL = "https://raw.githubusercontent.com/DirtyUnicorns/android_packages_apps_DU-Certified/README/jsons/lightthemes.json";

            if (isConnected) {
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(URL)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (response != null) {
                        return response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showNotConnectedDialog();
            }
                return URL;
        }

        @Override
        protected void onPostExecute(final String data) {
            ConnectivityManager connManager2 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager2.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isConnected = isConnected(LightThemes.this);
            if (isConnected) {
                if (!mWifi.isConnected()) {
                    try {
                        setJson(data);
                        Preferences.pbar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater adbInflater = LayoutInflater.from(getApplicationContext());
                    View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
                    dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
                    alertDialogBuilder.setView(eulaLayout);
                    alertDialogBuilder.setIcon(R.drawable.warning);
                    alertDialogBuilder.setTitle(R.string.warning_title);
                    alertDialogBuilder.setMessage(R.string.warning_message)
                            .setCancelable(false)
                            .setPositiveButton(R.string.alright, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String checkBoxResult = getString(R.string.not_checked);
                                    if (dontShowAgain.isChecked())
                                        checkBoxResult = getString(R.string.checked);
                                    SharedPreferences settings = getSharedPreferences(PREFS, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(getString(R.string.skipMessage), checkBoxResult);
                                    editor.apply();
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.load_anyways, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String checkBoxResult = getString(R.string.not_checked);
                                    if (dontShowAgain.isChecked())
                                        checkBoxResult = getString(R.string.checked);
                                    SharedPreferences settings = getSharedPreferences(PREFS, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(getString(R.string.skipMessage), checkBoxResult);
                                    editor.apply();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    SharedPreferences settings = getSharedPreferences(PREFS, 0);
                    String skipMessage = settings.getString(getString(R.string.skipMessage), getString(R.string.not_checked));
                    if (!skipMessage.equals(getString(R.string.checked)))
                        alertDialog.show();
                } else {
                    try {
                        setJson(data);
                        Preferences.pbar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                showNotConnectedDialog();
            }
        }

        private void setJson(String data) throws JSONException {
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                item = new Item(jsonObject.getString("theme_name"),
                                jsonObject.getString("theme_author"),
                                jsonObject.getString("theme_summary"),
                                jsonObject.getString("paid"),
                                jsonObject.getString("themeready"));

                item.uri.setCard_thumbnail(jsonObject.getJSONObject("uri").getString("card_thumbnail"));
                item.uri.setCollapsing_toolbar_thumbnail(jsonObject.getJSONObject("uri").getString("collapsing_toolbar_thumbnail"));
                item.uri.setScreenshot1(jsonObject.getJSONObject("uri").getString("screenshot1"));
                item.uri.setScreenshot2(jsonObject.getJSONObject("uri").getString("screenshot2"));
                item.uri.setScreenshot3(jsonObject.getJSONObject("uri").getString("screenshot3"));
                item.uri.setPlaystore(jsonObject.getJSONObject("uri").getString("playstore"));
                item.uri.setContact(jsonObject.getJSONObject("uri").getString("contact"));

                List.add(item);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
            Preferences.pbar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private static int tint(int color, double factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (result != null) {
            result.setSelection(1);
        } else {
            super.onBackPressed();
        }
    }

    public void ContactMe() {
        String[] TO = {"mazdarider23@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(android.net.Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_about_du_certified));

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose_email_app)));
        } catch (android.content.ActivityNotFoundException ex) {
            new MaterialDialog.Builder(this)
                    .title(getString(R.string.no_email_app_found_title))
                    .content(getString(R.string.no_email_app_found_message))
                    .neutralText(getString(R.string.ok))
                    .neutralColor(getResources().getColor(R.color.colorPrimary))
                    .positiveText(getString(R.string.download_gmail))
                    .positiveColor(getResources().getColor(R.color.colorPrimary))
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(android.net.Uri.parse(getResources().getString(R.string.gmail_link)));
                            startActivity(i);
                        }

                        @Override
                        public void onNeutral(MaterialDialog dialog) {
                            super.onNeutral(dialog);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public void showNotConnectedDialog() {
        Preferences.pbar.setVisibility(View.GONE);
        new SnackBar.Builder(this)
                .withMessageId(R.string.no_conn_content)
                .withActionMessageId(R.string.ok)
                .withStyle(SnackBar.Style.ALERT)
                .withDuration(SnackBar.MED_SNACK)
                .show();
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
