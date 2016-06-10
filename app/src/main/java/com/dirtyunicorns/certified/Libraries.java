package com.dirtyunicorns.certified;

import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;

public class Libraries extends LibsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setIntent(new LibsBuilder()
                .withActivityTitle(getResources().getString(R.string.libraries))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withAboutIconShown(true)
                .intent(this));

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent MainIntent = new Intent(Libraries.this, LightThemes.class);
        startActivityForResult(MainIntent, 0);
    }
}
