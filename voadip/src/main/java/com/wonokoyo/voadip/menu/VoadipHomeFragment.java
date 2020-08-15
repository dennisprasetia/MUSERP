package com.wonokoyo.voadip.menu;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class VoadipHomeFragment extends Fragment {

    private CardView cvPlan;
    private CardView cvEntry;
    private Button btnUpload;

    VoadipViewModel voadipViewModel;

    SharedPrefManager spm;

    public VoadipHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        spm = new SharedPrefManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voadip_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cvPlan = view.findViewById(R.id.cvPlanVoadip);
        cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_voadip_home_to_voadip_plan);
            }
        });

        cvEntry = view.findViewById(R.id.cvEntryVoadip);
        cvEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_voadip_home_to_voadip_scan);
            }
        });

        btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voadipViewModel.getVoadipToUpload().observe(getViewLifecycleOwner(), new Observer<List<VoadipWithItem>>() {
                    @Override
                    public void onChanged(List<VoadipWithItem> voadipWithItems) {
                        List<Voadip> voadips = new ArrayList<>();
                        for (VoadipWithItem vwi : voadipWithItems) {
                            Voadip voadip = vwi.getVoadip();
                            voadip.setItemVoadips(vwi.getVoadipItems());

                            if (voadip.getUrl() != "" && voadip.getUrl() != null) {
                                voadipViewModel.uploadVoadipSj(voadip.getUrl());
                            }

                            voadips.add(voadip);
                        }

                        voadipViewModel.uploadVoadipFromLocal(voadips, spm.getSpIdUser());
                    }
                });
            }
        });

        voadipViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("uploaded")) {
                    Toast.makeText(getContext(), "DATA SUDAH TERUPLOAD", Toast.LENGTH_SHORT).show();
                } else if (s.equalsIgnoreCase("sj_uploaded")) {
                    Toast.makeText(getContext(), "SJ VOADIP TERUPLOAD", Toast.LENGTH_SHORT).show();
                } else if (s.equalsIgnoreCase("notuploaded")) {
                    Toast.makeText(getContext(), "GAGAL UPLOAD KE SERVER", Toast.LENGTH_SHORT). show();
                } else if (s.equalsIgnoreCase("sj_notuploaded")) {
                    Toast.makeText(getContext(), "GAGAL UPLOAD SJ KE SERVER", Toast.LENGTH_SHORT). show();
                }
            }
        });
    }
}
