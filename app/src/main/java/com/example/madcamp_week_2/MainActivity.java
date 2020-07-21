package com.example.madcamp_week_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.Login.LoginActivity;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo_image;
import com.example.madcamp_week_2.UI.Tab.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    String result, ID, url;
    public Myinfo myinfo;
    ContentValues logincontents = new ContentValues();
    public static Context context;
    ArrayList<Myinfo_image> myinfo_image_arr = new ArrayList<Myinfo_image>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


//      context = this;
//      checkSelfPermission();

        Intent receivedIntent = getIntent();
        ID = receivedIntent.getStringExtra("ID");
        url = "http://192.249.19.244:1480/sojin/setup";

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // Connect Tabs
        TabLayout tablayout = findViewById(R.id.tab);
        tablayout.setupWithViewPager(vp);

        tablayout.getTabAt(0).setIcon(R.drawable.main);
        tablayout.getTabAt(1).setIcon(R.drawable.address);
        tablayout.getTabAt(2).setIcon(R.drawable.gallery);
        tablayout.getTabAt(3).setIcon(R.drawable.message);
        tablayout.getTabAt(4).setIcon(R.drawable.myinfo);

        logincontents.put("ID",ID);
        MainActivity.NetworkTask networkTask = new MainActivity.NetworkTask(url, logincontents);
        networkTask.execute();

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
                System.out.println(s);
                JSONObject jObject = new JSONObject(s);

                myinfo.setID(jObject.getString("ID"));
                myinfo.setAnimals(jObject.getString("animals"));
                myinfo.setBone(jObject.getString("bone"));
                myinfo.setGender(jObject.getString("gender"));
                myinfo.setNumber(jObject.getString("number"));
                myinfo.setNickname(jObject.getString("nickname"));
                myinfo.setScore(jObject.getString("score"));

                JSONArray image_arr = jObject.getJSONArray("images");

                for (int i=0; i<image_arr.length(); i++) {
                    JSONObject imageObject = (JSONObject) image_arr.get(i);
                    /**
                     * 받은 이미지 String --> Bitmap으로 바꿔주는 코드 필요하다
                     */
//                    Myinfo_image temp_object = new Myinfo_image(imageObject.getString("image"),
//                                                                imageObject.getString("date"),
//                                                                imageObject.getString("title"),
//                                                                imageObject.getString("tag"),
//                                                                imageObject.getString("score"));
//
//                    myinfo_image_arr.add(temp_object);
                }

                myinfo.setImages(myinfo_image_arr);
            }catch (Exception e) {e.printStackTrace();}
        }
    }

}