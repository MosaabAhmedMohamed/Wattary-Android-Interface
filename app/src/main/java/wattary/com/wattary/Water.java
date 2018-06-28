package wattary.com.wattary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anastr.speedviewlib.PointerSpeedometer;

public class Water extends AppCompatActivity {

    Button bWaterMeasure;
    TextView tWaterMeasure,tWaterBill;
    PointerSpeedometer wSpeedoMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //Init. :
        bWaterMeasure = (Button) findViewById(R.id.buttonWaterMeasure);
        tWaterBill = (TextView) findViewById(R.id.textWaterBill);
        tWaterMeasure = (TextView) findViewById(R.id.textWaterMeasure);
        wSpeedoMeter = (PointerSpeedometer) findViewById(R.id.speedWaterView);

        //set up button
        bWaterMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to 50 Km/s with Duration = 5 sec
                wSpeedoMeter.speedTo(70, 5000);
            }
        });
    }
}
