package wattary.com.wattary;

//created on 6/3/2018 by amryar10

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread Splash = new Thread(){
          @Override
          public void run(){
              try {
                  sleep(500); // the time of holding the splash
                  Intent splash = new Intent(getApplicationContext(),MainActivity.class);
                  startActivity(splash);
                  finish();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        };
        Splash.start();
    }
}
