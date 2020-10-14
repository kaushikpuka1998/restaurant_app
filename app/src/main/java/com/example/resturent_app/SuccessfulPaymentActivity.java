package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class SuccessfulPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_payment);


        final LottieAnimationView la = findViewById(R.id.successfulanimation);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                la.animate().translationY(400).setDuration(1300).setStartDelay(3000);
                Intent a = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(a);
            }
        },5000);

    }
}