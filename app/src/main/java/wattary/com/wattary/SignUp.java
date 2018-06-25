package wattary.com.wattary;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static maes.tech.intentanim.CustomIntent.customType;

/**
 * Created by mosabahmed55 on 05/03/2018.
 */


public class SignUp extends AppCompatActivity {

    private EditText FristName,LastName,Passowrd;
    private Button TakeBtn;
    private Uri mImageUri;
    private static final String TAG = "";

    private CircleImageView Signup_ImageView;
    private CircleProgressbar mProgressBar;


    private Uri mCropImageUri;
    public Uri file;
    private String generatedFilePath;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        LastName =(EditText)findViewById(R.id.FNsignUp);
        FristName=(EditText)findViewById(R.id.LNsignUp);
        Passowrd=(EditText)findViewById(R.id.pass_sign_up);
        TakeBtn=findViewById(R.id.tack_pic_btn);
        Signup_ImageView =  findViewById(R.id.CropImageView);
        mProgressBar = findViewById(R.id.signup_progress_bar);
        mCropImageUri=null;
        mImageUri=null;


        firebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        // ref = firebaseDatabase.getReference("ProfileInfo");
        //   StorageReference mStorageRef;

        //animation
        customType(SignUp.this,"left-to-right");


        TakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Passowrd.getText().length() > 8)

                {
                    if (!TextUtils.isEmpty(FristName.getText().toString())
                            && !TextUtils.isEmpty(LastName.getText().toString())
                            && !TextUtils.isEmpty(Passowrd.getText().toString())) {
                        if (mUploadTask != null && mUploadTask.isInProgress()) {
                            // Signup_ImageView.setVisibility(View.INVISIBLE);
                            // mProgressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUp.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                            if (mUploadTask.isComplete()) {
                                mUploadTask = null;
                                mCropImageUri = null;
                                mImageUri = null;
                            }
                        } else {
                            //  CropImage.activity()
                            //  .setGuidelines(CropImageView.Guidelines.ON)
                            // .setMinCropResultSize(512,512)
                            //  .setAspectRatio(1, 1)
                            // .start(SignUp.this);


                            startActivityForResult(getPickImageChooserIntent(), 200);
                            // submit();


                        }
                    } else {
                        Toast.makeText(SignUp.this, "Fill the empty frist !", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(SignUp.this, "Enter at least 8 number !", Toast.LENGTH_SHORT).show();
                }
            }




    });


    }


    public void SendToLogin()
    {
        Intent LoginIntent=new Intent(SignUp.this, LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }


    /**
     * Crop the image and set it back to the  cropping view.
     */
   public void onCropImageClick(View view) {
         //   Bitmap cropped =  Signup_ImageView.getCroppedImage(500, 500);
          //  if (cropped != null)
          //  Signup_ImageView.setImageBitmap(cropped);

       Crop.pickImage(this);
       mImageUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
       Crop.of(mCropImageUri, mImageUri).asSquare().start(SignUp.this);



    }

    public void onCropImage()
    {
        mImageUri= Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(mCropImageUri,mImageUri).asSquare().start(SignUp.this);
    }


    public Intent getPickImageChooserIntent() {

// Determine Uri of camera image to  save.
        Uri outputFileUri =  getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager =  getPackageManager();

// collect all camera intents
        Intent captureIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam =  packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new  Intent(captureIntent);
            intent.setComponent(new  ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
     /*
      Intent galleryIntent = new  Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery =  packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new  Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

     */

// the main intent is the last in the  list (fucking android) so pickup the useless one
        Intent mainIntent =  allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if  (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity"))  {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);


// Create a chooser from the main  intent
        Intent chooserIntent =  Intent.createChooser(mainIntent, "Select source");

// Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,  allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture  by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new  File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;

    }

    /**
     * Get the URI of the selected image from  {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera  and gallery image.
     *
     * @param data the returned data of the  activity result
     */
    public Uri getPickImageResultUri(Intent  data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null  && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ?  getCaptureImageOutputUri() : data.getData();
    }

    /**
     * Test if we can open the given Android URI to test if permission required error is thrown.<br>
     */
    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }






    public void submit() {
        if (mImageUri != null) {


            StorageReference storageReference = mStorageRef.child(String.valueOf(System.currentTimeMillis()));
            //StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);


                mUploadTask = storageReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);

                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(SignUp.this, "uploaded", Toast.LENGTH_SHORT).show();


                                //Getting the Url Of the Image
                                Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                                generatedFilePath = downloadUri.toString()+".jpg";
                                if (generatedFilePath != null) {
                                    Log.d("signup",generatedFilePath);
                                    Send();
                                } else {
                                    Toast.makeText(SignUp.this, "fill the empty ! ", Toast.LENGTH_SHORT);
                                }
                                // Toast.makeText(SignUp.this,generatedFilePath,Toast.LENGTH_LONG).show();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "failed", Toast.LENGTH_LONG).show();


                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        })
                ;

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri =  getPickImageResultUri(data);




            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    isUriRequiresPermissions(imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else if(requestCode==RESULT_OK&&resultCode==Crop.REQUEST_PICK)
            {
                mCropImageUri = imageUri;
                Signup_ImageView.setImageURI(mCropImageUri);
                onCropImage();
            }
            if (!requirePermissions&requestCode!=Crop.REQUEST_CROP) {
                // Signup_ImageView.setImageUriAsync(imageUri);

                mCropImageUri = imageUri;
                Signup_ImageView.setImageURI(mCropImageUri);
                onCropImage();
                return;



            }
            else if(resultCode==RESULT_OK&&requestCode==Crop.REQUEST_CROP)
            {
                outputCroppedImage(resultCode, data);
            }
            //  Signup_ImageView.setImageURI(Crop.getOutput(data));

        }

        else {
            return;
        }
    }

   public void  outputCroppedImage(int code, Intent result)
    {if(code==RESULT_OK)
    {   Signup_ImageView.setRotation(-90);
        Signup_ImageView.setImageURI(Crop.getOutput(result));
        submit();
    }

    }
    public void Send()
    {
        String ServerUrl="http://104.196.121.39:5000/signup";
        String FN=FristName.getText().toString();
        String LN=LastName.getText().toString();
        String Pass=Passowrd.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("PhotoUrl", generatedFilePath);
        postParam.put("UserName",FN+" "+LN );
        postParam.put("password",Pass);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SignUp.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.i("respo",response.toString());
                        //Toast.makeText(SignUp.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }

                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("respo",error.toString());
                VolleyLog.d(TAG, "Error" + error.getMessage());
                //Log.i("rr",error.getMessage().toString());
               Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        })

        {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("PhotoUrl", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue

        int socketTimeout = 30000;//30 seconds - change to what you want

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);


        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }


}