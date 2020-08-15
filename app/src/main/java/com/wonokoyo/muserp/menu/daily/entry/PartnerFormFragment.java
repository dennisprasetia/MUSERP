package com.wonokoyo.muserp.menu.daily.entry;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Doc;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.PartnerViewModel;

import java.util.ArrayList;
import java.util.List;

public class PartnerFormFragment extends Fragment {

    private Spinner spMitra;
    private Spinner spNoreg;
    private EditText etKandang;
    private EditText etPopulasi;
    private EditText etUmur;
    private ImageButton btnNextPartner;

    PartnerViewModel partnerViewModel;

    EntryViewModel entryViewModel;

    public PartnerFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        partnerViewModel = ((MainActivity) getActivity()).getPartnerViewModel();
        partnerViewModel.init();

        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();

        partnerViewModel.populateDocList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partner_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        spMitra = view.findViewById(R.id.spMitra);
        spNoreg = view.findViewById(R.id.spNoreg);
        etKandang = view.findViewById(R.id.etKandang);
        etPopulasi = view.findViewById(R.id.etPopulasi);
        etUmur = view.findViewById(R.id.etUmur);

        partnerViewModel.getDocMitraList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapterMitra = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                        strings);
                adapterMitra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMitra.setAdapter(adapterMitra);
            }
        });

        spMitra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String nama = spMitra.getItemAtPosition(position).toString();

                etKandang.setText("");
                etPopulasi.setText("");
                etUmur.setText("");

                ArrayAdapter<String> adapterNoreg = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                        partnerViewModel.populateStringNoreg(nama));
                adapterNoreg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spNoreg.setAdapter(adapterNoreg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spNoreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String noreg = spNoreg.getItemAtPosition(position).toString();

                Doc doc = partnerViewModel.getDocByNoreg(noreg);
                if (doc != null) {
                    etKandang.setText(doc.getNoreg().substring(8));
                    etPopulasi.setText(String.valueOf(doc.getPopulasi()));
                    etUmur.setText(String.valueOf(doc.getUmur()));

                    entryViewModel.setmDoc(doc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNextPartner = view.findViewById(R.id.btnNextPartner);
        btnNextPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(view))
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_partner_form_to_screen_form);
            }
        });
    }

    public boolean validate(View view) {
        if (spMitra.getSelectedItemPosition() == 0) {
            Snackbar.make(view, "Please Choose Mitra", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (spNoreg.getSelectedItemPosition() == 0) {
            Snackbar.make(view, "Please Choose Noreg", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
