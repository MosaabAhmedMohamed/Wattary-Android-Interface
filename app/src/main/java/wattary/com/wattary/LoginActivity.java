package wattary.com.wattary;

/**
 * Created by mosabahmed55 on 03/03/2018.
 */



import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;




public class LoginActivity extends Activity {

    ShowCamera showCamera;
    Camera camera;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frameLayout = (FrameLayout) findViewById(R.id.CameraView);

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

    }

    Camera.PictureCallback mpictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {


        }
    };

    public void onCapture(View view)
    {
        if(camera !=null)
        {
            camera.takePicture(null,null,mpictureCallback);
        }
    }




}