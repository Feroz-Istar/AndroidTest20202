package com.test.androidtest20202.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.test.androidtest20202.R;
import com.test.androidtest20202.util.MediaSaver;
import com.test.androidtest20202.util.PictureUploadUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageTextRecognizer extends AppCompatActivity {


    PictureUploadUtil pictureUploadUtil;
    private String TAG="ImageTextRecognizer";
    private static final int SELECT_FILE = 101;
    private static final int REQUEST_CAMERA = 100;
    TextRecognizer recognizer;
    @BindView(R.id.result)
    TextView resulttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text_recognizer);
        ButterKnife.bind(this);
        pictureUploadUtil = new PictureUploadUtil(ImageTextRecognizer.this);
         recognizer = TextRecognition.getClient();
        resulttext.setSelected(true);
    }


    @OnClick(R.id.upload_camera)
    public void uploadCamera(){

        if (checkStoragePermission()) {
            pictureUploadUtil.selectImage();
        }

    }



    public boolean checkStoragePermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                //showToast("Press save button to update your profile picture");
                if (requestCode == SELECT_FILE) {
                    onSelectFromGalleryResult(data);

                }

                else if (requestCode == REQUEST_CAMERA) {
                    // onSelectFromGalleryResult(data);
                    onCaptureImageResult(data);

                }

            }
        } catch (OutOfMemoryError oxy) {
            oxy.printStackTrace();
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.skipMemoryCache(true);
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                InputImage image = InputImage.fromBitmap(bm, 0);
                Task<Text> result =
                        recognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        // Task completed successfully
                                        // ...




                                        resulttext.setText(visionText.getText());

                                        Log.d(TAG,visionText.getText());
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });
            } catch (Exception e) {
                //e.printStackTrace();
                Log.d(TAG,"on select from gallery issue");
            }
        }
    }


    private void onCaptureImageResult(Intent data) {
        final MediaSaver temp_profile_pic = new MediaSaver(this).
                setParentDirectoryName("profile_pic").
                setFileNameKeepOriginalExtension("temp_profile_pic.jpg").
                setExternal(MediaSaver.isExternalStorageReadable());
        InputImage image;
        try {
            image = InputImage.fromFilePath(ImageTextRecognizer.this, Uri.fromFile(temp_profile_pic.pathFile()));
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    // Task completed successfully
                                    // ...

                                    resulttext.setText(visionText.getText());

                                    Log.d(TAG,visionText.getText());
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                            // ...
                                        }
                                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}