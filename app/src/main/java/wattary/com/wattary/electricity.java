package wattary.com.wattary;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.SpeedView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class electricity extends AppCompatActivity {

    Button bElectMeasure;
    TextView tElectMeasure,tElectBill;
    PointerSpeedometer eSpeedoMeter;
    int output;

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
                sendPost("64");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // do something after 1.5s = 1500 milliseconds
                        receivedPost();

                        // move to 50 Km/s with Duration = 5 sec
                        eSpeedoMeter.speedTo(output, 5000);
                    }
                }, 1500); //Time in millisecond
            }
        });

    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent intent =new Intent(electricity.this,VoiceActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendPost(String Value)
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
                        Toast.makeText(electricity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(electricity.this,error.toString(),Toast.LENGTH_SHORT).show();

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

    public void receivedPost()
    {
        final String TAG = "tag";

        String ServerUrl = "http://104.196.121.39:5000/getelectric";

        RequestQueue queue = Volley.newRequestQueue(this);



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,ServerUrl , (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(electricity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        String URL=null;
                        try {
                            int fromOmar = (int) response.get("message");
                            fromOmar = output;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.v("respo",response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("respoo",error.toString());
                Toast.makeText(electricity.this,error.toString(),Toast.LENGTH_SHORT).show();

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
