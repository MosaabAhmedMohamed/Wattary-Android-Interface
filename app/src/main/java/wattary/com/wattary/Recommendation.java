package wattary.com.wattary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.comix.overwatch.HiveProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Recommendation extends AppCompatActivity {
    protected static String Air_degree;
    protected static String bathroom_light;
    protected static String bedhroom_light;
    protected static String hallway_light;

    private static final String TAG ="dsg" ;

    private String UserName_value_ID;


    private Button Air_con_btn,Bath_btn,Bed_btn,Hall_btn,living_btn, Kitchen_btn;
    private HiveProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        progressView = (HiveProgressView) findViewById(R.id.hive_progress);
        progressView.setRainbow(false);
        progressView.setColor(0x000000);

        Air_con_btn=findViewById(R.id.air_condetioner_btn);
        Bath_btn=findViewById(R.id.bathroom_btn);
        Bed_btn=findViewById(R.id.bedroom_btn);
        Hall_btn=findViewById(R.id.hallway_btn);
        living_btn=findViewById(R.id.living_btn);
        Kitchen_btn=findViewById(R.id.kitchen_btn);

        //getting saved User name from Shared Preferences
        UserName_value_ID =null;
        String v=SharedPrefs.readSharedSettingUsername(Recommendation.this, "UserName", UserName_value_ID);
        UserName_value_ID =v;
        // Toast.makeText(VoiceActivity.this, UserName_value_ID, Toast.LENGTH_SHORT).show();


        Bath_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(  Bath_btn.getText().toString().equals("Turn off the light in the Bathroom"))
              {
                  String Value_On="Turn on the light in the Bathroom";
                  Bath_btn.setText("Turn on the light in the Bathroom");
                  sendPost(Value_On);
              }
              else if( Bath_btn.getText().toString().equals("Turn on the light in the Bathroom"))
              {   String Value_Off="Turn off the light in the Bathroom";
                  Bath_btn.setText("Turn off the light in the Bathroom");
                  sendPost(Value_Off);
              }
            }
        });

        Bed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  Bed_btn.getText().toString().equals("Turn off the light in the Bedroom"))
                {
                    String Value_On="Turn On the light in the Bedroom";
                    Bed_btn.setText("Turn On the light in the Bedroom");
                    sendPost(Value_On);
                }
                else if( Bed_btn.getText().toString().equals("Turn On the light in the Bedroom"))
                {
                    String Value_Off="Turn off the light in the Bedroom";
                    Bed_btn.setText("Turn off the light in the Bedroom");
                    sendPost(Value_Off);

                }
            }
        });

        Hall_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(  Hall_btn.getText().toString().equals("Turn Off the light in the hallway"))
                {
                    String Value_On="Turn On the light in the hallway";
                    Hall_btn.setText("Turn On the light in the hallway");
                    sendPost(Value_On);
                }
                else if( Hall_btn.getText().toString().equals("Turn On the light in the hallway"))
                {
                    String Value_Off="Turn Off the light in the hallway";
                    Hall_btn.setText("Turn Off the light in the hallway");
                    sendPost(Value_Off);

                }
            }
        });

        living_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  living_btn.getText().toString().equals("Turn Off the light in the living room"))
                {
                    String Value_On="Turn On the light in the living room";
                    living_btn.setText("Turn On the light in the living room");
                    sendPost(Value_On);
                }
                else if( living_btn.getText().toString().equals("Turn On the light in the living room"))
                {
                    String Value_Off="Turn Off the light in the living room";
                    living_btn.setText("Turn Off the light in the living room");
                    sendPost(Value_Off);

                }



            }
        });

        Kitchen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  Kitchen_btn.getText().toString().equals("Turn Off the light in the kitchen"))
                {
                    String Value_On="Turn On the light in the kitchen";
                    Kitchen_btn.setText("Turn On the light in the kitchen");
                    sendPost(Value_On);
                }
                else if( Kitchen_btn.getText().toString().equals("Turn On the light in the kitchen"))
                {
                    String Value_Off="Turn Off the light in the kitchen";
                    Kitchen_btn.setText("Turn Off the light in the kitchen");
                    sendPost(Value_Off);
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Air_degree=null;
        bathroom_light=null;
        bedhroom_light=null;
        hallway_light=null;
        Send();
    }

    public void buttonsUpdte()
    {
        progressView.setVisibility(View.GONE);
        Air_con_btn.setVisibility(View.VISIBLE);
        Bath_btn.setVisibility(View.VISIBLE);
        Bed_btn.setVisibility(View.VISIBLE);
        Hall_btn.setVisibility(View.VISIBLE);
        living_btn.setVisibility(View.VISIBLE);
        Kitchen_btn.setVisibility(View.VISIBLE);
        Air_con_btn.setText(Air_degree);
        Bath_btn.setText(bathroom_light);
        Bed_btn.setText(bedhroom_light);
        Hall_btn.setText(hallway_light);

    }

    public void Send()
    {
        String ServerUrl="http://104.196.121.39:5000/learn";

        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("hours", "12");
        postParam.put("minutes","11");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String AIR = null, BED = null, BATH = null, HALL = null;

                        //Toast.makeText(Recommendation.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            AIR = response.getString("Air Conditioner");
                            BED = response.getString("bathroom-light");
                            BATH= response.getString("bedroom-light");
                            HALL= response.getString("hallway-light");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Air_degree = AIR;
                        bedhroom_light = BED;
                        bathroom_light = BATH;
                        hallway_light = HALL;
                        if (response.toString() != null) {
                            if (Air_degree.equals("false")) {
                                Air_degree = "Turn off Air Conditioner";
                            } else if (Air_degree.equals("true")) {
                                Air_degree = "Turn On Air Conditioner";
                            }
                            if (bedhroom_light.equals("false")) {
                                bedhroom_light = "Turn off the light in the Bedroom";
                            } else if (bedhroom_light.equals("true")) {
                                bedhroom_light = "Turn On the light in the Bedroom";
                            }
                            if (bathroom_light.equals("false")) {
                                bathroom_light = "Turn off the light in the Bathroom";
                            } else if (bathroom_light.equals("true")) {
                                bathroom_light = "Turn On the light in the Bathroom";
                            }
                            if (hallway_light.equals("false")) {
                                hallway_light = "Turn Off the light in the hallway";
                            } else if (hallway_light.equals("true")) {
                                hallway_light = "Turn On the light in the hallway";
                            }
                            buttonsUpdte();
                        }
                    }

        {



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(Recommendation.this,error.toString(),Toast.LENGTH_SHORT).show();

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

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);


        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }


    public void sendPost(String Value)
    {
        final String TAG = "tag";

        String ServerUrl = "https://wattary2.herokuapp.com/main";

        RequestQueue queue = Volley.newRequestQueue(this);

        Toast.makeText(Recommendation.this,Value,Toast.LENGTH_SHORT).show();
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("message", Value);
        postParam.put("userID", UserName_value_ID);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(Recommendation.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                      //  Toast.makeText(Recommendation.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(Recommendation.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("message", "application/json; charset=utf-8");
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
