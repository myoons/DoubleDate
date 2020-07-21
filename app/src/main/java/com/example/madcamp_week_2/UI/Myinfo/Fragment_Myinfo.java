package com.example.madcamp_week_2.UI.Myinfo;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Gallery.GridViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Myinfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Myinfo extends Fragment {

    ImageView profile;
    TextView ID, nickname, number, gender, animals, bone, score;

    ArrayList<Myinfo_image> img_arr = new ArrayList<>();
    ArrayList<Bitmap> bmp_arr = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Myinfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Myinfo.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Myinfo newInstance(String param1, String param2) {
        Fragment_Myinfo fragment = new Fragment_Myinfo();
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
        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);

        Myinfo myinfo = ((MainActivity) MainActivity.context).myinfo;

        System.out.println(myinfo);

        ID = view.findViewById(R.id.myinfo_ID);
        nickname = view.findViewById(R.id.myinfo_nickname);
        number = view.findViewById(R.id.myinfo_number);
        gender = view.findViewById(R.id.myinfo_gender);
        animals = view.findViewById(R.id.myinfo_animals);
        bone = view.findViewById(R.id.myinfo_bone);
        score = view.findViewById(R.id.myinfo_score);

        System.out.println("absadfsad"+myinfo.getID());
        ID.setText(myinfo.getID());
        nickname.setText(myinfo.getNickname());
        number.setText(myinfo.getNumber());
        gender.setText(myinfo.getGender());
        animals.setText(myinfo.getAnimals());
        bone.setText(myinfo.getBone());
        score.setText(myinfo.getScore());

        GridView gridViewImages = (GridView) view.findViewById(R.id.myinfo_gridview);
        MyinfoGridViewAdapter ImageGridAdapter = new MyinfoGridViewAdapter(getContext(), myinfo.getImages());
        gridViewImages.setAdapter(ImageGridAdapter);

        return view;

    }
}