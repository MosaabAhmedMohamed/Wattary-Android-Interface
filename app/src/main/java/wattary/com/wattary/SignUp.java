package wattary.com.wattary;

/**
 * Created by mosabahmed55 on 05/03/2018.
 */


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SignUp extends AppCompatActivity {

    private EditText FristNameET;
    private EditText LastNameET;
    ImageView MyView;
    private Button CaptureBu;
    static final int REQUEST_iMAGE_CAPTURE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FristNameET=(EditText)findViewById(R.id.FristNameET);
        LastNameET=(EditText)findViewById(R.id.LastNameET);
        MyView=(ImageView)findViewById(R.id.myViewSginup);
        CaptureBu=(Button)findViewById(R.id.CaptuerSignUp);

        if(!hasCamera())
        {CaptureBu.setEnabled(false);}



    }

    public Boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void CaptuerSignUpOnClick(View view)
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
