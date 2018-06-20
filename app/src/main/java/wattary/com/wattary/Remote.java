package wattary.com.wattary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class Remote extends AppCompatActivity {

    private static String url = "https://wattary2.herokuapp.com/main"; //--> not this  link

    Button buttonUp,buttonDown,buttonLeft,buttonRight,
            button1,button2,button3,button4,button5,button6,button7,
            button8,button9,button0,buttonMute;

    String sendString;
    JSONObject jsonObject = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        //to set name ActionBar
        /*getSupportActionBar().setTitle("Remote"); //to hide  .hide()*/
        //animation
        customType(Remote.this,"fadein-to-fadeout");
        //Init. the Buttons :
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonDown = (Button) findViewById(R.id.buttonDown);
        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button0 = (Button) findViewById(R.id.button0);
        buttonMute = (Button) findViewById(R.id.buttonMute);


        //Set Up the Channels and Vol.
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Channel Up", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("");
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Channel Down", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("");
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Volume Down", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("");
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Volume Up", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("");
            }
        });

        //Set the Buttons of Channels { From 1 to 9 including 0 and Mute }
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "0", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "0", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "1", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "2", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "3", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "4", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "5", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "6", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "7", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "8", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "9", Toast.LENGTH_SHORT).show();
                sendPost("");
            }
        });

        buttonMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mute", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("");
            }
        });


    }

    //private void sendPost(String URL) {
      /*
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", sendString);
        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            String json = gson.toJson(response);
                            String fromOmar = (String) response.get("message");
                            Toast.makeText(Remote.this, fromOmar , Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        Controller.getInstance().addToRequestQueue(req);
    }
*/
    public void sendPost(String Value)
    {
        final String TAG = "tag";

        String ServerUrl = "http://35.228.93.235:5000/signin"; //-----> not this link

        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("PhotoUrl",Value);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,ServerUrl , new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Toast.makeText(Remote.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(Remote.this,error.toString(),Toast.LENGTH_SHORT).show();

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



        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }
}
