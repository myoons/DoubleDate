package com.example.madcamp_week_2.UI.Myinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Gallery.RatingActivity;

public class MyinfoImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_big_image);

        Intent receivedIntent = getIntent();

        ImageView myImageView = (ImageView) findViewById(R.id.myinfo_imageview);
        TextView myinfo_date = (TextView) findViewById(R.id.myinfo_date);
        TextView myinfo_title = (TextView) findViewById(R.id.myinfo_title);
        TextView myinfo_tag = (TextView) findViewById(R.id.myinfo_tag);
        TextView myinfo_score = (TextView) findViewById(R.id.myinfo_score);

        byte[] byteArray = receivedIntent.getByteArrayExtra("image");
        String title = receivedIntent.getStringExtra("title");
        String tag = receivedIntent.getStringExtra("tag");
        String date = receivedIntent.getStringExtra("date");
        String score = receivedIntent.getStringExtra("score");

        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);

        myImageView.setImageDrawable(new BitmapDrawable(getResources(),myBitmap));
        myinfo_date.setText(date);
        myinfo_score.setText(score);
        myinfo_tag.setText(tag);
        myinfo_title.setText(title);
    }
}
