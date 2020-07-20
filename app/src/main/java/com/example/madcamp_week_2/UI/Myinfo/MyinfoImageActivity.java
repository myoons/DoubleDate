package com.example.madcamp_week_2.UI.Gallery;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madcamp_week_2.R;

public class ImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_image);

        Intent receivedIntent = getIntent();
        ImageView myImageView = (ImageView) findViewById(R.id.imageView);
        TextView nameView = (TextView) findViewById(R.id.user_name);
        TextView titleView = (TextView) findViewById(R.id.title);

        byte[] byteArray = receivedIntent.getByteArrayExtra("image");
        String title = receivedIntent.getStringExtra("title");
        String name = receivedIntent.getStringExtra("name");

        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);

        myImageView.setImageDrawable(new BitmapDrawable(getResources(),myBitmap));
        nameView.setText(name);
        titleView.setText(title);

        Button rating_Button = findViewById(R.id.rating_button);
        rating_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RatingActivity.class);
                startActivity(intent);
            }
        });
    }
}
