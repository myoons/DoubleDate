package com.example.madcamp_week_2.Login;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.HttpGet;
import com.example.madcamp_week_2.Connection.HttpPost;
import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

import org.json.JSONObject;

import java.io.IOException;

public class NextActivity extends AppCompatActivity {

    EditText nickname, animals, gender, number;
    Button btn_create;
    String String_ID,String_PW,String_animals,String_gender,String_nickname,String_number;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_next);

        Intent receivedIntent = getIntent();

        String_ID = receivedIntent.getStringExtra("ID");
        String_PW = receivedIntent.getStringExtra("PW");

        nickname = (EditText) findViewById(R.id.Create_Nickname);
        animals = (EditText) findViewById(R.id.Create_animals);
        gender = (EditText) findViewById(R.id.Create_gender);
        number = (EditText) findViewById(R.id.Create_number);

        btn_create = findViewById(R.id.create_complete);
        btn_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        String_animals = animals.getText().toString();
                        String_gender = gender.getText().toString();
                        String_nickname = nickname.getText().toString();
                        String_number = number.getText().toString();
                        String data = "ID=" + String_ID + "&PW=" + String_PW + "&animals=" + String_animals +
                                "&gender=" + String_gender + "&phone_number=" + String_number + "&nickname=" + String_nickname ;
                        // Send to Server 5 information about created account
                        HttpPost postClient = new HttpPost();
                        try {
                            postClient.post("http://192.249.19.244:1480/sojin/add", data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }.start();

                finish();
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

            }catch (Exception e) {e.printStackTrace();}
        }
    }

}
