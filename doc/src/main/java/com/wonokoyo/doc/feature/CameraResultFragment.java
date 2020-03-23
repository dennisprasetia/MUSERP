package com.wonokoyo.doc.feature;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;

import java.io.File;

public class CameraResultFragment extends Fragment {

    private ImageView ivResult;
    private Button btnRetake;
    private Button btnNext;

    private String url;

    private Doc mDoc;

    public CameraResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDoc = (Doc) getArguments().getSerializable("doc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_doc_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        url = getArguments().getString("url");

        final File file = new File(url);
        Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();

        ivResult = view.findViewById(R.id.ivResultDoc);
        ivResult.setImageBitmap(bitmap);

        btnRetake = view.findViewById(R.id.btnRetakeDoc);
        btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.exists()) {
                    file.delete();

                    Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaStoreUpdate.setData(Uri.fromFile(new File(file.getAbsolutePath())));
                    getActivity().sendBroadcast(mediaStoreUpdate);

                    NavHostFragment.findNavController(getParentFragment()).navigateUp();
                }
            }
        });

        btnNext = view.findViewById(R.id.btnNextToFormDoc);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoc.setUrl(url);

                Bundle bundle = new Bundle();
                bundle.putSerializable("doc", mDoc);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_camera_doc_result_to_doc_entry_form, bundle);
            }
        });
    }
}
