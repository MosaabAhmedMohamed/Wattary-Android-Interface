package wattary.com.wattary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class on_The_Door extends AppCompatActivity {
  private ImageView Out_imageView;
  private Button Retake_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__the__door);
        Out_imageView=findViewById(R.id.outDoor_imView);
        Retake_btn=findViewById(R.id.outDoor_btn);


        Retake_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}
