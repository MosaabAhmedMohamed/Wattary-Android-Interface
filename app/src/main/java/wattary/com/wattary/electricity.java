package wattary.com.wattary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.SpeedView;

public class electricity extends AppCompatActivity {

    Button bElectMeasure;
    TextView tElectMeasure,tElectBill;
    PointerSpeedometer eSpeedoMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        //Init. :
        bElectMeasure = (Button) findViewById(R.id.buttonElectMeasure);
        tElectBill = (TextView) findViewById(R.id.textElectBill);
        tElectMeasure = (TextView) findViewById(R.id.textElectMeasure);
        eSpeedoMeter = (PointerSpeedometer) findViewById(R.id.speedElectView);

        //set up button
        bElectMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to 50 Km/s with Duration = 4 sec
                eSpeedoMeter.speedTo(50, 5000);
            }
        });
    }
}
