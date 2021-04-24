package com.wonokoyo.doc.menu.work;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocDetail;
import com.wonokoyo.doc.model.DocWeighs;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.serveraccess.sqlite.DbService;
import com.wonokoyo.doc.util.CustomDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DocScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private final static int REQUEST_CAMERA_CODE = 10;

    private FrameLayout frameLayout;

    private DocViewModel docViewModel;

    int countscan;

    CustomDialog dialog;

    public DocScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        countscan = 0;

        dialog = new CustomDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);

        docViewModel.deleteDocByOp();

        return inflater.inflate(R.layout.fragment_doc_scan, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        frameLayout = view.findViewById(R.id.flScanner);

        docViewModel.getLiveDoc().observe(this, new Observer<Doc>() {
            @Override
            public void onChanged(Doc doc) {
                if (doc != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = dateFormat.format(new Date());

                    doc.setKedatangan(date);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doc", doc);

                    NavHostFragment.findNavController(getParentFragment())
                            .navigate(R.id.action_doc_scan_to_doc_confirm, bundle);
                } else {
                    Toast.makeText(getActivity(), "No OP Tidak ditemukan",
                            Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mScannerView.resumeCameraPreview(DocScanFragment.this);
                        }
                    }, 1500);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                frameLayout.addView(mScannerView);
            } else {
                Toast.makeText(getContext(), "Please grant camera permission to use the Scanner", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        final String noOp = rawResult.getText();

        docViewModel.loadDocWeighsByOp(noOp).observe(this, new Observer<DocWeighs>() {
            @Override
            public void onChanged(DocWeighs docWeighs) {
                if (docWeighs != null) {
                    Doc doc = docWeighs.getDoc();
                    doc.setWeigh(docWeighs.getWeighs());

                    docViewModel.setDocMutableLiveData(doc);
                } else {
                    mScannerView.resumeCameraPreview(DocScanFragment.this);
                }
            }
        });

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(DocScanFragment.this);
            }
        }, 2000);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
