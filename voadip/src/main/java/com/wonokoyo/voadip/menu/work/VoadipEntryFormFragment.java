package com.wonokoyo.voadip.menu.work;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.adapter.VoadipFormAdapter;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VoadipEntryFormFragment extends Fragment {

    private Button btnNext;
    private ImageView ivSjVoadip;
    private EditText etNoSj;
    private EditText etPenerima;
    private RecyclerView rvFormItem;

    VoadipViewModel voadipViewModel;

    VoadipFormAdapter adapter;

    public VoadipEntryFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        adapter = new VoadipFormAdapter(getContext(), getActivity(), voadipViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voadip_entry_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvFormItem = view.findViewById(R.id.rvFormVoadip);
        rvFormItem.setAdapter(adapter);

        File file = new File(voadipViewModel.getLiveVoadip().getValue().getUrl());
        Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();

        ivSjVoadip = view.findViewById(R.id.ivSjVoadip);
        ivSjVoadip.setImageBitmap(bitmap);

        etNoSj = view.findViewById(R.id.etNoSjVoadip);
        etNoSj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                voadipViewModel.saveNoSjToVoadip(s.toString());
            }
        });

        etPenerima = view.findViewById(R.id.etPenerima);
        etPenerima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                voadipViewModel.savePenerimaToVoadip(s.toString());
            }
        });

        btnNext = view.findViewById(R.id.btnNextVoadipToFinger);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_voadip_entry_form_to_signature);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.syncItemVoadip(voadipViewModel.getLiveVoadip().getValue().getItemVoadips());
            }
        }, 1000);
    }
}
