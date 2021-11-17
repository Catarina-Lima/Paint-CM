package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CountDownTimer cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 5 segundos
        cd = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                toMain(findViewById(R.id.splash));
            }
        }.start();


    }

    public void toMain(View view) {

        cd.cancel();
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}