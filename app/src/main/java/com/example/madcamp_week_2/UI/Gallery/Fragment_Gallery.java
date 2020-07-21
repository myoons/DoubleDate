package com.example.madcamp_week_2.UI.Gallery;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Message.Fragment_Message;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_Gallery extends Fragment {

    CarouselView localCarouselView;
    View view;
    // List for GridView
    ArrayList<ImageInfo> ImageInfoList;
    Button btn_add;
    ImageButton btn_reset;
    String check = "0";
    ImageInfo added_imageinfo;

    public Fragment_Gallery() {
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

        view = inflater.inflate(R.layout.fragment_gallery,container,false);

        ImageInfoList = ((MainActivity) MainActivity.context).gallery_images;
        final GridView gridViewImages = (GridView) view.findViewById(R.id.GV_Images);
        final GridViewAdapter ImageGridAdapter = new GridViewAdapter(getContext(), ImageInfoList);
        gridViewImages.setAdapter(ImageGridAdapter);

        initView();

        btn_add = (Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = "0";
                Intent intent = new Intent(getContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        btn_reset = (ImageButton) view.findViewById(R.id.gallery_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check.equals("0")) {

                    added_imageinfo = ((AddActivity) AddActivity.context).added_info;
                    ImageInfoList.add(added_imageinfo);

                    GridViewAdapter ImageGridAdapter_added = new GridViewAdapter(getContext(), ImageInfoList);
                    gridViewImages.setAdapter(ImageGridAdapter_added);
                    check = "1";
                }

            }
        });
        return view;
    }

    private void initView(){

        if (ImageInfoList.size() != 0) {
        // drawable 이미지 롤링배너 보여주기
            localCarouselView = (CarouselView) view.findViewById(R.id.ImageCarouselView);
            localCarouselView.setPageCount(ImageInfoList.size());
            localCarouselView.setSlideInterval(2000);
            localCarouselView.setViewListener(new ViewListener() {

                @Override
                public View setViewForPosition(int position) {
                    View customView = getLayoutInflater().inflate(R.layout.carousel_view, null);

                    ImageView moving_View = (ImageView) customView.findViewById(R.id.carousel_image);
                    ImageInfo tempinfo = ImageInfoList.get(position);

                    ImageClickListener imageViewClickListener = new ImageClickListener(getContext(), tempinfo.getImage(), tempinfo.getTitle(), tempinfo.getTag(), tempinfo.getDate());
                    moving_View.setOnClickListener(imageViewClickListener);

                    moving_View.setImageBitmap(tempinfo.getImage());
                    localCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

                    return customView;
                }
             });
        }
    }
}