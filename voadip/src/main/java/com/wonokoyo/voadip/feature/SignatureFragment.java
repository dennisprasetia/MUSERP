package com.wonokoyo.voadip.feature;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.serveraccess.sqlite.DbServiceVoadip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignatureFragment extends Fragment {

    private SignaturePad signaturePad;
    private ImageView ivRefresh;
    private ImageView ivSend;

    private File signFolder;
    private String signFilename;

    VoadipViewModel voadipViewModel;

    DbServiceVoadip serviceVoadip;

    public SignatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        serviceVoadip = new DbServiceVoadip(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        createSignFolder();
        return inflater.inflate(R.layout.fragment_signature_voadip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signaturePad = view.findViewById(R.id.signPadVoadip);

        ivRefresh = view.findViewById(R.id.ivRefreshVoadip);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        ivSend = view.findViewById(R.id.ivSendEntryVoadip);
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                saveVoadipAndAttachment(bitmap);
            }
        });
    }

    public void saveVoadipAndAttachment(Bitmap bitmap) {
        try {
            File file = createSignFilename();
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaStoreUpdate.setData(Uri.fromFile(file));
            getActivity().sendBroadcast(mediaStoreUpdate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());

            voadipViewModel.saveUriSignToVoadip(signFilename);
            voadipViewModel.saveTglTerima(date);

            String idUser = ((VoadipActivity) getActivity()).ID_USER;

            try {
                voadipViewModel.saveVoadipAndDetail(idUser);
            } finally {
                voadipViewModel.saveVoadipAttachSj();
                voadipViewModel.saveVoadipAttachSign();
            }

            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_signature_to_voadip_result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSignFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        signFolder = new File(imageFile, "VOADIP/Signature");
        if (!signFolder.exists()) {
            signFolder.mkdirs();
        }
    }

    private File createSignFilename() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prepend = "VOADIP_SIGN_" + time;
        File imageFile = File.createTempFile(prepend, ".jpg", signFolder);

        signFilename = imageFile.getAbsolutePath();
        return imageFile;
    }
}
