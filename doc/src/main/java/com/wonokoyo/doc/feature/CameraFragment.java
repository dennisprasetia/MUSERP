package com.wonokoyo.doc.feature;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraFragment extends Fragment {

    private TextureView viewCamera;
    private ImageButton imgBtnCapture;
    private ImageCapture imgCap;

    private Preview preview;

    private File imageFolder;
    private String imageFilename;

    private final static int REQUEST_STORAGE_CODE = 20;

    private Doc mDoc = null;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mDoc = (Doc) getArguments().getSerializable("doc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_CODE);
        return inflater.inflate(R.layout.fragment_camera_doc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewCamera = view.findViewById(R.id.viewCamera);

        startCamera();

        imgBtnCapture = view.findViewById(R.id.imgBtnCapture);
        imgBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(imageFilename);
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                        @Override
                        public void onImageSaved(@NonNull File file) {
                            String msg = "Pic captured at " + file.getAbsolutePath();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

                            Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaStoreUpdate.setData(Uri.fromFile(new File(imageFilename)));
                            getActivity().sendBroadcast(mediaStoreUpdate);

                            viewResult();
                        }

                        @Override
                        public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message,
                                            @Nullable Throwable cause) {
                            String msg = "Pic capture failed : " + message;
                            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                            if (cause != null) {
                                cause.printStackTrace();
                            }
                        }
                    });
                } else {

                }
            }
        });

        CameraX.bindToLifecycle(this, preview, imgCap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    createImageFilename();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Aplikasi tidak bisa jalan tanpa perizinan", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation()).build();
        imgCap = new ImageCapture(imageCaptureConfig);

        createImageFolder();
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

    private void createImageFolder() {
        File imageFile = Environment.getDataDirectory();
        imageFolder = new File(getContext().getFilesDir(), "DOC/SJ_DOC");
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
    }

    private File createImageFilename() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prepend = "SJ_DOCIN_" + time;
        File imageFile = File.createTempFile(prepend, ".jpg", imageFolder);

        imageFilename = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void viewResult() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("url", imageFilename);
                bundle.putSerializable("doc", mDoc);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_camera_doc_to_camera_doc_result, bundle);
            }
        }, 500);
    }
}
