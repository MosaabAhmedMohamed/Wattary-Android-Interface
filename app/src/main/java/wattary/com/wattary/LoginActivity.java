package wattary.com.wattary;

/**
 * Created by mosabahmed55 on 03/03/2018.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

static final int REQUEST_iMAGE_CAPTURE=1;
ImageView MyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // Button Capture=(Button)findViewById(R.id.CaptureLoginBu);
         MyView=(ImageView)findViewById(R.id.MyLoginImage);

//        if(!hasCamera())
//        {Capture.setEnabled(false);}
       CaptuerLogin();

    }

    public Boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void CaptuerLogin()
    {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       startActivityForResult(intent, REQUEST_iMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_iMAGE_CAPTURE &&resultCode ==RESULT_OK)
        {
            Bundle extras =data.getExtras();
            Bitmap photo =(Bitmap) extras.get("data");
            MyView.setImageBitmap(photo);
        }
    }
}