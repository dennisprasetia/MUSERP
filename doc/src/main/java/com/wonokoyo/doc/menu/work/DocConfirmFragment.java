package com.wonokoyo.doc.menu.work;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;

public class DocConfirmFragment extends Fragment {

    private Button btnNext;
    private TextView tvInfoNoOp;
    private TextView tvInfoMitra;
    private TextView tvInfoNoreg;
    private TextView tvInfoKandang;
    private TextView tvInfoAlamat;
    private TextView tvInfoJumlahBox;
    private TextView tvInfoNopol;
    private TextView tvInfoSopir;
    private TextView tvInfoKedatangan;

    public DocConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Doc doc = (Doc) getArguments().getSerializable("doc");

        tvInfoNoOp = view.findViewById(R.id.tvInfoNoOp);
        tvInfoNoOp.setText(doc.getNoOpDoc());
        tvInfoMitra = view.findViewById(R.id.tvInfoMitra);
        tvInfoMitra.setText(doc.getMitra());
        tvInfoNoreg = view.findViewById(R.id.tvInfoNoreg);
        tvInfoNoreg.setText(doc.getNoreg());
        tvInfoKandang = view.findViewById(R.id.tvInfoKandang);
        tvInfoKandang.setText(String.valueOf(doc.getKandang()));
        tvInfoAlamat = view.findViewById(R.id.tvInfoAlamat);
        tvInfoAlamat.setText(doc.getAlamat());
        tvInfoJumlahBox = view.findViewById(R.id.tvInfoJumlahBox);
        tvInfoJumlahBox.setText(String.valueOf(doc.getJumlahBox()));
        tvInfoNopol = view.findViewById(R.id.tvInfoNopol);
        tvInfoNopol.setText(doc.getNopol());
        tvInfoSopir = view.findViewById(R.id.tvInfoSopir);
        tvInfoSopir.setText(doc.getSopir());
        tvInfoKedatangan = view.findViewById(R.id.tvInfoKedatangan);
        tvInfoKedatangan.setText(doc.getKedatangan());

        btnNext = view.findViewById(R.id.btnNextCaptureSj);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("doc", doc);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_doc_confirm_to_doc_weigh, bundle);
            }
        });
    }
}
