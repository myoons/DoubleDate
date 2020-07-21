package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Address.AddressClickListener;
import com.example.madcamp_week_2.UI.Address.AddressItem;
import com.example.madcamp_week_2.UI.Address.AddressItemView;

import java.util.ArrayList;

public class MessageSendAdapter extends BaseAdapter {

    private LayoutInflater inflate;
    private ArrayList<String> nicknames;
    private int layout;
    public static Context context;

    String nickname;

    public MessageSendAdapter(Context context, int layout, ArrayList<String> nicknames) {
        this.inflate = LayoutInflater.from(context);
        this.layout = layout;
        this.nicknames = nicknames;
        this.context = context;
    }

    @Override
    public int getCount() {
        return nicknames.size();//array size
    }

    @Override
    public String getItem(int pos) {
        String item = (String) nicknames.get(pos);
        return item;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {

        SendItemView view = null;

        if(convertView == null) {view = new SendItemView(context,null);}
        else { view = (SendItemView) convertView;}

        String item = (String) nicknames.get(pos);

        view.setName(item);

        double dValue = Math.random();
        if (dValue>0.5) view.setImage(R.drawable.send_girl);
        else view.setImage(R.drawable.send_man);

        SendClickListener sendClickListener = new SendClickListener(context, item);
        view.setOnClickListener(sendClickListener);

        return view;
    }

}
