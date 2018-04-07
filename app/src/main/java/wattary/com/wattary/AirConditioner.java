package wattary.com.wattary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class AirConditioner extends AppCompatActivity {

    private static String url = "https://wattary2.herokuapp.com/main"; //--> not this  link

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
            }
        });

        //Minus button
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = temp - 1;
                display(temp);
            }
        });
    }
    //This method displays the given temperature value on the screen.
    private void display(int number) {
        TextView tempTextView = (TextView) findViewById(R.id.temp_text_view); //object that changing textview of quantiity
        tempTextView.setText("" + number);
    }

    private void sendPost(String URL) {

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
    }
}
