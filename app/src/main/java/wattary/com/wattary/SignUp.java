package wattary.com.wattary;

/**
 * Created by mosabahmed55 on 05/03/2018.
 */


import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SignUp extends AppCompatActivity {

    private EditText FristNameET;
    private EditText LastNameET;
    private FrameLayout frameLayout;
    private Button CaptuerBU;


    ShowCamera showCamera;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FristNameET=(EditText)findViewById(R.id.FristNameET);
        LastNameET=(EditText)findViewById(R.id.LastNameET);
        frameLayout=(FrameLayout)findViewById(R.id.CameraViewSignUp);
        CaptuerBU=(Button)findViewById(R.id.CaptuerSignUp);


        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);
    }

    Camera.PictureCallback mpictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {


        }
    };


    public void CaptuerSignUpOnClick(View view)
    {
        if(camera !=null)
        {
            camera.takePicture(null,null,mpictureCallback);
        }
    }
}
