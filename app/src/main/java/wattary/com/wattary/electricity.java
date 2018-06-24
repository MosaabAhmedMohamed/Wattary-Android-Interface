package wattary.com.wattary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static maes.tech.intentanim.CustomIntent.customType;

public class electricity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        //animation
        customType(electricity.this,"left-to-right");
    }
}
