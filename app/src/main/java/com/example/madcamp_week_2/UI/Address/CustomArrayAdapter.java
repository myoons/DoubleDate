package com.example.madcamp_week_2.UI.Address;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private CustomArrayAdapter.ViewHolder viewHolder;
    private List<String> list;

    String place_name;
    String location;
    String purpose;
    String address;
    String number;
    double rating;
    byte[] thumbnail;


    public CustomArrayAdapter(Context context, int layout, ArrayList<AddressItem> addresses, List<String> list) {
        this.inflate = LayoutInflater.from(context);
        this.layout = layout;
        this.addresses = addresses;
        this.context = context;
        this.list = list;
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

        if(convertView == null) {
            convertView = inflate.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);

        } else { viewHolder = (CustomArrayAdapter.ViewHolder)convertView.getTag();}

        viewHolder.label.setText(list.get(pos));

        AddressItem item = (AddressItem) addresses.get(pos);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.profile);
        thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(item.getImage(),0,item.getImage().length));

        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(item.getName());

        TextView address = (TextView)convertView.findViewById(R.id.address);
        address.setText(item.getAddress());

        ///////////////////////////////////////////////////////////////////////////

        /**

        ImageClickListener imageViewClickListener = new ImageClickListener(context, item.getBig_picture());
        thumbnail.setOnClickListener(imageViewClickListener);

        ImageView calling;
        calling = (ImageView) convertView.findViewById(R.id.calling);
        num = item.getPnum();
        CallClickListener callClickListener = new CallClickListener(context, num);
        calling.setOnClickListener(callClickListener);

        ImageView message;
        message = (ImageView) convertView.findViewById(R.id.message);
        MessageClickListener messageClickListener = new MessageClickListener(context, num);
        message.setOnClickListener(messageClickListener);
         **/


        return convertView;

    }

    class ViewHolder {
        public TextView label;
    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = context.getAssets().open("json file name");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return json;
    }

    private void jsonParsing(String json) {
        try {
            JSONObject jsonobject = new JSONObject(json);

            place_name = jsonobject.getString("place_name");
            location = jsonobject.getString("location");
            purpose = jsonobject.getString("purpose");
            address = jsonobject.getString("address");
            number = jsonobject.getString("number");
            rating = jsonobject.getDouble("rating");

        } catch (Exception e) {e.printStackTrace();}
    }
}
