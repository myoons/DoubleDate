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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

public class RecieveActivity extends Activity {

    String message, nickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recieve_popup);

        TextView tv_nickname = findViewById(R.id.receive_nickname);
        TextView tv_message = findViewById(R.id.receive_message);

        Intent intent = getIntent();
        nickname = (String) intent.getStringExtra("nickname");
        message = (String) intent.getStringExtra("message");

        tv_nickname.setText(nickname);
        tv_message.setText(message);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기

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

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
