package wattary.com.wattary;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static String url = "https://wattary2.herokuapp.com/main";

    //Variables
    /*ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;*/
    List<ChatModel> listChat = new ArrayList<>();
    ListView chatListView;
    EditText editText;
    Button sendMessage;

    String sendString;
    JSONObject jsonObject = new JSONObject();

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Video Background
        videoView = (VideoView)findViewById(R.id.videoSplash);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.chat);
        videoView.setVideoURI(video);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        videoView.start();

        //declare
        sendMessage = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.textInput);
        //for listview
        chatListView = (ListView) findViewById(R.id.chatview);
        CustomAdapter adapter = new CustomAdapter(listChat,this);
        chatListView.setAdapter(adapter);


        /*CustomAdapter adapter = new CustomAdapter(firstChat,this);
        chatListView.setAdapter(adapter);*/

        /*arrayList=new ArrayList<String>();
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
        chatListView.setAdapter(adapter);*/


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();
                ChatModel rightSide = new ChatModel(newItem,true); // user send message
                listChat.add(rightSide);
                sendString = editText.getText().toString();

                /*if (newItem != null) {
                    // add new item to arraylist
                    //arrayList.add(newItem);
                    // notify listview of data changed
                        /*//*adapter.notifyDataSetChanged();*//**//*
                }*/
                sendPost(url);  //posting function
                editText.setText(null);

            }

        });
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
                            ChatModel liftSide = new ChatModel(fromOmar,false); // get response from core
                            listChat.add(liftSide);
                            /*arrayList.add(fromOmar);
                            adapter.notifyDataSetChanged();*/
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