package com.wonokoyo.muserp.feature;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.AttachmentViewModel;

import java.io.File;

public class VideoResultFragment extends Fragment {

    private VideoView vvResult;
    private ImageView ivPlayResult;
    private Button btnRetake;
    private Button btnNext;
    String uri;

    AttachmentViewModel attachmentViewModel;

    public VideoResultFragment() {
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
        return inflater.inflate(R.layout.fragment_video_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vvResult = view.findViewById(R.id.vvResult);
        vvResult.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlayResult.setVisibility(View.VISIBLE);
                btnRetake.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        if (getArguments() != null) {
            uri = getArguments().getString("url");
            vvResult.setVideoURI(Uri.parse(uri));
        }

        btnRetake = view.findViewById(R.id.btnRetakeVideo);
        btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(uri);
                if (file.exists())
                    file.delete();

                Intent mediaStoreUpdate = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaStoreUpdate.setData(Uri.fromFile(file));
                getActivity().sendBroadcast(mediaStoreUpdate);

                NavHostFragment.findNavController(getParentFragment()).navigateUp();
            }
        });

        btnNext = view.findViewById(R.id.btnNextResultVideo);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                if (attachmentViewModel.getListAttachment().getValue() != null) {
                    index = attachmentViewModel.getListAttachment().getValue().size() + 1;
                }
                attachmentViewModel.populateAttachment(index, "video", uri);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_video_result_to_daily_entry_attachment);
            }
        });

        ivPlayResult = view.findViewById(R.id.ivPlayResult);
        ivPlayResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vvResult.start();
                ivPlayResult.setVisibility(View.INVISIBLE);
                btnRetake.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
        });


    }
}
