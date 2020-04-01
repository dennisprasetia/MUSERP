package com.wonokoyo.doc.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.util.SharedPrefManager;

import java.util.List;

public class CheckDocFragment extends Fragment {

    DocViewModel docViewModel;

    public CheckDocFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_doc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        docViewModel.getAllDocWithLoc().observe(getViewLifecycleOwner(), new Observer<List<DocWithLoc>>() {
            @Override
            public void onChanged(List<DocWithLoc> docWithLocs) {
                /*
                for (DocWithLoc dwl : docWithLocs) {
                    if (dwl.getDoc().getId() == 2) {
                        Doc docLoc = dwl.getDoc();
                        docLoc.setLoc(dwl.getLoc());

                        docViewModel.saveSpjDoc(docLoc);
                        docViewModel.saveDoc(docLoc, "");
                    }
                }
                */

                for (DocWithLoc dwl : docWithLocs) {
                    System.out.println(new Gson().toJson(dwl));
                }
            }
        });
    }
}
