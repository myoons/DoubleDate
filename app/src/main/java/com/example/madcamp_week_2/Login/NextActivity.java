package com.example.madcamp_week_2.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.HttpGet;
import com.example.madcamp_week_2.Connection.HttpPost;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

import java.io.IOException;

public class NextActivity extends AppCompatActivity {

    EditText nickname, animals, gender, number;
    Button btn_create;
    String String_ID,String_PW,String_animals,String_gender,String_nickname,String_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_next);

        Intent receivedIntent = getIntent();

        String_ID = receivedIntent.getStringExtra("ID");
        String_PW = receivedIntent.getStringExtra("PW");

        nickname = (EditText) findViewById(R.id.Create_Nickname);
        animals = (EditText) findViewById(R.id.Create_animals);
        gender = (EditText) findViewById(R.id.Create_gender);
        number = (EditText) findViewById(R.id.Create_number);

        btn_create = findViewById(R.id.create_complete);
        btn_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        String_animals = animals.getText().toString();
                        String_gender = gender.getText().toString();
                        String_nickname = nickname.getText().toString();
                        String_number = number.getText().toString();
                        String data = "ID=" + String_ID + "&PW=" + String_PW + "&animals=" + String_animals + "&gender=" + String_gender + "&number=" + String_number;
                        // Send to Server 5 information about created account
                        HttpPost postClient = new HttpPost();
                        try {
                            postClient.post("http://192.249.19.244:1480/sojin/add", data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        });
    }
}
