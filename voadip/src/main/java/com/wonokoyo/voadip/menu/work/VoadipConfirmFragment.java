package com.wonokoyo.voadip.menu.work;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.adapter.VoadipItemAdapter;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;

public class VoadipConfirmFragment extends Fragment {

    private TextView txtMitra;
    private TextView txtNoreg;
    private TextView txtAlamat;
    private TextView txtNoOp;
    private TextView txtSupplier;
    private TextView txtTglKirim;
    private Button btnNext;
    private RecyclerView rvItemVoadip;

    VoadipItemAdapter adapter;

    VoadipViewModel voadipViewModel;

    public VoadipConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new VoadipItemAdapter(getContext(), getActivity());

        return inflater.inflate(R.layout.fragment_voadip_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Voadip voadip = voadipViewModel.getLiveVoadip().getValue();

        adapter.syncItemDetailVoadip(voadip.getItemVoadips());

        txtMitra = view.findViewById(R.id.tvMitra);
        txtMitra.setText(voadip.getMitra());
        txtNoreg = view.findViewById(R.id.tvNoreg);
        txtNoreg.setText(voadip.getNoreg());
        txtAlamat = view.findViewById(R.id.tvAlamat);
        txtAlamat.setText(voadip.getAlamat());
        txtNoOp = view.findViewById(R.id.tvNoOp);
        txtNoOp.setText(voadip.getNoOp());
        txtSupplier = view.findViewById(R.id.tvSupplier);
        txtSupplier.setText(voadip.getSupplier());
        txtTglKirim = view.findViewById(R.id.tvTglKirim);
        txtTglKirim.setText(voadip.getTglKirim());

        rvItemVoadip = view.findViewById(R.id.rvItemVoadip);
        rvItemVoadip.setAdapter(adapter);

        btnNext = view.findViewById(R.id.btnNextCaptureSj);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_voadip_confirm_to_camera_voadip);
            }
        });
    }
}
