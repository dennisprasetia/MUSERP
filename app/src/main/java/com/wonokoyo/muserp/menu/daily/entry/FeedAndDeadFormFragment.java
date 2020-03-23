package com.wonokoyo.muserp.menu.daily.entry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;

public class FeedAndDeadFormFragment extends Fragment {

    private ImageButton btnNext;
    private EditText etReceive;
    private EditText etRemain;
    private EditText etDeath;
    private EditText etDescription;

    EntryViewModel entryViewModel;

    public FeedAndDeadFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_and_dead_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etReceive = view.findViewById(R.id.etTerimaPakan);
        etRemain = view.findViewById(R.id.etSisaPakan);
        etDeath = view.findViewById(R.id.etKematian);
        etDescription = view.findViewById(R.id.etKeterangan);

        btnNext = view.findViewById(R.id.btnNextFD);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryViewModel.addFad(etReceive.getText().toString(), etRemain.getText().toString(),
                        etDeath.getText().toString(), etDescription.getText().toString());

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_feed_and_dead_form_to_necropsy_form);
            }
        });
    }
}
