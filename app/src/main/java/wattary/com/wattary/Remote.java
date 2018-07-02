package wattary.com.wattary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.androidadvance.topsnackbar.TSnackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class Remote extends AppCompatActivity {

    private static String url = "http://104.196.121.39:5000/remote"; //--> not this  link

    Button buttonUp,buttonDown,buttonLeft,buttonRight,
            button1,button2,button3,button4,button5,button6,button7,
            button8,button9,button0,buttonOK;

    ToggleButton tvSwitch;
    ToggleButton buttonMute;

    String sendString;
    JSONObject jsonObject = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        //to set name ActionBar
        /*getSupportActionBar().setTitle("Remote"); //to hide  .hide()*/
        //animation
        customType(Remote.this,"left-to-right");
        //Init. the Buttons :
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonDown = (Button) findViewById(R.id.buttonDown);
        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        buttonOK = (Button) findViewById(R.id.buttonOK);
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
        tvSwitch = (ToggleButton) findViewById(R.id.tvSwitch);
        buttonMute = (ToggleButton) findViewById(R.id.buttonMute);

        buttonUp.setEnabled(false);
        buttonDown.setEnabled(false);
        buttonLeft.setEnabled(false);
        buttonRight.setEnabled(false);
        buttonOK.setEnabled(false);
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        button0.setEnabled(false);
        buttonMute.setEnabled(false);


        //Switch button
        tvSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton view, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked

                    //new Top Snackbar
                    TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content),"TV is Switched on",TSnackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    snackbarView.setBackgroundColor(Color.parseColor("#3DB161"));
                    snackbar.show();

                    buttonUp.setEnabled(true);
                    buttonDown.setEnabled(true);
                    buttonLeft.setEnabled(true);
                    buttonRight.setEnabled(true);
                    buttonOK.setEnabled(true);
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button4.setEnabled(true);
                    button5.setEnabled(true);
                    button6.setEnabled(true);
                    button7.setEnabled(true);
                    button8.setEnabled(true);
                    button9.setEnabled(true);
                    button0.setEnabled(true);
                    buttonMute.setEnabled(true);
                    sendPost("39"); //TV Code On
                }
                else
                {
                    //Do something when Switch is off/unchecked

                    //new Top Snackbar
                    TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content),"TV is Switched off",TSnackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    snackbarView.setBackgroundColor(Color.parseColor("#C12828"));
                    snackbar.show();

                    buttonUp.setEnabled(false);
                    buttonDown.setEnabled(false);
                    buttonLeft.setEnabled(false);
                    buttonRight.setEnabled(false);
                    buttonOK.setEnabled(false);
                    button0.setEnabled(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                    button5.setEnabled(false);
                    button6.setEnabled(false);
                    button7.setEnabled(false);
                    button8.setEnabled(false);
                    button9.setEnabled(false);
                    button0.setEnabled(false);
                    buttonMute.setEnabled(false);
                    sendPost("40"); //TV Code Off
                }
            }
        });


        //Set Up the Channels and Vol.
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Channel Up", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("52");
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Channel Down", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("53");
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Volume Up", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("54");
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Volume Down", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendPost("55");
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost("63");
            }
        });

        //Set the Buttons of Channels { From 1 to 9 including 0 and Mute }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "1", Toast.LENGTH_SHORT).show();
                sendPost("41");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "2", Toast.LENGTH_SHORT).show();
                sendPost("42");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "3", Toast.LENGTH_SHORT).show();
                sendPost("43");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "4", Toast.LENGTH_SHORT).show();
                sendPost("44");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "5", Toast.LENGTH_SHORT).show();
                sendPost("45");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "6", Toast.LENGTH_SHORT).show();
                sendPost("46");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "7", Toast.LENGTH_SHORT).show();
                sendPost("47");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "8", Toast.LENGTH_SHORT).show();
                sendPost("48");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "9", Toast.LENGTH_SHORT).show();
                sendPost("49");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Remote.this, "0", Toast.LENGTH_SHORT).show();
                sendPost("50");
            }
        });


        buttonMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    Snackbar.make(view, "Muted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    sendPost("51");
                } else {
                    Snackbar.make(view, "Unmuted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    sendPost("51");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent intent =new Intent(Remote.this,VoiceActivity.class);
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
}
