package com.ferra.taga.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ferra.taga.Global;
import com.ferra.taga.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Global.setBottomNavigationBarColorColor(SplashActivity.this, R.color.bottom);
        Global.setSystemBarColor(SplashActivity.this, R.color.bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, EnterMobileActivity.class));
                finish();
            }
        }, 3000);
    }
}
