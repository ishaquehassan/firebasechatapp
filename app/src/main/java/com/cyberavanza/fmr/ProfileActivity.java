package com.cyberavanza.fmr;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyberavanza.fmr.base.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadBg();
        try {
            final Toolbar myToolbar = (Toolbar) findViewById(R.id.chatsToolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Profile");

        ImageView bgSimplePattern = (ImageView) findViewById(R.id.bgSimplePattern);
        Glide.with(this).load(R.drawable.pattern).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).centerCrop().into(bgSimplePattern);


    }
}
