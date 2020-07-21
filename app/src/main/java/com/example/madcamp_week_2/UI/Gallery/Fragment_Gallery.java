package com.example.madcamp_week_2.UI.Gallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madcamp_week_2.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Gallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Gallery extends Fragment {

    CarouselView localCarouselView;
    View view;

    // List for GridView
    ArrayList<ImageInfo> ImageInfoList;

    // List for Carousel
    int[] localImages = {R.drawable.dog1,
            R.drawable.dog2,
            R.drawable.dog3
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Gallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Gallery newInstance(String param1, String param2) {
        Fragment_Gallery fragment = new Fragment_Gallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_gallery,container,false);
        ImageInfoList = new ArrayList<>();

        for (int i=0; i<localImages.length; i++) {

            Drawable drawable = getActivity().getDrawable(localImages[i]);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();

            ImageInfo tempinfo = new ImageInfo(bitmap,"name","title","score","date");

            ImageInfoList.add(tempinfo);
        }

        GridView gridViewImages = (GridView) view.findViewById(R.id.GV_Images);
        GridViewAdapter ImageGridAdapter = new GridViewAdapter(getContext(), ImageInfoList);
        gridViewImages.setAdapter(ImageGridAdapter);

        initView();

        return view;
    }

    private void initView(){

        // drawable 이미지 롤링배너 보여주기
        localCarouselView = (CarouselView) view.findViewById(R.id.ImageCarouselView);
        localCarouselView.setPageCount(localImages.length);
        localCarouselView.setSlideInterval(2000);
        localCarouselView.setViewListener(new ViewListener() {

            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.carousel_view, null);

                ImageView fruitImageView = (ImageView) customView.findViewById(R.id.carousel_image);
                ImageInfo tempinfo = ImageInfoList.get(position);


                ImageClickListener imageViewClickListener = new ImageClickListener(getContext(),tempinfo.getImage(), tempinfo.getTitle(), tempinfo.getName(),tempinfo.getScore(),tempinfo.getDate());
                fruitImageView.setOnClickListener(imageViewClickListener);

                fruitImageView.setImageResource(localImages[position]);

                localCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

                return customView;
            }
        });
    }

}