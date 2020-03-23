package com.wonokoyo.voadip.menu.work;


import android.Manifest;
import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.serveraccess.sqlite.DbServiceVoadip;
import com.wonokoyo.voadip.util.CustomDialog;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class VoadipScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private final static int REQUEST_CAMERA_CODE = 10;

    private FrameLayout frameLayout;

    VoadipViewModel voadipViewModel;

    DbServiceVoadip serviceVoadip;

    int countscan;

    CustomDialog dialog;

    public VoadipScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        serviceVoadip = new DbServiceVoadip(getContext());

        countscan = 0;

        dialog = new CustomDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);

        voadipViewModel.deleteLiveDataVoadip();

        return inflater.inflate(R.layout.fragment_voadip_scan, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());

        frameLayout = view.findViewById(R.id.flScannerVoadip);

        voadipViewModel.getLiveVoadip().observe(this, new Observer<Voadip>() {
            @Override
            public void onChanged(Voadip voadip) {
                if (voadip != null) {
                    NavHostFragment.findNavController(getParentFragment())
                            .navigate(R.id.action_voadip_scan_to_voadip_confirm);
                } else {
                    countscan++;
                    if (countscan < 3) {
                        Toast.makeText(getActivity(), "No OP Tidak ditemukan",
                                Toast.LENGTH_SHORT).show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mScannerView.resumeCameraPreview(VoadipScanFragment.this);
                            }
                        }, 1500);
                    } else {
                        dialog.alertScanGagal(getContext(), new CustomDialog.alertDialogCallBack() {
                            @Override
                            public void action(Boolean val) {
                                if (val) {
                                    NavHostFragment.findNavController(getParentFragment()).navigate(
                                            R.id.action_voadip_scan_to_voadip_entry_op);
                                } else {
                                    countscan = 0;
                                    mScannerView.resumeCameraPreview(VoadipScanFragment.this);
                                }
                            }
                        });
                    }
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

        voadipViewModel.loadVoadipByOp(noOp).observe(getParentFragment(), new Observer<VoadipWithItem>() {
            @Override
            public void onChanged(VoadipWithItem voadipWithItem) {
                Voadip voadip = voadipWithItem.getVoadip();
                voadip.setItemVoadips(voadipWithItem.getVoadipItems());

                voadipViewModel.setVoadipMutableLiveData(voadip);
            }
        });

//        voadipViewModel.getVoadipByOp(noOp);

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(VoadipScanFragment.this);
            }
        }, 2000);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
