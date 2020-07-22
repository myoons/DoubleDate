package com.example.madcamp_week_2.UI.Address;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        String tel = "tel:" + receivedIntent.getStringExtra("dial");
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
    }
}
