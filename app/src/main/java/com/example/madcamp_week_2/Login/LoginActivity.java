package com.example.madcamp_week_2.Login;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText ID,PW;
    String String_ID,String_PW,result;
    String url;
    ContentValues logincontents = new ContentValues();
    Button btn_login;
    Button btn_create;
    Button btn_find;
    CheckBox cb_save;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = this;
        ID = (EditText) findViewById(R.id.ID);
        PW = (EditText) findViewById(R.id.PW);

        url = "http://192.249.19.244:1480/sojin/login";

        cb_save = (CheckBox) findViewById(R.id.cb_save);
        boolean boo = PreferenceManager.getBoolean(mContext,"check");

        if(boo) {
            ID.setText(PreferenceManager.getString(mContext, "id"));
            PW.setText(PreferenceManager.getString(mContext, "pw"));
            cb_save.setChecked(true);
        }

        cb_save.setOnClickListener(new CheckBox.OnClickListener() {

            @Override public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    PreferenceManager.setString(mContext, "id", ID.getText().toString());
                    PreferenceManager.setString(mContext, "pw", PW.getText().toString());
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked());
                } else {
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked());
                    PreferenceManager.clear(mContext);
                }
            }
        }) ;

        btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String_ID = ID.getText().toString();
                String_PW = PW.getText().toString();

                PreferenceManager.setString(mContext, "id", String_ID);
                PreferenceManager.setString(mContext, "pw", String_PW);

                String checkId = PreferenceManager.getString(mContext, "id");
                String checkPw = PreferenceManager.getString(mContext, "pw");

                if (TextUtils.isEmpty(checkId) || TextUtils.isEmpty(checkPw)){
                    Toast.makeText(getApplicationContext(), "ID/PW를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    logincontents.put("ID",String_ID);
                    logincontents.put("PW",String_PW);
                    NetworkTask networkTask = new NetworkTask(url, logincontents);
                    networkTask.execute();
                }
            }
        });

        btn_create = findViewById(R.id.create_account);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
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

            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jObject = new JSONObject(s);
                result = jObject.getString("result");

                if (result.equals("0")) {
                    Toast.makeText(getApplicationContext(),"Login Failed : ID or PW may be wrong",Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("ID",String_ID);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }

}
