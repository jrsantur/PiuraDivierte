package com.project.workgroup.piuradivierte;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.workgroup.piuradivierte.view.activities.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {


    public static final int segundos = 2;
    public static final int milisegundos = segundos * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        delay();

    }

    public void delay(){
        new CountDownTimer(milisegundos , 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Intent i  = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}
