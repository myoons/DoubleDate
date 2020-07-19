package com.example.madcamp_week_2.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madcamp_week_2.R;

public class NextActivity extends AppCompatActivity {

    EditText nickname, animals, gender;
    Button btn_create;
    String String_ID,String_PW,String_animals,String_gender,String_nickname;

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

        btn_create = findViewById(R.id.create_complete);
        btn_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String_animals = animals.getText().toString();
                String_gender = gender.getText().toString();
                String_nickname = nickname.getText().toString();

                // Send to Server 5 information about created account

                /**
                 * 서버에 5가지 정보 보내는 코드
                 * ID / PW / animals / gender / nickname
                 */
            }
        });
    }


}
