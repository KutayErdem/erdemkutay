package com.example.learnin5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT  = 2000;
    private SharedPreferences _remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _remember = getSharedPreferences("RememberMe", Context.MODE_PRIVATE);
                if ( _remember.contains("username") && _remember.contains("password") ){
                    String username = _remember.getString("username", null);
                    String password = _remember.getString("password", null);
                    String name_surname = _remember.getString("name_surname", null);
                    String email = _remember.getString("email", null);
                    SharedPreferences.Editor editor = _remember.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(SplashActivity.this, LoginMainActivity.class)
                            .putExtra("_username", username).putExtra("_password", password)
                            .putExtra("_name_surname", name_surname).putExtra("_email", email));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
