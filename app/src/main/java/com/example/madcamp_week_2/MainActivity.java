package com.example.madcamp_week_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
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
import com.example.madcamp_week_2.UI.Gallery.ImageInfo;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo_image;
import com.example.madcamp_week_2.UI.Tab.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    String result, ID, url, url_image;
    String date,score,tag,title, nickname;
    public String my_ID, my_nickname;
    Bitmap bitmap;
    public Myinfo myinfo;
    ContentValues logincontents = new ContentValues();
    ContentValues imagecontents = new ContentValues();
    public static Context context;
    ArrayList<Myinfo_image> myinfo_image_arr = new ArrayList<Myinfo_image>();
    public ArrayList<ImageInfo> gallery_images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        checkSelfPermission();

        Intent receivedIntent = getIntent();
        my_ID = receivedIntent.getStringExtra("ID");
        url = "http://192.249.19.244:1480/sojin/info";
        url_image = "http://192.249.19.244:1480/yoonseo/all";

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

        tablayout.getTabAt(0).setIcon(R.drawable.icon_1);
        tablayout.getTabAt(1).setIcon(R.drawable.icon_2);
        tablayout.getTabAt(2).setIcon(R.drawable.icon_3);
        tablayout.getTabAt(3).setIcon(R.drawable.icon_4);
        tablayout.getTabAt(4).setIcon(R.drawable.icon_4);

        logincontents.put("ID",my_ID);
        MainActivity.NetworkTask networkTask_login = new MainActivity.NetworkTask(url, logincontents);
        networkTask_login.execute();

        MainActivity.NetworkTask_Image networkTask_image = new MainActivity.NetworkTask_Image(url_image, imagecontents);
        networkTask_image.execute();

    }

    public void checkSelfPermission() {
        String temp = "";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.CAMERA + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_CONTACTS + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_CONTACTS + " ";
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_CALL_LOG + " ";
        }

        if (TextUtils.isEmpty(temp) == false) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        } else {
            Toast.makeText(this, "All permission Accepeted",Toast.LENGTH_SHORT).show();
        }
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
                System.out.println("myinfo_main : " + s);
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

                my_nickname = jObject.getString("nickname");

                myinfo = new Myinfo(jObject.getString("ID"),
                                    jObject.getString("nickname"),
                                    jObject.getString("phone_number"),
                                    jObject.getString("bone"),
                                    jObject.getString("score"),
                                    jObject.getString("animals"),
                                    jObject.getString("gender"),
                                    myinfo_image_arr);

            }catch (Exception e) {e.printStackTrace();}
        }
    }

    private class NetworkTask_Image extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask_Image (String url, ContentValues values) {
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
                if (s!=null ) {
                    JSONObject jObject = new JSONObject(s);
                    JSONArray image_arr = jObject.getJSONArray("pictures");

                    for (int i = 0; i < image_arr.length(); i++) {
                        JSONObject imageObject = (JSONObject) image_arr.get(i);

                        System.out.println("image json : " + imageObject);
                        System.out.println("image : " + imageObject.getString("image"));
                        ID = imageObject.getString("ID");
                        date = imageObject.getString("date");
                        score = imageObject.getString("pscore");
                        tag = imageObject.getString("tag");
                        title = imageObject.getString("title");
                        bitmap = StringToBitmap(imageObject.getString("image").replace(" ","+"));
                        nickname = imageObject.getString("nickname");

                        ImageInfo temp_galleryobject = new ImageInfo(bitmap, nickname, title, date, tag);
                        gallery_images.add(temp_galleryobject);

                        if (my_ID.equals(ID)) {
                            Myinfo_image temp_object = new Myinfo_image(bitmap, date, title, tag, score);
                            myinfo_image_arr.add(temp_object);
                        }
                    }

                    myinfo.setImages(myinfo_image_arr);
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}