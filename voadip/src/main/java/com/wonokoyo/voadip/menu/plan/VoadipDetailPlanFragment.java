package com.wonokoyo.voadip.menu.plan;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.adapter.VoadipItemAdapter;

public class VoadipDetailPlanFragment extends Fragment {
    private TextView detailTanggal;
    private TextView detailMitra;
    private TextView detailNoreg;
    private TextView detailNoOp;
    private TextView detailAlamat;
    private TextView detailPopulasi;
    private TextView detailSupplier;
    private RecyclerView rvDetailVoadip;

    VoadipItemAdapter adapter;

    Voadip mVoadip;

    public VoadipDetailPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mVoadip = (Voadip) getArguments().getSerializable("voadip");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voadip_detail_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setDetail(view);
    }

    private void setDetail(View view) {
        detailTanggal = view.findViewById(R.id.detailTanggal);
        detailTanggal.setText(mVoadip.getTglKirim());
        detailMitra = view.findViewById(R.id.detailMitra);
        detailMitra.setText(mVoadip.getMitra());
        detailNoreg = view.findViewById(R.id.detailNoreg);
        detailNoreg.setText(mVoadip.getNoreg());
        detailNoOp = view.findViewById(R.id.detailNoOp);
        detailNoOp.setText(mVoadip.getNoOp());
        detailAlamat = view.findViewById(R.id.detailAlamat);
        detailAlamat.setText(mVoadip.getAlamat());
        detailPopulasi = view.findViewById(R.id.detailPopulasi);
        detailPopulasi.setText(mVoadip.getPopulasi());
        detailSupplier = view.findViewById(R.id.detailSupplier);
        detailSupplier.setText(mVoadip.getSupplier());

        adapter = new VoadipItemAdapter(getContext(), getActivity());
        rvDetailVoadip = view.findViewById(R.id.rvDetailVoadip);
        rvDetailVoadip.setAdapter(adapter);

        adapter.syncItemDetailVoadip(mVoadip.getItemVoadips());
    }
}
