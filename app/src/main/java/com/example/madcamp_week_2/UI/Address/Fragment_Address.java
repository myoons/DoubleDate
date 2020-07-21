package com.example.madcamp_week_2.UI.Address;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Gallery.GridViewAdapter;
import net.daum.mf.map.api.MapView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class Fragment_Address extends Fragment {

    Button btn_hospital, btn_hotel, btn_shop, btn_entertain;
    public static Context context;
    // Store data in address_items
    public ArrayList<AddressItem> address_items;
    public Fragment_Address() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address,container,false);

        // Hospital Button
        btn_hospital = view.findViewById(R.id.button_hospital);
        btn_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HospitalActivity.class);
                startActivity(intent);
            }
        });

        btn_entertain = view.findViewById(R.id.button_entertains);
        btn_entertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EntertainActivity.class);
                startActivity(intent);
            }
        });

        btn_shop = view.findViewById(R.id.button_shop);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ShopActivity.class);
                startActivity(intent);
            }
        });

        btn_hotel = view.findViewById(R.id.button_hotel);
        btn_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HotelActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
