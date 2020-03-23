package com.wonokoyo.muserp.menu.daily.entry;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Attachment;
import com.wonokoyo.muserp.menu.daily.model.adapter.AttachmentAdapter;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.AttachmentViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;

import java.util.List;

public class DailyEntryAttachmentFragment extends Fragment {

    private ImageView ivCamera;
    private ImageView ivSend;
    private RecyclerView rvAttachment;

    AttachmentViewModel attachmentViewModel;

    AttachmentAdapter attachmentAdapter;

    EntryViewModel entryViewModel;

    public DailyEntryAttachmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attachmentViewModel = ((MainActivity) getActivity()).getAttachmentViewModel();
        attachmentViewModel.init();

        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();

        attachmentAdapter = new AttachmentAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_entry_attachment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        attachmentViewModel.getListAttachment().observe(this, new Observer<List<Attachment>>() {
            @Override
            public void onChanged(List<Attachment> attachments) {
                attachmentAdapter.addNewImage(attachments);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivCamera = view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[] {Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO}, 200);
            }
        });

        ivSend = view.findViewById(R.id.ivSendAttachment);
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryViewModel.setmListAttachment(attachmentViewModel.getListAttachment().getValue());

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_daily_entry_attachment_to_solution_form);
            }
        });

        rvAttachment = view.findViewById(R.id.rvAttachment);
        rvAttachment.setAdapter(attachmentAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String[] action = {"Image", "Video"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Pick an Action");
                builder.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            NavHostFragment.findNavController(getParentFragment())
                                    .navigate(R.id.action_daily_entry_attachment_to_camera);
                        } else {
                            NavHostFragment.findNavController(getParentFragment())
                                    .navigate(R.id.action_daily_entry_attachment_to_video);
                        }
                    }
                });
                builder.show();
            }
        }
    }
}
