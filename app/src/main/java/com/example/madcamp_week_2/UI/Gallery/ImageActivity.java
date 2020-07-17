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
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.madcamp_week_2.R;

public class ImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_image);

        Intent receivedIntent = getIntent();
        ImageView myImageView = (ImageView) findViewById(R.id.imageView);

        byte[] byteArray = receivedIntent.getByteArrayExtra("image");
        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);

        myImageView.setImageDrawable(new BitmapDrawable(getResources(),myBitmap));
    }
}
