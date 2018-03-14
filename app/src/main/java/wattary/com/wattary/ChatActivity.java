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

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    //Variables
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ListView chatListView;
    EditText editText;
    FloatingActionButton sendMessage;

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
                tv.setTextColor(Color.parseColor("#FFFFFF"));

                // Generate ListView Item using TextView
                return view;
            }
        };
        chatListView.setAdapter(adapter);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();
                editText.setText(null);
                // add new item to arraylist
                arrayList.add(newItem);
                // notify listview of data changed
                adapter.notifyDataSetChanged();
            }

        });


    }
}
