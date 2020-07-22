package com.example.madcamp_week_2.Login;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.R;

import org.json.JSONObject;

public class Findpw_Activity extends AppCompatActivity {
    Button btn_search;
    Button btn_next;
    EditText search_email;
    String url,  password;
    ContentValues createcontents = new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_search);
        search_email = (EditText) findViewById(R.id.search_email);

        btn_search = findViewById(R.id.pw_search_btn);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = search_email.getText().toString();

                createcontents.put("ID",email);
                url = "http://192.249.19.244:1480/sojin/info";
                Findpw_Activity.NetworkTask networkTask = new Findpw_Activity.NetworkTask(url, createcontents);
                networkTask.execute();

            }
        });

        btn_next = findViewById(R.id.next_login);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPopupActivity.class);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
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

            String result;
            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);

                JSONObject jObject = new JSONObject(s);
                password = jObject.getString("PW");

            }catch (Exception e) {e.printStackTrace();}
        }
    }
}
