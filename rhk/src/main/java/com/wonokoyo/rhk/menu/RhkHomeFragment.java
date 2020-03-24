package com.wonokoyo.rhk.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonokoyo.rhk.R;

public class RhkHomeFragment extends Fragment {

    private CardView cvCatatan;
    private CardView cvLaporan;
    private CardView cvKonfirmasi;

    public RhkHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rhk_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cvCatatan = view.findViewById(R.id.cvCatatan);
        cvCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvLaporan = view.findViewById(R.id.cvLaporan);

        cvKonfirmasi = view.findViewById(R.id.cvKonfirmasi);
    }
}
