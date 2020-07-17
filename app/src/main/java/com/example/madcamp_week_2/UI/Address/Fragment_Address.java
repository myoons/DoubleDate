package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madcamp_week_2.R;

import java.util.ArrayList;


public class Fragment_Address extends Fragment {

    public static Context context;
    // Store data in address_items
    public ArrayList<AddressItem> adress_items;

    public Fragment_Address() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address,container,false);
        return view;
    }


}