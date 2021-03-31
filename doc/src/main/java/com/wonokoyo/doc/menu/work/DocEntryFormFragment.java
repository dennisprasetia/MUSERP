package com.wonokoyo.doc.menu.work;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocEntryFormFragment extends Fragment {

    private Button btnNext;
    private ImageView ivSjDoc;
    private EditText etNoSjDoc;
    private EditText etJenisDoc;
    private EditText etJumlahBox;
    private EditText etMatiEkor;
    private EditText etEkorTerima;
    private EditText etBbRata;
    private EditText etBbTara;
    private EditText etKeteranganDoc;
    private Doc mDoc;
    private String tara;

    DocViewModel docViewModel;

    public DocEntryFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        if (getArguments() != null) {
            mDoc = (Doc) getArguments().getSerializable("doc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_entry_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivSjDoc = view.findViewById(R.id.ivSjDoc);

        if (mDoc.getUrl() != null) {
            File file = new File(mDoc.getUrl());
            Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();
            ivSjDoc.setImageBitmap(bitmap);
        } else {
            ivSjDoc.setImageResource(R.drawable.ic_camera_doc);
        }

        etNoSjDoc = view.findViewById(R.id.etNoSjDoc);
        etJenisDoc = view.findViewById(R.id.etJenisDoc);
        etJumlahBox = view.findViewById(R.id.etJumlahBox);
        etMatiEkor = view.findViewById(R.id.etMatiEkor);
        etEkorTerima = view.findViewById(R.id.etEkorTerima);
        etBbRata = view.findViewById(R.id.etBbRata);
        etBbTara = view.findViewById(R.id.etBbTara);
        etKeteranganDoc = view.findViewById(R.id.etKeteranganDoc);

        String bbDoc = String.format("%.0f", mDoc.getBbRata());
        etBbRata.setText(bbDoc);

        String taraBox = String.format("%.0f", mDoc.getTaraBox());
        etBbTara.setText(taraBox);

        etJumlahBox.setText(String.valueOf(mDoc.getTerimaBox()));
        etEkorTerima.setText(String.valueOf(mDoc.getEkorTerima()));

        btnNext = view.findViewById(R.id.btnNextToFinger);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoc.setNoSj(etNoSjDoc.getText().toString());
                mDoc.setJenis(etJenisDoc.getText().toString());
                mDoc.setTerimaBox(Integer.valueOf(etJumlahBox.getText().toString()));
                mDoc.setMati(Integer.valueOf(etMatiEkor.getText().toString()));
                mDoc.setEkorTerima(Integer.valueOf(etEkorTerima.getText().toString()));
                mDoc.setBbRata(Double.valueOf(etBbRata.getText().toString()));
                mDoc.setKeterangan(etKeteranganDoc.getText().toString());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dateFormat.format(new Date());
                mDoc.setPenerimaan(date);
                mDoc.setStat_recieve(1);

                docViewModel.savePrepDoc(mDoc);

                Bundle bundle = new Bundle();
                bundle.putSerializable("doc", mDoc);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_doc_entry_form_to_doc_result, bundle);
            }
        });

        ivSjDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDoc.getUrl() == null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doc", mDoc);

                    NavHostFragment.findNavController(getParentFragment())
                            .navigate(R.id.action_doc_entry_form_to_camera, bundle);
                }
            }
        });
    }
}
