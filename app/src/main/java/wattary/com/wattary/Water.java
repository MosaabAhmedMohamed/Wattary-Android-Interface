package wattary.com.wattary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static maes.tech.intentanim.CustomIntent.customType;

public class Water extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //animation
        customType(Water.this,"left-to-right");
    }
}
