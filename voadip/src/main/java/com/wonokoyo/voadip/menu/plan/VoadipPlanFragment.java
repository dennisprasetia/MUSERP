package com.wonokoyo.voadip.menu.plan;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.adapter.PlanningVoadipAdapter;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.util.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoadipPlanFragment extends Fragment {

    private Button btnSinkron;
    private TextView tvLastSync;
    private RecyclerView rvPlanVoadip;

    VoadipViewModel voadipViewModel;

    PlanningVoadipAdapter adapter;

    SharedPrefManager spm;

    public VoadipPlanFragment() {
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
        return inflater.inflate(R.layout.fragment_voadip_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvLastSync = view.findViewById(R.id.tvLastSync);

        adapter = new PlanningVoadipAdapter(getContext(), getActivity());

        rvPlanVoadip = view.findViewById(R.id.rvPlanVoadip);
        rvPlanVoadip.setAdapter(adapter);

        voadipViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("sync")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = dateFormat.format(new Date());
                    spm.saveSPString(SharedPrefManager.SP_LAST_SYNC, date);

                    tvLastSync.setText("* Sinkronisasi terakhir " + date);
                }

                if (s.equalsIgnoreCase("notsync")) {
                    tvLastSync.setText("* Sinkronisasi terakhir " + spm.getSpLastSync());
                }
            }
        });

        voadipViewModel.populateListVoadip().observe(getViewLifecycleOwner(), new Observer<List<VoadipWithItem>>() {
            @Override
            public void onChanged(List<VoadipWithItem> voadipWithItems) {
                if (voadipWithItems.size() > 0) {
                    List<Voadip> voadipList = setVoadipWithItem(voadipWithItems);

                    adapter.syncDetailPlanning(voadipList);
                    tvLastSync.setText("* Sinkronisasi terakhir " + spm.getSpLastSync());
                } else {
                    voadipViewModel.syncVoadipToPhone(spm.getSpIdUser());
                }
            }
        });
    }

    private List<Voadip> setVoadipWithItem(List<VoadipWithItem> withItems) {
        List<Voadip> voadips = new ArrayList<>();

        for (VoadipWithItem vwi : withItems) {
            Voadip voadip = vwi.getVoadip();
            voadip.setItemVoadips(vwi.getVoadipItems());

            voadips.add(voadip);
        }

        return voadips;
    }
}
