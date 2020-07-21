package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.madcamp_week_2.R;

public class ReceiveItemview extends LinearLayout {

    TextView tv_nickname;
    TextView tv_message;

    public ReceiveItemview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.messagereceiveitem,this,true);

        tv_nickname = (TextView) findViewById(R.id.receive_nickname);
        tv_message = (TextView) findViewById(R.id.receive_message);
    }

    public void setName(String name) {
        tv_nickname.setText(name);
    }
    public void setMessage(String message) {
        tv_message.setText(message);
    }


}
