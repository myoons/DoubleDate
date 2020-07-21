package com.example.madcamp_week_2.UI.Gallery;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.R;

public class ImageActivity extends AppCompatActivity {

    String check;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_image);

        check="0";

        Intent receivedIntent = getIntent();
        ImageView myImageView = (ImageView) findViewById(R.id.si_iV);
        TextView dateView = (TextView) findViewById(R.id.si_date);
        TextView titleView = (TextView) findViewById(R.id.si_title);
        TextView tagView = (TextView) findViewById(R.id.si_tag);

        byte[] byteArray = receivedIntent.getByteArrayExtra("image");
        final String title = receivedIntent.getStringExtra("title");
        String date = receivedIntent.getStringExtra("date");
        String tag = receivedIntent.getStringExtra("tag");

        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);

        myImageView.setImageDrawable(new BitmapDrawable(getResources(),myBitmap));
        dateView.setText(date);
        titleView.setText(title);
        tagView.setText(tag);

        Button rating_Button = findViewById(R.id.si_btn);
        rating_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check.equals("1")) {
                    Toast.makeText(getApplicationContext(),"이미 평가를 완료하셨습니다", Toast.LENGTH_SHORT).show();
                } else {
                    check = "1";
                    Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }
            }
        });
    }

    private class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Toast.makeText(getApplicationContext(),"뼈다귀 1개 획득!",Toast.LENGTH_SHORT).show();
            }catch (Exception e) {e.printStackTrace();}
        }
    }

}
