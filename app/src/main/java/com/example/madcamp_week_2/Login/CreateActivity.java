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

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

import org.json.JSONObject;

public class CreateActivity extends AppCompatActivity {

    Button btn_next;
    Button btn_check;
    EditText ID,PW,Confirm_PW;
    String String_ID,String_PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        ID = (EditText) findViewById(R.id.Create_Email);
        PW = (EditText) findViewById(R.id.Create_PW);
        Confirm_PW = (EditText) findViewById(R.id.Create_Confirm);

        btn_check = findViewById(R.id.same_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String_ID = ID.getText().toString();
                

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
                String result = jObject.getString("result");

                if (result.equals("0")) {
                    Toast.makeText(getApplicationContext(),"Same Email Exist",Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {
                    Toast.makeText(getApplicationContext(),"Available Email",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }

}
