package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ReceiveClickListener implements View.OnClickListener {

    Context context;
    ReceiveItem message;

    public ReceiveClickListener(Context context, ReceiveItem message) {
        this.context = context;
        this.message = message;
    }

    public void onClick(View v) {

        Intent intent = new Intent(context, RecieveActivity.class);
        intent.putExtra("message",message.getMessage());
        intent.putExtra("nickname",message.getNickname());
        context.startActivity(intent);
    }
}
