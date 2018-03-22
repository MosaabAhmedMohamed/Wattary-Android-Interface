package wattary.com.wattary;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static String url = "https://wattary2.herokuapp.com/main";

    //Variables
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView chatListView;
    EditText editText;
    FloatingActionButton sendMessage;

    String sendString;
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //declare
        sendMessage = (FloatingActionButton) findViewById(R.id.send);
        editText=(EditText)findViewById(R.id.textInput);
        //for listview
        chatListView = (ListView) findViewById(R.id.chatview);
        arrayList=new ArrayList<String>();
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.parseColor("#00c000"));

                // Set the text size of TextView (ListView Item)
                tv.setTextSize(20.0f);

                // Generate ListView Item using TextView
                return view;
            }
        };
        chatListView.setAdapter(adapter);

        if (editText != null) {
            sendMessage.setEnabled(true);
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newItem = editText.getText().toString();
                    sendString = editText.getText().toString();
                    editText.setText(null);
                    if (newItem != null) {
                        // add new item to arraylist
                        arrayList.add(newItem);
                        // notify listview of data changed
                        adapter.notifyDataSetChanged();
                    }
                    sendPost(url);
                }

            });
        }
        else
            sendMessage.setEnabled(false);

    }

    //Set the Method of SendPost to Core
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
                            arrayList.add(fromOmar);
                            adapter.notifyDataSetChanged();
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