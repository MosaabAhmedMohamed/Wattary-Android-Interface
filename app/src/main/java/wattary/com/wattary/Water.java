package wattary.com.wattary;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.anastr.speedviewlib.PointerSpeedometer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Water extends AppCompatActivity {

    Button bWaterMeasure;
    TextView tWaterMeasure,tWaterBill;
    PointerSpeedometer wSpeedoMeter;
    int output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //Init. :
        bWaterMeasure = (Button) findViewById(R.id.buttonWaterMeasure);
        tWaterBill = (TextView) findViewById(R.id.textWaterBill);
        tWaterMeasure = (TextView) findViewById(R.id.textWaterMeasure);
        wSpeedoMeter = (PointerSpeedometer) findViewById(R.id.speedWaterView);

        //set up button
        bWaterMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost("65");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // do something after 1.5s = 1500 milliseconds
                        receivedPost("getWater");

                        // move to 50 Km/s with Duration = 5 sec
                        wSpeedoMeter.speedTo(output, 5000);
                    }
                }, 1500); //Time in millisecond
            }
        });
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
                        Toast.makeText(Water.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(Water.this,error.toString(),Toast.LENGTH_SHORT).show();

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

    public void receivedPost(String Value)
    {
        final String TAG = "tag";

        String ServerUrl = "http://104.196.121.39:5000/getWater";

        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("code",Value);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int fromOmar = (int) response.get("message");
                            fromOmar = output;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(Water.this,error.toString(),Toast.LENGTH_SHORT).show();

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
}