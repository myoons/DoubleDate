package com.example.madcamp_week_2.UI.Gallery;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;

public class RatingActivity extends Activity {

    String result, url, url_bone, result_rating, title, my_ID;
    Context context;
    ContentValues ratingcontents = new ContentValues();
    ContentValues bonecontents = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_activity);

        url = "http://192.249.19.244:1480/yoonseo/score";

        url_bone = "http://192.249.19.244:1480/sojin/bone";
        my_ID = ((MainActivity) MainActivity.context).my_ID;
        bonecontents.put("ID",my_ID);

        context = getApplicationContext();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        final TextView tv = (TextView) findViewById(R.id.textView1);
        RatingBar rb =(RatingBar)findViewById(R.id.ratingBar1);
        LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(132,66,5), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.rgb(244,227,193), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.rgb(244,227,193), PorterDuff.Mode.SRC_ATOP);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                result_rating = String.valueOf(rating);
                tv.setText("점수 : " + rating);
            }
        });

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        // Data Convey

        ratingcontents.put("title",title);
        ratingcontents.put("score",result_rating);

        RatingActivity.NetworkTask networkTask_score = new RatingActivity.NetworkTask(url, ratingcontents);
        networkTask_score.execute();

        RatingActivity.NetworkTask networkTask_bone = new RatingActivity.NetworkTask(url, ratingcontents);
        networkTask_bone.execute();

        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        // Close Popup
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Prevent Closed by touching outer space
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // Prevent Android Back Button
        return;
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
                Toast.makeText(context,"점수가 반영되었습니다",Toast.LENGTH_SHORT).show();
            }catch (Exception e) {e.printStackTrace();}
        }
    }

    private class NetworkTask_Bone extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask_Bone(String url, ContentValues values) {
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
