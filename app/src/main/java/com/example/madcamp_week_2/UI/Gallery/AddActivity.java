package com.example.madcamp_week_2.UI.Gallery;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.madcamp_week_2.Connection.RequestHttpConnection;
import com.example.madcamp_week_2.MainActivity;
import com.example.madcamp_week_2.R;
import com.example.madcamp_week_2.UI.Myinfo.Myinfo_image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    ImageView add_image;
    EditText add_title;
    EditText add_tag;
    Bitmap added_image, scaled_image;
    Button addimage_btn, btn_gallery, btn_camera;
    String mCurrentPhotoPath, title, tag, result, url_image, my_nickname, date, check, my_ID;
    Uri selected_image;
    int size;
    String message;
    ContentValues addcontents = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        my_nickname = ((MainActivity) MainActivity.context).my_nickname;
        my_ID = ((MainActivity) MainActivity.context).my_ID;

        add_image = findViewById(R.id.add_image);
        add_title = findViewById(R.id.add_title);
        add_tag = findViewById(R.id.add_tag);

        addimage_btn = findViewById(R.id.addimage_btn);
        btn_gallery = findViewById(R.id.select_gallery);
        btn_camera = findViewById(R.id.select_camera);

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select Picture"), 1);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoFile = null;
                try{
                    photoFile = createImageFile();
                } catch (IOException ex) {}

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                            "com.example.myapplication.fileprovider",
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, 2);
                }
            }
        });

        addimage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = add_title.getText().toString();
                tag = add_tag.getText().toString();
                scaled_image = Bitmap.createScaledBitmap(added_image, 100, 100, false);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled_image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                url_image = "http://192.249.19.244:1480/yoonseo/upload";

                addcontents.put("title",title);
                addcontents.put("tag",tag);
                addcontents.put("image",BitmapToString(added_image));
                addcontents.put("nickname",my_nickname);
                addcontents.put("ID",my_ID);

                AddActivity.NetworkTask_Image networkTask_image = new AddActivity.NetworkTask_Image(url_image, addcontents);
                networkTask_image.execute();

//                Fragment_Gallery fragment_gallery = new Fragment_Gallery();
//                getSupportFragmentManager().beginTransaction().replace(R.id.request_layout,fragment_gallery).commit();
//
//                Bundle bundle = new Bundle(5); // 파라미터는 전달할 데이터 개수
//                bundle.putString("title",title);
//                bundle.putString("tag",tag);
//                bundle.putByteArray("image",byteArray);
//                bundle.putString("Nickname",my_nickname);
//                bundle.putString("date",date);
//
//                fragment_gallery.setArguments(bundle);

                Intent intent = new Intent(AddActivity.this, RequestActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("tag",tag);
                intent.putExtra("nickname",my_nickname);
                intent.putExtra("date",date);
                intent.putExtra("image",byteArray);

                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                try {
                    if (data.getClipData() == null) {
                        selected_image = data.getData();
                    } else {
                        ClipData clipData = data.getClipData();
                        size = clipData.getItemCount();
                        if (clipData.getItemCount() > 100) {
                            return;
                        } else if (clipData.getItemCount() == 1) {
                            selected_image = clipData.getItemAt(0).getUri();
                        }
                    }

                    Bitmap bitmap_gallery;
                    Bitmap rotatedBitmap_gallery;
                    int orientation;

                    try {
                        bitmap_gallery = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), (Uri) selected_image);
                        ExifInterface ei = new ExifInterface(getRealPathFromURI(getApplicationContext(), (Uri) selected_image));
                        orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90: {
                                rotatedBitmap_gallery = rotateImage(bitmap_gallery, 90);
                                break;
                            }
                            case ExifInterface.ORIENTATION_ROTATE_180: {
                                rotatedBitmap_gallery = rotateImage(bitmap_gallery, 180);
                                break;
                            }
                            case ExifInterface.ORIENTATION_ROTATE_270: {
                                rotatedBitmap_gallery = rotateImage(bitmap_gallery, 270);
                                break;
                            }
                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap_gallery = bitmap_gallery;
                        }

                        add_image.setImageBitmap(rotatedBitmap_gallery);
                        added_image = rotatedBitmap_gallery;
                    } catch (Exception e) { e.printStackTrace(); }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap_camera;
                Bitmap rotatedBitmap_camera;
                int orientation;

                try {
                    File file = new File(mCurrentPhotoPath);
                    Uri Camera_Uri = Uri.fromFile(file);
                    bitmap_camera = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                    ExifInterface ei = new ExifInterface(getRealPathFromURI(getApplicationContext(), Camera_Uri));
                    orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90: {
                            rotatedBitmap_camera = rotateImage(bitmap_camera, 90);
                            break;
                        }
                        case ExifInterface.ORIENTATION_ROTATE_180: {
                            rotatedBitmap_camera = rotateImage(bitmap_camera, 180);
                            break;
                        }
                        case ExifInterface.ORIENTATION_ROTATE_270: {
                            rotatedBitmap_camera = rotateImage(bitmap_camera, 270);
                            break;
                        }
                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap_camera = bitmap_camera;
                    }

                    add_image.setImageBitmap(rotatedBitmap_camera);
                    added_image = rotatedBitmap_camera;
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                } else {
                    String SDcardpath = getRemovableSDCardPath(context).split("/Android")[0];
                    return SDcardpath +"/"+ split[1];
                }
            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public static String getRemovableSDCardPath(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return storages[1].toString();
        else
            return "";
    }
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }
    private class NetworkTask_Image extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask_Image (String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("add : " + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                date = jsonObject.getString("date");
                check = jsonObject.getString("result");
                if (check.equals("1")) message = "Success";
                else message = "Failed";

            }catch (Exception e) {e.printStackTrace();}
        }
    }

}