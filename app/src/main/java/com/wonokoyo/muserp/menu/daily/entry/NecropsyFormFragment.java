package com.wonokoyo.muserp.menu.daily.entry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.adapter.NecropsyAdapter;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.NecropsyViewModel;

public class NecropsyFormFragment extends Fragment {

    private ImageButton btnNext;
    private RecyclerView rvNecropsy;

    NecropsyViewModel necropsyViewModel;

    NecropsyAdapter necropsyAdapter;

    EntryViewModel entryViewModel;

    public NecropsyFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        necropsyViewModel = ((MainActivity) getActivity()).getNecropsyViewModel();
        necropsyViewModel.init();

        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();

        necropsyAdapter = new NecropsyAdapter(getContext(), necropsyViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_necropsy_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvNecropsy = view.findViewById(R.id.rvNecropsy);
        rvNecropsy.setAdapter(necropsyAdapter);

        btnNext = view.findViewById(R.id.btnNextNecropsy);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryViewModel.setmListNecropsy(necropsyViewModel.getListNecropsy());

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_necropsy_form_to_daily_entry_attachment);
            }
        });
    }
}
