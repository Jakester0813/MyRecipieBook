package com.eleven.group.myrecipiebook.activity;

/**
 * Created by siddhatapatil on 10/22/17.
 */
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.eleven.group.myrecipiebook.R;

public class Camera2Activity extends Activity {
    private static final int CAMERA_REQUEST=1888;
    ImageView imageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        imageView=(ImageView)this.findViewById(R.id.imageView1);

        Button photoButton=(Button)this.findViewById(R.id.button1);

        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST){
            Bitmap photo =(Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}

