package com.test.androidtest20202.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.test.androidtest20202.R;
import com.test.androidtest20202.util.MediaSaver;
import com.test.androidtest20202.util.PictureUploadUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaceDetectionActivity extends AppCompatActivity {
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private String TAG="FaceDetectionActivity";

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }
    FaceDetector detector;
    PictureUploadUtil pictureUploadUtil;
    private static final int SELECT_FILE = 101;
    private static final int REQUEST_CAMERA = 100;

    @BindView(R.id.head_rotate)
    TextView head_rotate;

    @BindView(R.id.head_tilt)
    TextView head_tilt;

    @BindView(R.id.left_ear)
    TextView left_ear;

    @BindView(R.id.smile)
    TextView smile;

    @BindView(R.id.leftEyeContourText)
    TextView leftEyeContourText;

    @BindView(R.id.right_eye)
    TextView right_eye;


    @BindView(R.id.upperLipBottomContourText)
    TextView upperLipBottomContourText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);
        ButterKnife.bind(this);
        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();

// Real-time contour detection
        FaceDetectorOptions realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .build();


        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setClassificationMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)

                        .setMinFaceSize(0.15f)
                        .enableTracking()
                        .build();
        detector = FaceDetection.getClient(options);
        pictureUploadUtil = new PictureUploadUtil(FaceDetectionActivity.this);

    }


    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
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
                Task<List<Face>> result =
                        detector.process(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<List<Face>>() {
                                            @Override
                                            public void onSuccess(List<Face> faces) {
                                                // Task completed successfully
                                                // ...

                                                for (Face face : faces) {
                                                    Rect bounds = face.getBoundingBox();
                                                    float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
                                                    float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees
                                                    head_rotate.setText(rotY+"");
                                                    head_tilt.setText(rotZ+"");
                                                    // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                                    // nose available):
                                                    FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
                                                    if (leftEar != null) {
                                                        PointF leftEarPos = leftEar.getPosition();
                                                        left_ear.setText(leftEarPos.x+" "+leftEarPos.y);
                                                    }

                                                    // If contour detection was enabled:
                                                    List<PointF> leftEyeContour =
                                                            face.getContour(FaceContour.LEFT_EYE).getPoints();
                                                    List<PointF> upperLipBottomContour =
                                                            face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints();

                                                    StringBuffer sb = new StringBuffer();
                                                    for (PointF pointF: leftEyeContour){
                                                        sb.append(pointF.x+" "+pointF.y);
                                                    }

                                                    StringBuffer sb1 = new StringBuffer();
                                                    for (PointF pointF: upperLipBottomContour){
                                                        sb1.append(pointF.x+" "+pointF.y);
                                                    }
                                                    leftEyeContourText.setText(sb.toString());
                                                    upperLipBottomContourText.setText(sb1.toString());
                                                    // If classification was enabled:
                                                    if (face.getSmilingProbability() != null) {
                                                        float smileProb = face.getSmilingProbability();
                                                        smile.setText(smileProb+"");
                                                    }
                                                    if (face.getRightEyeOpenProbability() != null) {
                                                        float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                                        right_eye.setText(rightEyeOpenProb+"");
                                                    }

                                                    // If face tracking was enabled:
                                                    if (face.getTrackingId() != null) {
                                                        int id = face.getTrackingId();
                                                    }
                                                }
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
            image = InputImage.fromFilePath(FaceDetectionActivity.this, Uri.fromFile(temp_profile_pic.pathFile()));
            Task<List<Face>> result =
                    detector.process(image)
                            .addOnSuccessListener(
                                    new OnSuccessListener<List<Face>>() {
                                        @Override
                                        public void onSuccess(List<Face> faces) {
                                            // Task completed successfully
                                            // ...

                                            for (Face face : faces) {
                                                Rect bounds = face.getBoundingBox();
                                                float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
                                                float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees
                                                head_rotate.setText(rotY+"");
                                                head_tilt.setText(rotZ+"");
                                                // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                                // nose available):
                                                FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
                                                if (leftEar != null) {
                                                    PointF leftEarPos = leftEar.getPosition();
                                                    left_ear.setText(leftEarPos.x+" "+leftEarPos.y);
                                                }

                                                // If contour detection was enabled:
                                                List<PointF> leftEyeContour =
                                                        face.getContour(FaceContour.LEFT_EYE).getPoints();
                                                List<PointF> upperLipBottomContour =
                                                        face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints();

                                                StringBuffer sb = new StringBuffer();
                                                for (PointF pointF: leftEyeContour){
                                                    sb.append(pointF.x+" "+pointF.y);
                                                }

                                                StringBuffer sb1 = new StringBuffer();
                                                for (PointF pointF: upperLipBottomContour){
                                                    sb1.append(pointF.x+" "+pointF.y);
                                                }
                                                leftEyeContourText.setText(sb.toString());
                                                upperLipBottomContourText.setText(sb1.toString());
                                                // If classification was enabled:
                                                if (face.getSmilingProbability() != null) {
                                                    float smileProb = face.getSmilingProbability();
                                                    smile.setText(smileProb+"");
                                                }
                                                if (face.getRightEyeOpenProbability() != null) {
                                                    float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                                    right_eye.setText(rightEyeOpenProb+"");
                                                }

                                                // If face tracking was enabled:
                                                if (face.getTrackingId() != null) {
                                                    int id = face.getTrackingId();
                                                }
                                            }
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