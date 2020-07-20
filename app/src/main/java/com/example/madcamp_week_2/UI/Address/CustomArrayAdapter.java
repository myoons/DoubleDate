package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.madcamp_week_2.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends BaseAdapter {

    private LayoutInflater inflate;
    private ArrayList<AddressItem> addresses;
    private int layout;
    public static Context context;

    String name;
    String address;
    String number;
    byte[] image;

    public CustomArrayAdapter(Context context, int layout, ArrayList<AddressItem> addresses) {
        this.inflate = LayoutInflater.from(context);
        this.layout = layout;
        this.addresses = addresses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return addresses.size();//array size
    }

    @Override
    public AddressItem getItem(int pos) {
        AddressItem item = (AddressItem) addresses.get(pos);
        return item;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        AddressItemView view = null;

        if(convertView == null) {view = new AddressItemView(context,null);}
        else { view = (AddressItemView) convertView;}

        AddressItem item = (AddressItem) addresses.get(pos);

        view.setNumber(item.getNumber());
        view.setName(item.getName());

//        Bitmap temp_bitmap = BitmapFactory.decodeByteArray(item.getImage(),0,item.getImage().length);
//        thumbnail.setImageBitmap(temp_bitmap);
        // When click item, Popup the map

        AddressClickListener addressclicklistener = new AddressClickListener(context, item.getNumber(), item.getName());
        view.setOnClickListener(addressclicklistener);

        return view;
    }

}
