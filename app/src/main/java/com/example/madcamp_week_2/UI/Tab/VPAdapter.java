package com.example.madcamp_week_2.UI.Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.madcamp_week_2.UI.Address.Fragment_Address;
import com.example.madcamp_week_2.UI.Gallery.Fragment_Gallery;
import com.example.madcamp_week_2.UI.Main.Fragment_Main;
import com.example.madcamp_week_2.UI.Message.Fragment_Message;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> items;
    public VPAdapter(@NonNull FragmentManager fm) {
        super(fm);

        items = new ArrayList<Fragment>();
        items.add(new Fragment_Main());
        items.add(new Fragment_Address());
        items.add(new Fragment_Gallery());
        items.add(new Fragment_Message());

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
