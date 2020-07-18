package com.example.madcamp_week_2;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;

public class LoginActivity extends AppCompatActivity {

    EditText ID,PW;
    String String_ID,String_PW;
    String url;
    ContentValues logincontents = new ContentValues();
    Button btn_login;
    Button btn_create;
    Button btn_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ID = (EditText) findViewById(R.id.ID);
        PW = (EditText) findViewById(R.id.PW);

        url = "http://192.249.19.244:1480/sojin/login";

        btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String_ID = ID.getText().toString();
                String_PW = PW.getText().toString();

                logincontents.put("ID",String_ID);
                logincontents.put("PW",String_PW);

                NetworkTask networkTask = new NetworkTask(url, logincontents);
                networkTask.execute();

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

            System.out.println(s);
        }

    }

}
