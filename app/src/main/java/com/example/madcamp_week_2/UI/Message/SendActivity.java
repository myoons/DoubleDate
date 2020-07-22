package com.example.madcamp_week_2.UI.Message;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;


import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo;

import org.json.JSONObject;

import java.util.ArrayList;

public class SendActivity extends Activity {

    EditText send_message;
    String message;
    String result, url, my_nickname;
    ContentValues sendcontents = new ContentValues();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_popup);

        my_nickname = ((MainActivity) MainActivity.context).my_nickname;

        Intent intent = getIntent();
        String nickname = (String) intent.getStringExtra("nickname");

        url = "http://192.249.19.244:1480/sojin/message_send";
        sendcontents.put("my_nickname",my_nickname);
        sendcontents.put("your_nickname",nickname);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기

        Toast.makeText(this,"메세지 전송 완료",Toast.LENGTH_SHORT).show();
        send_message = (EditText) findViewById(R.id.send_message);
        message = send_message.getText().toString();
        sendcontents.put("message",message);

        SendActivity.NetworkTask networkTask_login = new SendActivity.NetworkTask(url, sendcontents);
        networkTask_login.execute();

        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
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
        }
    }

}
