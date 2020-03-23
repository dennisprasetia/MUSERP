package com.wonokoyo.muserp.feature;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCaptureConfig;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wonokoyo.muserp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoFragment extends Fragment {

    private TextureView viewCamera;
    private ImageButton imgBtnRecord;
    private VideoCapture videoCap;

    private Preview preview;

    private File videoFolder;
    private String videoFilename;

    private final static int REQUEST_STORAGE_CODE = 20;

    private boolean isRecord = false;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_CODE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewCamera = view.findViewById(R.id.tvVideo);

        startCamera();

        imgBtnRecord = view.findViewById(R.id.imgBtnRecord);
        imgBtnRecord.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (!isRecord) {
                    File file = new File(videoFilename);
                    Toast.makeText(getContext(), "Start Recording", Toast.LENGTH_SHORT).show();
                    videoCap.startRecording(file, new VideoCapture.OnVideoSavedListener() {
                        @Override
                        public void onVideoSaved(File file) {
                            Toast.makeText(getContext(), "File save successfully", Toast.LENGTH_SHORT).show();

                            Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaStoreUpdate.setData(Uri.fromFile(file));
                            getActivity().sendBroadcast(mediaStoreUpdate);

                            viewResult();
                        }

                        @Override
                        public void onError(VideoCapture.UseCaseError useCaseError, String message, @Nullable Throwable cause) {

                        }
                    });
                    isRecord = true;
                } else {
                    Toast.makeText(getContext(), "Stop Recording", Toast.LENGTH_SHORT).show();
                    videoCap.stopRecording();
                    isRecord = false;
                }
            }
        });

        CameraX.bindToLifecycle(this, preview, videoCap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    createVideoFilename();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "App won't run without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    public void startCamera() {
        CameraX.unbindAll();

        Rational aspecRatio = new Rational(viewCamera.getWidth(), viewCamera.getHeight());
        Size screenSize = new Size(viewCamera.getWidth(), viewCamera.getHeight());

        PreviewConfig pConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(aspecRatio)
                .setTargetResolution(screenSize).build();

        preview = new Preview(pConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(@NonNull Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) viewCamera.getParent();
                parent.removeView(viewCamera);
                parent.addView(viewCamera, 0);

                viewCamera.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        videoCap = new VideoCapture(new VideoCaptureConfig.Builder()
                .setTargetAspectRatio(aspecRatio)
                .setTargetResolution(screenSize)
                .setAudioRecordSource(MediaRecorder.AudioEncoder.DEFAULT)
                .setBitRate(2500000)
                .setVideoFrameRate(30).build());

        createVideoFolder();
    }

    private void updateTransform() {
        Matrix mx = new Matrix();
        float w = viewCamera.getWidth();
        float h = viewCamera.getHeight();

        float cX = w / 2f;
        float cY = h / 2f;

        int rotationDgr;
        int rotation = (int) viewCamera.getRotation();

        switch(rotation){
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float) rotationDgr, cX, cY);
        viewCamera.setTransform(mx);
    }

    private void createVideoFolder() {
        File videoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        videoFolder = new File(videoFile, "MUS ERP");
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
    }

    private File createVideoFilename() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prepend = "MUS_" + time;
        File videoFile = File.createTempFile(prepend, ".mp4", videoFolder);

        videoFilename = videoFile.getAbsolutePath();
        return videoFile;
    }

    private void viewResult() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("url", videoFilename);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_video_to_video_result, bundle);
            }
        });
    }
}
