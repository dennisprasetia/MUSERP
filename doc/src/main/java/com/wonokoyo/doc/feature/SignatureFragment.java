package com.wonokoyo.doc.feature;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;

import org.json.JSONException;

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

    private Doc mDoc;

    DocViewModel docViewModel;

    public SignatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        createSignFolder();
        return inflater.inflate(R.layout.fragment_signature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDoc = (Doc) getArguments().getSerializable("doc");

        // START GET SIGNATURE AND SEND TO SAVE
        signaturePad = view.findViewById(R.id.signPad);

        ivRefresh = view.findViewById(R.id.ivRefresh);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        ivSend = view.findViewById(R.id.ivSendEntry);
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                saveAttachment(bitmap);
            }
        });
        // END GET SIGNATURE AND SEND TO SAVE
    }

    private void saveAttachment(Bitmap bitmap) {
        try {
            File file = createSignFilename();
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaStoreUpdate.setData(Uri.fromFile(file));
            getActivity().sendBroadcast(mediaStoreUpdate);

            // SET FILE SIGNATURE URL
            mDoc.setUrlSign(signFilename);

            // SET DATE TERIMA
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            mDoc.setPenerimaan(date);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Bundle bundle = new Bundle();
            bundle.putSerializable("doc", mDoc);

            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_signature_to_doc_result, bundle);
        }
    }

    private void createSignFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        signFolder = new File(imageFile, "DOC/Signature");
        if (!signFolder.exists()) {
            signFolder.mkdirs();
        }
    }

    private File createSignFilename() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prepend = "DOC_SIGN_" + time;
        File imageFile = File.createTempFile(prepend, ".jpg", signFolder);

        signFilename = imageFile.getAbsolutePath();
        return imageFile;
    }
}
