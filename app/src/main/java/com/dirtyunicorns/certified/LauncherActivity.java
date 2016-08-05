package com.dirtyunicorns.certified;

/**
 * Created by mazda on 6/17/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dirtyunicorns.certified.activities.MainActivity;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launch = new Intent(this, MainActivity.class);
        startActivity(launch);
        finish();
    }
}
