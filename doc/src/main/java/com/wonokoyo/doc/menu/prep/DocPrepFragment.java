package com.wonokoyo.doc.menu.prep;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.adapter.PlanningAdapter;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.util.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DocPrepFragment extends Fragment {

    private RecyclerView rvPlanning;
    private Button btnSync;
    private TextView tvSyncDate;

    PlanningAdapter adapter;

    DocViewModel docViewModel;

    SharedPrefManager spm;

    public DocPrepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        docViewModel.resetEvent();

        adapter = new PlanningAdapter(getContext(), getActivity(), getParentFragment());

        spm = new SharedPrefManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_prep, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        docViewModel.getListDoc().observe(this, new Observer<List<Doc>>() {
            @Override
            public void onChanged(List<Doc> docList) {
                adapter.syncPlanning(docList);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPlanning = view.findViewById(R.id.rvPlanning);
        rvPlanning.setAdapter(adapter);

        tvSyncDate = view.findViewById(R.id.tvLastSync);

        docViewModel.populateListDoc().observe(getViewLifecycleOwner(), new Observer<List<Doc>>() {
            @Override
            public void onChanged(List<Doc> docs) {
                if (docs.size() > 0) {
                    docViewModel.setMutableLiveData(docs);

                    tvSyncDate.setText("* Sinkronisasi terakhir " + spm.getSpLastSync());
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = dateFormat.format(new Date());
                    spm.saveSPString(SharedPrefManager.SP_LAST_SYNC, date);

                    docViewModel.syncDocToPhone(spm.getSpIdUser());

                    tvSyncDate.setText("* Sinkronisasi terakhir " + date);
                }
            }
        });

        btnSync = view.findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("FragmentLiveDataObserve")
            @Override
            public void onClick(View view) {

            }
        });
    }
}
