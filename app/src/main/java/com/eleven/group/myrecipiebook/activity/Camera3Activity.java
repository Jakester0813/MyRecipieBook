package com.eleven.group.myrecipiebook.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.eleven.group.myrecipiebook.cameraDB.ApiService;
import com.eleven.group.myrecipiebook.cameraDB.Result;
import com.eleven.group.myrecipiebook.cameraDB.RetroClient;
import com.eleven.group.myrecipiebook.R;

/**
 * Created by siddhatapatil on 10/27/17.
 */

public class Camera3Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCamera, btnGallery;
    private ImageView imgResult;
    public static final int GALLERY = 1;
    private static final int CAMERA = 1888;
    private Uri fileUri;
    private String imagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera3);

        btnCamera = (Button)findViewById(R.id.btn_camera);
        btnGallery = (Button)findViewById(R.id.btn_gallery);
        imgResult = (ImageView)findViewById(R.id.image_result);

        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);

        requestPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        },10);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Camera3Activity.this.RESULT_CANCELED){
            return;
        }
        if (requestCode == GALLERY){
            if (data != null) {
                fileUri = data.getData();
                Toast.makeText(this, "FileUri Data : "+data.getData(), Toast.LENGTH_SHORT).show();
                try {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null);
                    if (cursor != null){
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imagePath = cursor.getString(columnIndex);
                        Log.d("Image",imagePath);
                    }
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Camera3Activity.this.getContentResolver(), fileUri);
                    imgResult.setImageBitmap(bitmap);
                    uploadImage();
                    Toast.makeText(this, ""+fileUri.getPath(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Camera3Activity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == CAMERA){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgResult.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            uploadImage();
            Toast.makeText(this, "My Recipes", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        PackageManager m = Camera3Activity.this.getPackageManager();
        String s = Camera3Activity.this.getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + s);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(Camera3Activity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            f = new File(f.getAbsolutePath());
            imagePath = f.getAbsolutePath();
            Log.d("Image2",imagePath);
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_camera:
                takePhotoCamera();
                break;
            case R.id.btn_gallery:
                choosePhotoFromGallery();
                break;
        }
    }

    private void takePhotoCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CAMERA);
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    private void uploadImage()
    {
        ApiService service = RetroClient.getApiService();
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        Call<Result> resultCall = service.uploadImage(body);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

}

