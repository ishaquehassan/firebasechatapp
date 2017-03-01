package com.cyberavanza.fmr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cyberavanza.fmr.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadBg();
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(getAuth().getCurrentUser() != null){
                    intent = new Intent(SplashActivity.this,ChatsActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        },3500);

    }
}
