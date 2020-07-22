package com.example.madcamp_week_2.UI.Myinfo;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Gallery.AddActivity;
import com.example.madcamp_week_2.UI.Gallery.GridViewAdapter;
import com.example.madcamp_week_2.UI.Gallery.ImageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.madcamp_week_2.MainActivity.StringToBitmap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Myinfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Myinfo extends Fragment {

    ImageView profile;
    TextView ID, nickname, number, gender, animals, bone, score;
    String result, url;
    ArrayList<Myinfo_image> img_arr = new ArrayList<>();
    ArrayList<Bitmap> bmp_arr = new ArrayList<>();
    ContentValues myinfocontents = new ContentValues();
    Bitmap bitmap;
    String sink_date, sink_score, sink_tag, sink_title, sink_nickname;

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

        final Myinfo myinfo = ((MainActivity) MainActivity.context).myinfo;
        String my_ID = ((MainActivity) MainActivity.context).my_ID;

        url = "http://192.249.19.244:1480/yoonseo/download";
        myinfocontents.put("ID", my_ID);

        System.out.println(myinfo);

        ID = view.findViewById(R.id.myinfo_ID);
        nickname = view.findViewById(R.id.myinfo_nickname);
        number = view.findViewById(R.id.myinfo_number);
        gender = view.findViewById(R.id.myinfo_gender);
        animals = view.findViewById(R.id.myinfo_animals);
        bone = view.findViewById(R.id.myinfo_bone);
        score = view.findViewById(R.id.myinfo_score);

        System.out.println("absadfsad" + myinfo.getID());
        ID.setText(myinfo.getID());
        nickname.setText(myinfo.getNickname());
        number.setText(myinfo.getNumber());
        gender.setText(myinfo.getGender());
        animals.setText(myinfo.getAnimals());
        bone.setText(myinfo.getBone());
        score.setText(myinfo.getScore());

        String ca = ((AddActivity) AddActivity.context).check_myinfo;

        if (ca.equals("1")) {
            ImageInfo added_imageinfo = ((AddActivity) AddActivity.context).added_info;
            Myinfo_image added_myimageinfo = new Myinfo_image(added_imageinfo.getImage(), added_imageinfo.getDate(), added_imageinfo.getTitle(), added_imageinfo.getTag(), "0");
            myinfo.getImages().add(added_myimageinfo);
        }


        GridView gridViewImages = (GridView) view.findViewById(R.id.myinfo_gridview);
        MyinfoGridViewAdapter ImageGridAdapter = new MyinfoGridViewAdapter(getContext(), myinfo.getImages());
        gridViewImages.setAdapter(ImageGridAdapter);

        ImageButton btn_sink = view.findViewById(R.id.myinfo_profile);
        btn_sink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Myinfo sink_myinfo = ((MainActivity) MainActivity.context).myinfo;
                GridView gridViewImages = (GridView) view.findViewById(R.id.myinfo_gridview);
                MyinfoGridViewAdapter ImageGridAdapter = new MyinfoGridViewAdapter(getContext(), myinfo.getImages());
                gridViewImages.setAdapter(ImageGridAdapter);
            }
        });

        return view;

    }
}
//    private class NetworkTask extends AsyncTask<Void, Void, String> {
//
//                private String url;
//                private ContentValues values;
//
//                public NetworkTask(String url, ContentValues values) {
//                    this.url = url;
//                    this.values = values;
//                }
//
//                @Override
//                protected String doInBackground(Void... params) {
//
//                    RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
//                    result = requestHttpConnection.request(url, values);
//
//                    return result;
//                }
//
//                @Override
//                protected void onPostExecute(String s) {
//                    super.onPostExecute(s);
//
//                    try {
//
//                        JSONObject jObject = new JSONObject(s);
//                        JSONArray image_arr = jObject.getJSONArray("yoonseo");
//
//                        for (int i = 0; i < image_arr.length(); i++) {
//                            JSONObject imageObject = (JSONObject) image_arr.get(i);
//
//                            sink_date = imageObject.getString("date").substring(0,10);
//                            sink_score = imageObject.getString("pscore");
//                            sink_tag = imageObject.getString("tag");
//                            sink_title = imageObject.getString("title");
//                            bitmap = StringToBitmap(imageObject.getString("image").replace(" ","+"));
//                            sink_nickname = imageObject.getString("nickname");
//
//                        }catch (Exception e) {e.printStackTrace();}
//                    } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//}