package com.example.madcamp_week_2.UI.Myinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.madcamp_week_2.UI.Gallery.ImageActivity;

import java.io.ByteArrayOutputStream;

public class MyinfoImageClickListener implements OnClickListener {

    Context context;

    //-----------------------------------------------------------
    // imageID는 확대해서 보여줄 이미지의 리소스 ID입니다.

    Bitmap imageID;
    String title;
    String date, tag, score;

    public MyinfoImageClickListener(Context context, Bitmap imageID, String title, String date, String tag, String score) {
        this.context = context;
        this.imageID = imageID;
        this.title = title;
        this.date = date;
        this.tag = tag;
        this.score = score;
    }

    public void onClick(View v) {
        //---------------------------------------------------------
        // 확대된 이미지를 보여주는 액티비티를 실행하기 위해 인텐트 객체를 정의합니다.
        // 그리고 이 액티비티에 전달할 imageID의 값을 이 객체에 저장합니다.
        // 인텐트 객체를 정의 후 이 액티비티를 실행합니다.

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        float scale = (float) (1024/(float)imageID.getWidth());
        int image_w = (int) (imageID.getWidth() * scale);
        int image_h = (int) (imageID.getHeight() * scale);
        Bitmap resize = Bitmap.createScaledBitmap(imageID, image_w, image_h, true);
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(context, MyinfoImageActivity.class);
        intent.putExtra("image", byteArray);
        intent.putExtra("title",title);
        intent.putExtra("date",date);
        intent.putExtra("tag",tag);
        intent.putExtra("score",score);

        context.startActivity(intent);
    }
}

