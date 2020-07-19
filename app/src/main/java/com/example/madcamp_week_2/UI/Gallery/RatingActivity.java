package com.example.madcamp_week_2.UI.Gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.madcamp_week_2.R;

public class RatingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rating_activity);

        Intent intent = getIntent();

        final TextView tv = (TextView) findViewById(R.id.textView1);
        RatingBar rb =(RatingBar)findViewById(R.id.ratingBar1);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                tv.setText("rating : " + rating);
            }
        });

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        // Data Convey
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

}
