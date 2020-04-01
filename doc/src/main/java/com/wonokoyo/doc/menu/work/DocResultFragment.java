package com.wonokoyo.doc.menu.work;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.util.SharedPrefManager;
import com.wonokoyo.doc.util.biometric.BiometricCallback;
import com.wonokoyo.doc.util.biometric.BiometricManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocResultFragment extends Fragment implements BiometricCallback {

    BiometricManager mBiometricManager;

    private TextView tvResultMitra;
    private TextView tvResultNoreg;
    private TextView tvResultNoSj;
    private TextView tvResultNopol;
    private TextView tvResultSopir;
    private TextView tvResultKedatangan;
    private TextView tvResultPenerimaan;
    private TextView tvResultJenisDoc;
    private TextView tvResultJumlahBox;
    private TextView tvResultMati;
    private TextView tvResultTerima;
    private TextView tvResultBbRata;
    private TextView tvResultKeterangan;
    private ImageView ivShowSign;
    private Button btnDone;

    private Doc mDoc;

    DocViewModel docViewModel;

    SharedPrefManager spm;

    Handler handler;

    public DocResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        docViewModel.resetEvent();

        if (getArguments() != null) {
            mDoc = (Doc) getArguments().getSerializable("doc");
        }

        spm = new SharedPrefManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvResultMitra = view.findViewById(R.id.tvResultMitra);
        tvResultMitra.setText(mDoc.getMitra());
        tvResultNoreg = view.findViewById(R.id.tvResultNoreg);
        tvResultNoreg.setText(mDoc.getNoreg());
        tvResultNoSj = view.findViewById(R.id.tvResultNoSj);
        tvResultNoSj.setText(mDoc.getNoSj());
        tvResultNopol = view.findViewById(R.id.tvResultNopol);
        tvResultNopol.setText(mDoc.getNopol());
        tvResultSopir = view.findViewById(R.id.tvResultSopir);
        tvResultSopir.setText(mDoc.getSopir());
        tvResultKedatangan = view.findViewById(R.id.tvResultKedatangan);
        tvResultKedatangan.setText(mDoc.getKedatangan());
        tvResultPenerimaan = view.findViewById(R.id.tvResultPenerimaan);
        tvResultPenerimaan.setText(mDoc.getPenerimaan());
        tvResultJenisDoc = view.findViewById(R.id.tvResultJenisDoc);
        tvResultJenisDoc.setText(mDoc.getJenis());
        tvResultJumlahBox = view.findViewById(R.id.tvResultJumlahBox);
        tvResultJumlahBox.setText(String.valueOf(mDoc.getJumlahBox()));
        tvResultMati = view.findViewById(R.id.tvResultMati);
        tvResultMati.setText(String.valueOf(mDoc.getMati()));
        tvResultTerima = view.findViewById(R.id.tvResultJumlahTerima);
        tvResultTerima.setText(String.valueOf(mDoc.getEkorTerima()));
        tvResultBbRata = view.findViewById(R.id.tvResultBB);
        tvResultBbRata.setText(String.valueOf(mDoc.getBbRata()));
        tvResultKeterangan = view.findViewById(R.id.tvResultKeterangan);
        tvResultKeterangan.setText(mDoc.getKeterangan());

        /*
        File file = new File(mDoc.getUrlSign());
        Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();

        ivShowSign = view.findViewById(R.id.ivShowSign);
        ivShowSign.setImageBitmap(bitmap);
        */

        docViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("saved_lokal")) {
                    Toast.makeText(getContext(), "SELESAI SIMPAN", Toast.LENGTH_SHORT);

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(getParentFragment())
                                    .navigate(R.id.action_doc_result_to_doc_home);
                        }
                    }, 1000);
                }

                if (s.equalsIgnoreCase("notfound")) {
                    Toast.makeText(getContext(), "DOC BELUM SIAP", Toast.LENGTH_SHORT);
                }
            }
        });

        btnDone = view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBiometricManager = new BiometricManager.BiometricBuilder(getContext())
                        .setTitle("Verifikasi")
                        .setSubtitle("Verifikasi Fingerprint")
                        .setDescription("Lakukan verifikasi finger untuk memulai perjalanan !")
                        .setNegativeButtonText("Batal")
                        .build();

                //start authentication
                mBiometricManager.authenticate(DocResultFragment.this);
            }
        });
    }

    // Authentication
    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getActivity().getApplicationContext(),
                "SDK Version is not supported", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Device doesn't support Biometric Authentication", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getActivity().getApplicationContext(),
                "No Fingeprint is registered in device", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Permission is not granted by User", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Failed to login", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Authentication is canceled by User", Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {
        String idUser = spm.getSpIdUser();

        // SAVE DOC
        try {
            mDoc.setStat_entry(1);

            docViewModel.savePrepDoc(mDoc);
//            docViewModel.saveDoc(mDoc, idUser);
        } finally {
            if (mDoc.getUrl() != "" || mDoc.getUrl() != null) {
                docViewModel.uploadAttachment(mDoc.getUrl());
            }
        }
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }
}
