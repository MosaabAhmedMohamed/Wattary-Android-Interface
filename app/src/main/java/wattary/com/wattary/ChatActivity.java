package wattary.com.wattary;

import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static maes.tech.intentanim.CustomIntent.customType;

public class ChatActivity extends AppCompatActivity {

    private static String url = "http://104.196.121.39:5000/main";

    //Variables
    ListView chatListView;
    ChatArrayAdapter adapter;
    EditText editText;
    Button sendMessage;

    String sendString;
    JSONObject jsonObject = new JSONObject();

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //animation
        customType(ChatActivity.this,"fadein-to-fadeout");
        //Video Background /*created by amryar10*/
        videoView = (VideoView)findViewById(R.id.videoChat);
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
        adapter = new ChatArrayAdapter(getApplicationContext(), R.layout.message_send);
        chatListView = (ListView) findViewById(R.id.chatview);
        chatListView.setAdapter(adapter);
        chatListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatListView.setAdapter(adapter);
        //to scroll the list view to bottom on data change
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                chatListView.setSelection(adapter.getCount() - 1);
            }
        });


        sendMessage.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*MUST CREATED BECAUSE
                OF TEXTWATCHER METHOD
                */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    sendMessage.setEnabled(false);
                } else {
                    sendMessage.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*MUST CREATED BECAUSE
                OF TEXTWATCHER METHOD
                */
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();
                sendString = editText.getText().toString();
                sendMessageBallon(sendString); //User Send Message Method
                sendPost(url);  //posting function
                editText.setText(null);

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoView.start();
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
                            receiveMessageBallon(fromOmar); //Server Receive Message Method
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //OLD VOLLEY ERROR EXCEPTION
                /*VolleyLog.e("Error: ", error.getMessage());
                receiveMessageBallon("Error .. Server is Offline, Please try again later");
                tts.speak("Connection error or server is Disconnected",TextToSpeech.QUEUE_FLUSH,null); //Text to Speech Method*/
                if (error instanceof NetworkError) {
                    receiveMessageBallon("Error .. Cannot connect to Internet, Please check your connection!");
                } else if (error instanceof ServerError) {
                    receiveMessageBallon("Error .. Server is Offline, Please try again later");
                } else if (error instanceof AuthFailureError) {
                    receiveMessageBallon("Error .. Cannot connect to Internet, Please check your connection!");
                } else if (error instanceof ParseError) {
                    receiveMessageBallon("Error .. Parsing error!, Please try again after some time!");
                } else if (error instanceof TimeoutError) {
                    receiveMessageBallon("Error .. Connection TimedOut!, Please check your internet connection!");
                }
            }
        });

        // add the request object to the queue to be executed
        Controller.getInstance().addToRequestQueue(req);
    }

    private boolean sendMessageBallon (String msg) {
        adapter.add(new ChatMessage(true, msg));
        return true;
    }


    private boolean receiveMessageBallon (String msg) {
        adapter.add(new ChatMessage(false, msg));
        return true;
    }
}