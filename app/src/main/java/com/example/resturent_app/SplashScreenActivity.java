package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


       Thread thread = new Thread(){


           @Override
           public void run() {
               try {
                    sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               finally {

                   Intent a = new Intent(SplashScreenActivity.this,MainActivity.class);
                   startActivity(a);
               }
           }
       };

       thread.start();
    }
}