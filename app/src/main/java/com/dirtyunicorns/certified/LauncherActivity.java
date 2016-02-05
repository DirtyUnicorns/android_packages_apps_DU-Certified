package com.dirtyunicorns.certified;

/**
 * Created by mazda on 2/5/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launch = new Intent(this, com.dirtyunicorns.certified.MainActivity.class);
        startActivity(launch);
        finish();
    }
}