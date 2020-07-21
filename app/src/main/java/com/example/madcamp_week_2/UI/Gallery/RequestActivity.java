package com.example.madcamp_week_2.UI.Gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.R;

import java.io.ByteArrayOutputStream;

public class RequestActivity extends AppCompatActivity {

    String title, tag, nickname, date;
    byte[] ba_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();

        Intent receivedintent = getIntent();
        nickname =receivedintent.getStringExtra("nickname");
        tag= receivedintent.getStringExtra("tag");
        title = receivedintent.getStringExtra("title");
        date = receivedintent.getStringExtra("date");
        ba_image = receivedintent.getByteArrayExtra("image");

        Fragment_Gallery fragment_gallery = new Fragment_Gallery();
        getSupportFragmentManager().beginTransaction().replace(R.id.request_layout,fragment_gallery).commit();

        Bundle bundle = new Bundle(5); // 파라미터는 전달할 데이터 개수
        bundle.putString("title",title);
        bundle.putString("tag",tag);
        bundle.putByteArray("image",ba_image);
        bundle.putString("Nickname",nickname);
        bundle.putString("date",date);

        System.out.println("Bundle act");

        fragment_gallery.setArguments(bundle);
    }
}
