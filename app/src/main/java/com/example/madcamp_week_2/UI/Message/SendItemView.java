package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.madcamp_week_2.R;

public class SendItemView extends LinearLayout {

    TextView tv_nickname;
    ImageView iv_profile;

    public SendItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.messagesenditem,this,true);

        tv_nickname = (TextView) findViewById(R.id.send_nickname);
        iv_profile = (ImageView) findViewById(R.id.send_profile);
    }

    public void setName(String name) {
        tv_nickname.setText(name);
    }
    public void setImage(int resId) {
        iv_profile.setImageResource(resId);
    }


}
