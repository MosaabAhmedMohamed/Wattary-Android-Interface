package wattary.com.wattary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class on_The_Door extends AppCompatActivity {
  private ImageView Out_imageView;
  private Button Retake_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__the__door);
        Out_imageView=findViewById(R.id.outDoor_imView);
        Retake_btn=findViewById(R.id.outDoor_btn);

       // Out_imageView.setImageURI();


        Retake_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("110");
                GET();


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        send("110");
        GET();
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent intent =new Intent(on_The_Door.this,VoiceActivity.class);
        startActivity(intent);
        finish();
    }

    public void send(String Value)
    {
        final String TAG = "tag";

        String ServerUrl = "http://104.196.121.39:5000/remote";

        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("code",Value);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(on_The_Door.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(on_The_Door.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("code", "application/json; charset=utf-8");
                return headers;
            }



        };



        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }

    public void GET()
    {
        final String TAG = "tag";

        String ServerUrl = "http://104.196.121.39:5000/getcamera";

        RequestQueue queue = Volley.newRequestQueue(this);



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,ServerUrl , (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(on_The_Door.this,response.toString(),Toast.LENGTH_SHORT).show();
                        String URL=null;
                        try {
                            URL=response.getString("imageUrl");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Picasso.with(on_The_Door.this).load(URL).into(Out_imageView);
                        Toast.makeText(on_The_Door.this,URL,Toast.LENGTH_SHORT).show();

                        Log.v("respo",response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("respoo",error.toString());
                Toast.makeText(on_The_Door.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        int socketTimeout = 15000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }
}
