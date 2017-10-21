package com.eleven.group.myrecipiebook.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;

import java.io.File;
import java.util.List;

/**
 * Created by siddhatapatil on 10/20/17.
 */

@SuppressLint("NewApi")
public class CameraMenuActivity extends Activity {
	private int parentId = 3;
	private Button btnVideoBrowse;
	private Button btnImgBrowse;
	private Button btnSetting;
	
	private List<Size> supportedPictureSizes = null;
	private List<Size> supportedVideoSizes = null;

	private Integer videoWidth;
	private Integer videoHeight;

	private Integer pictureWidth;
	private Integer pictureHeight;
	
	static SharedPreferences settings;
	static SharedPreferences.Editor editor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		//btnImgBrowse = (Button) findViewById(R.id.arc_hf_img_btnGridShow);
		//btnImgBrowse.setOnClickListener(new btnListener());

		//Button btnVideo = (Button) findViewById(R.id.btnVideo);
		//btnVideo.setOnClickListener(new btnListener());
		
		//Button btnImg = (Button) findViewById(R.id.btnImg);
		//btnImg.setOnClickListener(new btnListener());

		//btnVideoBrowse = (Button) findViewById(R.id.arc_hf_video_btnVideoBrowse);
		//btnVideoBrowse.setOnClickListener(new btnListener());
		//showVideoCount();
		//showImgCount();

		//btnSetting = (Button) findViewById(R.id.arc_hf_btnSetting);
		//btnSetting.setOnClickListener(new btnListener());
		
    	//int modeWorldWriteable = MODE_PRIVATE;
		//settings = this.getPreferences(modeWorldWriteable);

		//supportedPictureSizes = new ArrayList<Camera.Size>();
		//supportedPictureSizes.add(Camera.Size)
		//supportedVideoSizes = new ArrayList<Camera.Size>();
		/*
		if (supportedPictureSizes == null) {
			Camera camera = Camera.open();
			Camera.Parameters parameters = camera.getParameters();
			supportedPictureSizes = parameters.getSupportedPictureSizes();
			supportedVideoSizes = parameters.getSupportedVideoSizes();
			pictureWidth = settings.getInt("pictureWidth", Util.getMaxSize(supportedPictureSizes).width);
			pictureHeight = settings.getInt("pictureHeight", Util.getMaxSize(supportedPictureSizes).height);
			videoWidth = settings.getInt("videoWidth", Util.getMaxSize(supportedVideoSizes).width);
			videoHeight = settings.getInt("videoHeight", Util.getMaxSize(supportedVideoSizes).height);
			camera.release();
			camera = null;
		}*/
	}

	class btnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageIcon:
				imgShow();
				break;
			case R.id.btn_video_start:
				startRecorder();
				break;
				
			case R.id.btnImg:
				startPhoto();
				break;
				
			case R.id.btnVideo:
				videoShow();
				break;
			case R.id.btnSetting:
				startSetting();
				break;
			default:
				break;
			}
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			showImgCount();
			break;
		case 2:
			showImgCount();
			break;
		case 3:
			showVideoCount();
			break;
		case 4:
			showImgCount();
			break;
		case 5:
			showVideoCount();
			break;
		case 6:
			if (resultCode == RESULT_OK) {
				pictureWidth = data.getIntExtra("pictureWidth", -1);
				pictureHeight = data.getIntExtra("pictureHeight", -1);
				videoWidth = data.getIntExtra("videoWidth", -1);
				videoHeight = data.getIntExtra("videoHeight", -1);
				
				if (pictureWidth<0 || pictureHeight<0 || videoWidth<0 || videoHeight<0) {
					showMsg("Invalid Size: pictureSize " + pictureWidth + "x" + pictureHeight + ", videoSize " + videoWidth + "x" + videoHeight);
					return ;
				}
				
				editor = settings.edit();
				editor.putInt("pictureWidth", pictureWidth);
				editor.putInt("pictureHeight", pictureHeight);
				editor.putInt("videoWidth", videoWidth);
				editor.putInt("videoHeight", videoHeight);
				editor.apply();
				editor.commit();
			}
			break;
		default:
			break;
		}
	}

	private Toast toast;
	private String videoPath;
	private String imgPath;

	public void showMsg(String arg) {
		if (toast == null) {
			toast = Toast.makeText(this, arg, Toast.LENGTH_SHORT);
		} else {
			toast.cancel();
			toast.setText(arg);
		}
		toast.show();
	}

	public void imgShow() {
		Intent intent = new Intent();
		intent.putExtra("path", imgPath);
		intent.setClass(this, MyRecipeFilesActivity.class);
		startActivityForResult(intent, 2);
	}

	private void showImgCount() {
		imgPath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/FACameraDemo/img/" + String.valueOf(parentId) + "/";
		File file = new File(imgPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		int fileCount = files.length;
		if (fileCount == 0) {
			btnImgBrowse.setEnabled(false);
		} else {
			btnImgBrowse.setEnabled(true);
		}
		btnImgBrowse.setText("Photos(" + fileCount + ")");
	}

	public void startRecorder() {
		Intent intent = new Intent();
		intent.setClass(CameraMenuActivity.this, CameraActivity.class);
		intent.putExtra("parentId", parentId);
		intent.putExtra("mode", "video");
		intent.putExtra("pictureWidth", pictureWidth);
		intent.putExtra("pictureHeight", pictureHeight);
		intent.putExtra("videoWidth", videoWidth);
		intent.putExtra("videoHeight", videoHeight);
		startActivityForResult(intent, 3);
	}
	
	public void startPhoto() {
		Intent intent = new Intent();
		intent.setClass(CameraMenuActivity.this, CameraActivity.class);
		intent.putExtra("parentId", parentId);
		intent.putExtra("mode", "img");
		intent.putExtra("pictureWidth", pictureWidth);
		intent.putExtra("pictureHeight", pictureHeight);
		intent.putExtra("videoWidth", videoWidth);
		intent.putExtra("videoHeight", videoHeight);
		startActivityForResult(intent, 4);
	}

	public void videoShow() {
		Intent intent = new Intent();
		intent.putExtra("path", videoPath);
		intent.setClass(CameraMenuActivity.this, MyRecipeFilesActivity.class);
		startActivityForResult(intent, 5);
	}
	
	public void startSetting() {
		Intent intent = new Intent();
		intent.setClass(CameraMenuActivity.this, SettingActivity.class);
		intent.putExtra("pictureWidth", pictureWidth);
		intent.putExtra("pictureHeight", pictureHeight);
		intent.putExtra("videoWidth", videoWidth);
		intent.putExtra("videoHeight", videoHeight);
		startActivityForResult(intent, 6);
	}

	public void showVideoCount() {
		videoPath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/FACameraDemo/video/" + String.valueOf(parentId) + "/";
		File file = new File(videoPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		int fileCount = files.length;
		if (fileCount == 0) {
			btnVideoBrowse.setEnabled(false);
		} else {
			btnVideoBrowse.setEnabled(true);
		}
		btnVideoBrowse.setText("Videos(" + fileCount + ")");
	}
}
