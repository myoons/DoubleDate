package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.madcamp_week_2.UI.Address.AddressActivity;

public class SendClickListener implements View.OnClickListener {

    Context context;
    String nickname;

    public SendClickListener(Context context, String nickname) {
        this.context = context;
        this.nickname = nickname;
    }

    public void onClick(View v) {

        Intent intent = new Intent(context, SendActivity.class);
        intent.putExtra("nickname",nickname);
        context.startActivity(intent);
    }
}
