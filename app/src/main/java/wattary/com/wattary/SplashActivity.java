package wattary.com.wattary;

//created on 6/3/2018 by amryar10

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

   LinearLayout layout1,layout2;
   Animation uptodwon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        layout1=(LinearLayout)findViewById(R.id.layout1);
        layout2=(LinearLayout)findViewById(R.id.layout2);
        uptodwon= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        layout1.setAnimation(uptodwon);
        layout2.setAnimation(uptodwon);

        Thread Splash = new Thread(){
            @Override
            public void run(){

                try {
                    sleep(2000); // the time of holding the splash
                    Intent splash = new Intent(SplashActivity.this,MainActivity.class);
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
