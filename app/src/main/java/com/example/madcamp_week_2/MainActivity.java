package com.example.madcamp_week_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.example.madcamp_week_2.UI.Tab.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import net.daum.android.map.MapView;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map_view);

//      context = this;
//      checkSelfPermission();
        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // Connect Tabs
        TabLayout tablayout = findViewById(R.id.tab);
        tablayout.setupWithViewPager(vp);

        tablayout.getTabAt(0).setIcon(R.drawable.icon_1);
        tablayout.getTabAt(1).setIcon(R.drawable.icon_2);
        tablayout.getTabAt(2).setIcon(R.drawable.icon_3);
        tablayout.getTabAt(3).setIcon(R.drawable.icon_4);
        tablayout.getTabAt(4).setIcon(R.drawable.icon_4);
        tablayout.getTabAt(5).setIcon(R.drawable.icon_4);

    }
}