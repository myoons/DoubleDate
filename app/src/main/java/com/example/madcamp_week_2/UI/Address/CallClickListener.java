package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CallClickListener implements OnClickListener {

    Context context;
    String number;

    public CallClickListener(Context context, String number) {
        this.context = context;
        this.number = number;
    }


    public void onClick(View v) {

        Intent intent = new Intent(context, CallActivity.class);
        intent.putExtra("dial", number);
        context.startActivity(intent);
    }
}
