package com.wonokoyo.doc.menu;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;

import java.util.ArrayList;
import java.util.List;

public class DocHomeFragment extends Fragment {

    private CardView cvPlan;
    private CardView cvEntry;
    private Button btnUpload;

    DocViewModel docViewModel;

    ProgressDialog pd;

    public DocHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Sedang mengupload..");
        pd.setCancelable(false);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        docViewModel.getEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("file_uploaded")) {
                    Toast.makeText(getContext(), "FILE TERUPLOAD", Toast.LENGTH_SHORT).show();
                    docViewModel.resetEvent();
                }

                if (s.equalsIgnoreCase("file_not_uploaded")) {
                    Toast.makeText(getContext(), "FILE TIDAK TERUPLOAD", Toast.LENGTH_SHORT).show();
                    docViewModel.resetEvent();
                }

                if (s.equalsIgnoreCase("uploaded")) {
                    Toast.makeText(getContext(), "UPLOAD SPJ SELESAI", Toast.LENGTH_SHORT).show();
                    docViewModel.resetEvent();
                    pd.dismiss();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cvPlan = view.findViewById(R.id.cvPlan);
        cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_home_to_doc_prep);
            }
        });

        cvEntry = view.findViewById(R.id.cvEntry);
        cvEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_home_to_doc_scan);
            }
        });

        btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                docViewModel.getAllDocWithLoc().observe(getViewLifecycleOwner(), new Observer<List<DocWithLoc>>() {
                    @Override
                    public void onChanged(final List<DocWithLoc> docWithLocs) {
                        List<Doc> docs = new ArrayList<>();
                        for (DocWithLoc dwl : docWithLocs) {
                            Doc doc = dwl.getDoc();
                            doc.setLoc(dwl.getLoc());

                            if (doc.getUrl() != "" && doc.getUrl() != null) {
                                docViewModel.uploadAttachment(doc.getUrl());
                            }

                            docs.add(doc);
                        }

                        docViewModel.uploadFromLokal(docs);
                    }
                });
            }
        });
    }
}