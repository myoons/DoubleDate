package com.example.madcamp_week_2.UI.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.madcamp_week_2.R;

import java.util.ArrayList;

public class MessageReceiveAdapter extends BaseAdapter {

    private LayoutInflater inflate;
    private ArrayList<ReceiveItem> messages;
    private int layout;
    public static Context context;

    String nickname;
    String message;

    public MessageReceiveAdapter(Context context, int layout, ArrayList<ReceiveItem> messages) {
        this.inflate = LayoutInflater.from(context);
        this.layout = layout;
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();//array size
    }

    @Override
    public ReceiveItem getItem(int pos) {
        ReceiveItem item = (ReceiveItem) messages.get(pos);
        return item;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {

        ReceiveItemview view = null;

        if(convertView == null) {view = new ReceiveItemview(context,null);}
        else { view = (ReceiveItemview) convertView;}

        ReceiveItem item = (ReceiveItem) messages.get(pos);

        view.setName(item.getNickname());
        view.setMessage(item.getMessage());

        ReceiveClickListener receiveClickListener = new ReceiveClickListener(context, item);
        view.setOnClickListener(receiveClickListener);

        return view;
    }

}
