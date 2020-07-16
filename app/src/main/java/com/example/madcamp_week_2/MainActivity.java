package com.example.madcamp_week_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      context = this;
//      checkSelfPermission();

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // Connect Tabs
        TabLayout tablayout = findViewById(R.id.tab);
        tablayout.setupWithViewPager(vp);

        // Insert icons to Tab Layout
        tablayout.getTabAt(0).setIcon(R.drawable.icon_1);
        tablayout.getTabAt(1).setIcon(R.drawable.icon_2);
        tablayout.getTabAt(2).setIcon(R.drawable.icon_3);
        tablayout.getTabAt(3).setIcon(R.drawable.icon_4);

    }
}