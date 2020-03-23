package com.wonokoyo.muserp.feature;


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

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.AttachmentViewModel;
import com.wonokoyo.muserp.util.ImageUtil;

import java.io.File;

public class CameraResultFragment extends Fragment {

    private ImageView ivResult;
    private Button btnRetake;
    private Button btnNext;

    private String url;

    AttachmentViewModel attachmentViewModel;

    public CameraResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attachmentViewModel = ((MainActivity) getActivity()).getAttachmentViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        url = getArguments().getString("url");

        final File file = new File(url);
        Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();

        ivResult = view.findViewById(R.id.ivResult);
        ivResult.setImageBitmap(ImageUtil.getBestImageOrientation(bitmap, url));

        btnRetake = view.findViewById(R.id.btnRetake);
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

        btnNext = view.findViewById(R.id.btnNextToForm);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                if (attachmentViewModel.getListAttachment().getValue() != null) {
                    index = attachmentViewModel.getListAttachment().getValue().size() + 1;
                }
                attachmentViewModel.populateAttachment(index, "image", url);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_camera_result_to_daily_entry_attachment);
            }
        });
    }
}
