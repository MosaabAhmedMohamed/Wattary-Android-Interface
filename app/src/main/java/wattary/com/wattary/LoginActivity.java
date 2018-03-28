package wattary.com.wattary;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mosabahmed55 on 03/03/2018.
 */
public class LoginActivity extends AppCompatActivity {
    private Button mProfileImage;
    Button GaleryBu;
    Uri mImageUri;
    private ProgressBar mProgressBar;

    Bitmap photo;
    private static final int CAMERA_REQ = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private static final String TAG = "";


    private String generatedFilePath;
    String userId;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    byte[] b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProfileImage = (Button) findViewById(R.id.CaptureLoginBu);
        firebaseDatabase = FirebaseDatabase.getInstance();
        GaleryBu=(Button)findViewById(R.id.Galery) ;
        mProgressBar = findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        ref = firebaseDatabase.getReference("ProfileInfo");
        StorageReference mStorageRef;

        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser();
//        userId = firebaseUser.getUid();

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(LoginActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQ);
                }


            }
        });
        GaleryBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(LoginActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    openFileChooser();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void CameraImageCompression()
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

         b = stream.toByteArray();
    }

    public void submit(){

        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask= fileReference.putFile(mImageUri)
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


                            Toast.makeText(LoginActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            //Getting the Url Of the Image
                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                            generatedFilePath = downloadUri.toString();
                            if(generatedFilePath!=null)
                            {
                                Send();
                            }else
                            {
                                Toast.makeText(LoginActivity.this,"Try Agin",Toast.LENGTH_SHORT);
                            }
                            Toast.makeText(LoginActivity.this,generatedFilePath,Toast.LENGTH_LONG).show();
                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        }
        else if (mImageUri ==null){

        CameraImageCompression();

      StorageReference storageReference = mStorageRef.child(System.currentTimeMillis()+".jpg");
        //StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            mUploadTask=  storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
            Toast.makeText(LoginActivity.this, "uploaded", Toast.LENGTH_SHORT).show();


            //Getting the Url Of the Image
            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
            generatedFilePath = downloadUri.toString();
            if(generatedFilePath!=null)
            {
                Send();
            }else
            {
                Toast.makeText(LoginActivity.this,"Try Agin",Toast.LENGTH_SHORT);
            }
            Toast.makeText(LoginActivity.this,generatedFilePath,Toast.LENGTH_LONG).show();
        }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"failed",Toast.LENGTH_LONG).show();


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
         else {
        Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
    }}


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQ && resultCode ==RESULT_OK) {

            photo = (Bitmap) data.getExtras().get("data");
            submit();
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            submit();
        }
        else {
            return;
        }
        }

    public void Send()
    {
        String ServerUrl="https://wattary2.herokuapp.com/signin";

        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("PhotoUrl", generatedFilePath);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

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
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }
}






