package com.dirtyunicorns.certified.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.dirtyunicorns.certified.R;
import com.squareup.picasso.Picasso;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

public class FullscreenActivity extends Activity {

    public static Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);

        final ImageView image = (ImageView) findViewById(R.id.image);
        image.setBackgroundColor(ContextCompat.getColor(this, R.color.fullscreen_background_color));
        Picasso.with(this).load(getIntent().getStringExtra("url")).fit().centerInside().placeholder(drawable).into(image);

        getWindow().getDecorView().setSystemUiVisibility(
                SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | SYSTEM_UI_FLAG_FULLSCREEN
                        | SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public static void launch(Activity activity, ImageView transitionView, String id) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, id);
        drawable = transitionView.getDrawable();
        transitionView.setTransitionName(id);
        Intent intent = new Intent(activity, FullscreenActivity.class);
        intent.putExtra("url", (String) transitionView.getTag());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void click(View view) {
        this.finishAfterTransition();
    }
}
