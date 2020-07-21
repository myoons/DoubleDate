package com.example.madcamp_week_2.UI.Message;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.madcamp_week_2.Connection.HttpGet;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Address.GpsTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener  {

    private static final String LOG_TAG = "KakaoMapActivity";
    private MapView mMapView;

    private static Double X,Y;
    private String lat_json = null;
    private String long_json = null;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    private static final String TAG = "imagesearchexample";
    public static final int LOAD_SUCCESS = 101;

    private String SEARCH_URL;
    private String REQUEST_URL;
    String encode_query;

    private ProgressDialog progressDialog;

    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_map);

        Intent receivedIntent = getIntent();
       // String number = receivedIntent.getStringExtra("number");
       // String name = receivedIntent.getStringExtra("name");
        menu = "number";

//        ImageView imageview = (ImageView) findViewById(R.id.hospital_ImageView);
//        TextView tv_name = (TextView) findViewById(R.id.hospital_name);
//        TextView tv_number = (TextView) findViewById(R.id.hospital_number);
//
//
//
//        tv_name.setText(name);
//        tv_number.setText(number);

        mMapView = (MapView) findViewById(R.id.message_map);
        //mMapView.setOpenAPIKeyAuthenticationResultListener();
        mMapView.setDaumMapApiKey("2435920dcfc3a79ef8712efebf322f1d");
        mMapView.setCurrentLocationEventListener((MapView.CurrentLocationEventListener) this);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        GpsTracker gpsTracker = new GpsTracker(MapActivity.this);
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude();


        lat_json = String.valueOf(latitude);
        long_json = String.valueOf(longitude);

        // SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json?y=" + lat_json + "&x=" + long_json + "&radius=20000&query=";
        SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json?y=37.514322572335935&x=127.06283102249932&radius=20000&query=";

        //httpGet.get("https://dapi.kakao.com/v2/local/search/keyword.")
//        Intent intent = getIntent();
//        menu = intent.getExtras().getString("menu");

        try {
            encode_query = URLEncoder.encode(menu, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        REQUEST_URL = SEARCH_URL + encode_query;

        progressDialog = new ProgressDialog( MapActivity.this );
        progressDialog.setMessage("인내는 미덕이다...");
        progressDialog.show();

        getJSON();

        mMapView.setZoomLevel(4,false);

        FloatingActionButton btn = findViewById(R.id.find_someone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "kakaomap://route?sp="+lat_json+","+long_json+"&ep="+ Y.toString() + "," + X.toString() + "&by=CAR";
                System.out.println(url);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();

        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    private final MapActivity.MyHandler mHandler = new MapActivity.MyHandler(MapActivity.this);


    private static class MyHandler extends Handler {
        private final WeakReference<MapActivity> weakReference;

        public MyHandler(MapActivity mainactivity) {
            weakReference = new WeakReference<MapActivity>(mainactivity);
        }

        @Override
        public void handleMessage(Message msg) {

            MapActivity mainactivity = weakReference.get();

            if (mainactivity != null) {
                switch (msg.what) {

                    case LOAD_SUCCESS:
                        mainactivity.progressDialog.dismiss();

                        String jsonString = (String)msg.obj;
                        JSONObject result_json = null;
                        JSONArray result_array = null;

                        try {
                            result_json = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            result_array = result_json.getJSONArray("documents");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < 1; i++) {
                            //마커 표시 코드
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName("Default Marker");
                            marker.setTag(0);

                            double x = 0;
                            double y = 0;
                            String place_name = null;
                            try {
                                x = result_array.getJSONObject(i).getDouble("x");
                                y = result_array.getJSONObject(i).getDouble("y");
                                X = x;
                                Y = y;
                                System.out.println(x + " :result: " +
                                        ""+ y);
                                place_name = result_array.getJSONObject(i).getString("place_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(y, x);

                            marker.setMapPoint(MARKER_POINT);
                            marker.setItemName(place_name);

                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

//                            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//                            marker.setCustomImageResourceId(R.drawable.marker); // 마커 이미지.
//                            marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//                            marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

                            mainactivity.mMapView.addPOIItem(marker);

                        }
                        break;
                }
            }
        }
    }

    public void  getJSON() {

        Thread thread = new Thread(new Runnable() {

            public void run() {

                String result;

                try {

                    Log.d(TAG, REQUEST_URL);
                    URL url = new URL(REQUEST_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                    httpURLConnection.setReadTimeout(3000);
                    httpURLConnection.setConnectTimeout(3000);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.addRequestProperty("Authorization", "KakaoAK 1827e1883257e82278a4eeb9c6a9fe08");
                    httpURLConnection.connect();


                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {

                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();

                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;


                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();

                    result = sb.toString().trim();


                } catch (Exception e) {
                    result = e.toString();
                }


                Message message = mHandler.obtainMessage(LOAD_SUCCESS, result);
                mHandler.sendMessage(message);
            }

        });
        thread.start();
    }

}


//package com.example.madcamp_week_2.UI.Address;

//import android.Manifest;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.madcamp_week_2.R;
//
//import net.daum.mf.map.api.MapPOIItem;
//import net.daum.mf.map.api.MapPoint;
//import net.daum.mf.map.api.MapReverseGeoCoder;
//import net.daum.mf.map.api.MapView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.ref.WeakReference;
//import java.net.URLEncoder;
//
//public class AddressActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
//
//    private static final String LOG_TAG = "KakaoMapActivity";
//    public MapView mMapView ;
//    private String lat_json = null;
//    private String long_json = null;
//    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
////    private static final String TAG = "imagesearchexample";
//    public static final int LOAD_SUCCESS = 101;
//    private String SEARCH_URL;
//    private String REQUEST_URL;
//    String encode_query;
//    private ProgressDialog progressDialog;
//    private String menu;
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.address_single);
//
//        Intent receivedIntent = getIntent();
//        ImageView imageview = (ImageView) findViewById(R.id.hospital_ImageView);
//        TextView tv_name = (TextView) findViewById(R.id.hospital_name);
//        TextView tv_number = (TextView) findViewById(R.id.hospital_number);
//
////        byte[] byteArray = receivedIntent.getByteArrayExtra("image");
//        String number = receivedIntent.getStringExtra("number");
//        String name = receivedIntent.getStringExtra("name");
//
//        // If getting image from server success
////        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
////        imageview.setImageDrawable(new BitmapDrawable(getResources(),myBitmap));
//
//        tv_name.setText(name);
//        tv_number.setText(number);
//
//        mMapView = (MapView) findViewById(R.id.map_view);
//        //mMapView.setOpenAPIKeyAuthenticationResultListener();
//        mMapView.setDaumMapApiKey("2435920dcfc3a79ef8712efebf322f1d");
//        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
//        mMapView.setCurrentLocationEventListener(this);
//
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MapView.CurrentLocationEventListener mCurrentLocationEventListener = new MapView.CurrentLocationEventListener() {
//                    @Override
//                    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
//                    }
//                    @Override
//                    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
//                    }
//                    @Override
//                    public void onCurrentLocationUpdateFailed(MapView mapView) {
//                    }
//                    @Override
//                    public void onCurrentLocationUpdateCancelled(MapView mapView) {
//                    }
//                };
//
//                System.out.println("Steps 1");
//
////                mMapView = (MapView) findViewById(R.id.map_view);
////                //mMapView.setOpenAPIKeyAuthenticationResultListener();
////                mMapView.setDaumMapApiKey("2435920dcfc3a79ef8712efebf322f1d");
////                //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
////                mMapView.setCurrentLocationEventListener(this);
//
////                MapView mapView = new MapView(AddressActivity.this);
////                ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
////                mapViewContainer.addView(mapView);
//
//
//                System.out.println("Steps 2");
//                if (!checkLocationServicesStatus()) {
//                    showDialogForLocationServiceSetting();
//                } else {
//                    checkRunTimePermission();
//                }
//
//                GpsTracker gpsTracker = new GpsTracker(AddressActivity.this);
//                double latitude = gpsTracker.getLatitude(); // 위도
//                double longitude = gpsTracker.getLongitude();
//                lat_json = String.valueOf(latitude);
//                long_json = String.valueOf(longitude);
//
//                SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json?y=" + lat_json + "&x=" + long_json + "&radius=20000&query=";
//
//                Intent intent = getIntent();
//                //menu = intent.getExtras().getString("menu");
//                menu = "중식";
//
//                System.out.println("Steps 3");
//
//                try {
//                    encode_query = URLEncoder.encode(menu, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                REQUEST_URL = SEARCH_URL + encode_query;
//
//                progressDialog = new ProgressDialog(AddressActivity.this);
//                progressDialog.setMessage("인내는 미덕이다..");
//                progressDialog.show();
//
//                //getJSON();
//
//                System.out.println("Steps 4");
//
//                mMapView.setZoomLevel(4, false);
//
//                System.out.println("Steps 5");
//            }
//
//        },0);
//
////        th.start();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
//        mMapView.setShowCurrentLocationMarker(false);
//    }
//
//    @Override
//    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
//        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
//
//        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
//    }
//
//    @Override
//    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
//    }
//
//    @Override
//    public void onCurrentLocationUpdateFailed(MapView mapView) {
//    }
//
//    @Override
//    public void onCurrentLocationUpdateCancelled(MapView mapView) {
//    }
//
//    @Override
//    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
//        mapReverseGeoCoder.toString();
//        onFinishReverseGeoCoding(s);
//    }
//
//    @Override
//    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
//        onFinishReverseGeoCoding("Fail");
//    }
//
//    private void onFinishReverseGeoCoding(String result) {
////        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
//    }
//
//    /*
//     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
//     */
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grandResults) {
//
//        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//
//            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
//
//            boolean check_result = true;
//
//
//            // 모든 퍼미션을 허용했는지 체크합니다.
//
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//
//            if ( check_result ) {
//                Log.d("@@@", "start");
//                //위치 값을 가져올 수 있음
//                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
//            }
//            else {
//                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
//
//                    Toast.makeText(AddressActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
//                    finish();
//
//                }else {
//
//                    Toast.makeText(AddressActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        }
//    }
//
//    void checkRunTimePermission(){
//
//        //런타임 퍼미션 처리
//        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(AddressActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
//
//            // 2. 이미 퍼미션을 가지고 있다면
//            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
//
//
//            // 3.  위치 값을 가져올 수 있음
//            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
//
//
//        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
//
//            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
//            if (ActivityCompat.shouldShowRequestPermissionRationale(AddressActivity.this, REQUIRED_PERMISSIONS[0])) {
//
//                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
//                Toast.makeText(AddressActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
//                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
//                ActivityCompat.requestPermissions(AddressActivity.this, REQUIRED_PERMISSIONS,
//                        PERMISSIONS_REQUEST_CODE);
//
//
//            } else {
//                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
//                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
//                ActivityCompat.requestPermissions(AddressActivity.this, REQUIRED_PERMISSIONS,
//                        PERMISSIONS_REQUEST_CODE);
//            }
//
//        }
//
//    }
//
//    //여기부터는 GPS 활성화를 위한 메소드들
//    private void showDialogForLocationServiceSetting() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
//        builder.setTitle("위치 서비스 비활성화");
//        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
//                + "위치 설정을 수정하실래요?");
//        builder.setCancelable(true);
//        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Intent callGPSSettingIntent
//                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        builder.create().show();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//
//            case GPS_ENABLE_REQUEST_CODE:
//
//                //사용자가 GPS 활성 시켰는지 검사
//                if (checkLocationServicesStatus()) {
//                    if (checkLocationServicesStatus()) {
//
//                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
//                        checkRunTimePermission();
//                        return;
//                    }
//                }
//
//                break;
//        }
//    }
//
//    public boolean checkLocationServicesStatus() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//
//
//    private final AddressActivity.MyHandler mHandler = new AddressActivity.MyHandler(this);
//
//
//    private static class MyHandler extends Handler {
//        private final WeakReference<AddressActivity> weakReference;
//
//        public MyHandler(AddressActivity mainactivity) {
//            weakReference = new WeakReference<AddressActivity>(mainactivity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//
//            AddressActivity mainactivity = weakReference.get();
//
//            if (mainactivity != null) {
//                switch (msg.what) {
//
//                    case LOAD_SUCCESS:
//                        mainactivity.progressDialog.dismiss();
//
//                        String jsonString = (String)msg.obj;
//                        JSONObject result_json = null;
//                        JSONArray result_array = null;
//
//                        try {
//                            result_json = new JSONObject(jsonString);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            result_array = result_json.getJSONArray("documents");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        for (int i = 0; i < result_array.length(); i++) {
//                            //마커 표시 코드
//                            MapPOIItem marker = new MapPOIItem();
//                            marker.setItemName("Default Marker");
//                            marker.setTag(0);
//
//                            double x = 0;
//                            double y = 0;
//                            String place_name = null;
//                            try {
//                                x = result_array.getJSONObject(i).getDouble("x");
//                                y = result_array.getJSONObject(i).getDouble("y");
//                                place_name = result_array.getJSONObject(i).getString("place_name");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(y, x);
//
//                            marker.setMapPoint(MARKER_POINT);
//                            marker.setItemName(place_name);
//
//                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//                            mainactivity.mMapView.addPOIItem(marker);
//
//                        }
//                        break;
//                }
//            }
//        }
//    }
//
//
//
//}