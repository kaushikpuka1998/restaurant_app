package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {


    ImageView logo,name,splimg;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo  = findViewById(R.id.logo );
        name = findViewById(R.id.appname);

        lottieAnimationView = findViewById(R.id.animate);
        Thread thread = new Thread()
            {

                @Override
                public void run() {

                    try{
                        sleep(4400);
                        logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
                        name.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    finally
                    {
                        Intent u = new Intent(getApplicationContext(),MainActivity.class);
                        lottieAnimationView.animate().translationY(400).setDuration(1300).setStartDelay(3000);
                        startActivity(u);
                    }

                }
            };
        thread.start();
    }
}