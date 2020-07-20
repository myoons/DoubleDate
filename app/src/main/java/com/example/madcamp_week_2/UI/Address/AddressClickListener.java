package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.example.madcamp_week_2.UI.Gallery.ImageActivity;

import java.io.ByteArrayOutputStream;

public class AddressClickListener implements View.OnClickListener {

    Context context;
//    Bitmap image;
    String name;
    String number;

    public AddressClickListener(Context context, String number, String name) {
        this.context = context;
//        this.image = image;
        this.number = number;
        this.name = name;
    }

    public void onClick(View v) {
        //---------------------------------------------------------
        // 확대된 이미지를 보여주는 액티비티를 실행하기 위해 인텐트 객체를 정의합니다.
        // 그리고 이 액티비티에 전달할 imageID의 값을 이 객체에 저장합니다.
        // 인텐트 객체를 정의 후 이 액티비티를 실행합니다.

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        float scale = (float) (1024/(float)image.getWidth());
//        int image_w = (int) (image.getWidth() * scale);
//        int image_h = (int) (image.getHeight() * scale);
//
//        Bitmap resize = Bitmap.createScaledBitmap(image, image_w, image_h, true);
//        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(context, AddressActivity.class);
//        intent.putExtra("image", byteArray);
        intent.putExtra("number",number);
        intent.putExtra("name",name);

        context.startActivity(intent);
    }

}
