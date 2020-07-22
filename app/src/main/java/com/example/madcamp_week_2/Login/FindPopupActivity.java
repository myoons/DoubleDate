package com.example.madcamp_week_2.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Message.SendActivity;

public class FindPopupActivity extends Activity {

    String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pw_popup);

        TextView hint = findViewById(R.id.find_hint);

        Intent intent = getIntent();
        password = (String) intent.getStringExtra("password");
        String hint_password;

        if (password.length() > 5) {
            hint_password = password.substring(0,4);
            for (int i=0; i<password.length()-4; i++)
                hint_password += "*";
        } else {
            hint_password = password.substring(0,2);
            for (int i=0; i<password.length()-2; i++)
                hint_password += "*";
        }
        hint.setText(hint_password);

    }
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

}
