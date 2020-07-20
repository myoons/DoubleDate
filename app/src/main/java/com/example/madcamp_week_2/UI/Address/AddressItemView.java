package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.madcamp_week_2.R;

public class AddressItemView extends LinearLayout {

    TextView tv_name;
    TextView tv_number;
    ImageView iv_profile;

    public AddressItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.addressitem,this,true);

        tv_name = (TextView) findViewById(R.id.name);
        tv_number = (TextView) findViewById(R.id.number);
        iv_profile = (ImageView) findViewById(R.id.profile);
    }

    public void setName(String name) {
        tv_name.setText(name);
    }

    public void setNumber(String mobile) {
        tv_number.setText(mobile);
    }

    public void setImage(int resId) {
        iv_profile.setImageResource(resId);
    }
}
