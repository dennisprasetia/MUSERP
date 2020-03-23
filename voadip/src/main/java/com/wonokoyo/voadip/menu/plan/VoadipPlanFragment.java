package com.wonokoyo.voadip.menu.plan;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.serveraccess.sqlite.DbServiceVoadip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VoadipPlanFragment extends Fragment {

    private Button btnSinkron;

    VoadipViewModel voadipViewModel;

    DbServiceVoadip serviceVoadip;

    public VoadipPlanFragment() {
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
        return inflater.inflate(R.layout.fragment_voadip_plan, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        voadipViewModel.getLiveListVoadip().observe(this, new Observer<List<Voadip>>() {
            @Override
            public void onChanged(List<Voadip> voadips) {
                if (voadips.size() > 0) {
                    serviceVoadip.saveVoadipToSqlite(voadips);
                }
            }
        });

        voadipViewModel.loadAllVoadip().observe(getParentFragment(), new Observer<List<VoadipWithItem>>() {
            @Override
            public void onChanged(List<VoadipWithItem> voadipWithItems) {
                for (VoadipWithItem v : voadipWithItems) {
                    System.out.println(new Gson().toJson(v.getVoadip()));
                    System.out.println(new Gson().toJson(v.getVoadipItems()));
                }
            }
        });

        btnSinkron = view.findViewById(R.id.btnSinkron);
        btnSinkron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                String date = spf.format(new Date());

//                System.out.println(date);
                voadipViewModel.populateListVoadip("2020-02-13");
            }
        });
    }
}
