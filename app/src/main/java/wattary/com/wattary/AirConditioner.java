package wattary.com.wattary;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class AirConditioner extends AppCompatActivity {

    //private static String url = "https://wattary2.herokuapp.com/main"; //--> not this  link

    int temp = 24;
    Button plus,minus;
    Switch acSwitch;

    String sendString;
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_conditioner);
        //to set name ActionBar
        /*getSupportActionBar().setTitle("Air Conditioner"); //to hide  .hide()*/

        //init the Values
        //animation
        customType(AirConditioner.this,"fadein-to-fadeout");
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        acSwitch = (Switch) findViewById(R.id.acSwitch);
        //Set Enable for the buttons , Change it to true in the switch
        plus.setEnabled(false);
        minus.setEnabled(false);

        //Switch button
        acSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton view, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    Snackbar.make(view, "Air Conditioner is on", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    plus.setEnabled(true);
                    minus.setEnabled(true);
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    Snackbar.make(view, "Air Conditioner is off", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    plus.setEnabled(false);
                    minus.setEnabled(false);
                }
            }
        });

        //plus button
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = temp + 1;
                display(temp);
                sendPost("");
            }
        });

        //Minus button
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = temp - 1;
                display(temp);
                sendPost("");
            }
        });
    }
    //This method displays the given temperature value on the screen.
    private void display(int number) {
        TextView tempTextView = (TextView) findViewById(R.id.temp_text_view); //object that changing textview of quantiity
        tempTextView.setText("" + number);
    }

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
                        Toast.makeText(AirConditioner.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.v("respo",response.toString());
                        //Toast.makeText(LoginActivity.this,"is Done ",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,error.toString());
                Toast.makeText(AirConditioner.this,error.toString(),Toast.LENGTH_SHORT).show();

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



        //Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
        /* if (queue!= null) {
        queue.cancelAll(TAG);
        } */

    }

   /* private void sendPost(String URL) {

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
                            Toast.makeText(AirConditioner.this, fromOmar , Toast.LENGTH_SHORT).show();
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
    }*/
}
