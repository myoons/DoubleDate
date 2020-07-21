package com.example.madcamp_week_2.UI.Message;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Address.CustomArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    JSONArray markerArray;
    String result, url, my_ID;
    ContentValues receivecontents = new ContentValues();
    ArrayList<ReceiveItem> messages = new ArrayList<>();
    String message, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        markerArray = ((MapActivity) MapActivity.context).marker_Array;
        my_ID = ((MainActivity) MainActivity.context).my_ID;
        url = "http://192.249.19.244:1480/sojin/message_receive";

        receivecontents.put("ID",my_ID);

        MessageActivity.NetworkTask networkTask_login = new MessageActivity.NetworkTask(url, receivecontents);
        networkTask_login.execute();

        ArrayList<String> nicknames = new ArrayList<>();
        for (int i=0; i<markerArray.length(); i++) {
            try {
                nicknames.add( ((JSONObject)markerArray.get(i)).getString("nickname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ListView listView = findViewById(R.id.ListView_send);
        MessageSendAdapter listViewAdapter = new MessageSendAdapter(getApplicationContext(), R.layout.activity_message, nicknames);
        listView.setAdapter(listViewAdapter);
    }

    private class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jObject = new JSONObject(s);
                JSONArray jsonArray = jObject.getJSONArray("messages");
                System.out.println("receive :" + s);

                for (int i=0; i<jsonArray.length(); i++) {
                    message = "" + ( (JSONObject)jsonArray.get(i) ).getString("message");
                    nickname = "" + ( (JSONObject)jsonArray.get(i) ).getString("nickname");

                    ReceiveItem temp_item = new ReceiveItem(nickname, message);
                    messages.add(temp_item);
                }

                System.out.println(messages.size());

                GridView gridView = findViewById(R.id.GridView_receive);
                MessageReceiveAdapter gridViewAdapter = new MessageReceiveAdapter(getApplicationContext(), R.layout.activity_message, messages);
                gridView.setAdapter(gridViewAdapter);

            }catch (Exception e) {e.printStackTrace();}
        }
    }

}
