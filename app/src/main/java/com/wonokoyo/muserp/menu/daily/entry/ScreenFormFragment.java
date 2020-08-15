package com.wonokoyo.muserp.menu.daily.entry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Screen;
import com.wonokoyo.muserp.menu.daily.model.adapter.ScreenAdapter;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.ScreenViewModel;

import java.util.List;

public class ScreenFormFragment extends Fragment {

    private EditText etQuantity;
    private EditText etWeight;
    private ImageButton btnAdd;
    private RecyclerView rvScreen;
    private ImageButton btnNextScreen;

    ScreenViewModel screenViewModel;

    ScreenAdapter screenAdapter;

    EntryViewModel entryViewModel;

    public ScreenFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenViewModel = ((MainActivity) getActivity()).getScreenViewModel();
        screenViewModel.init();

        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();

        screenAdapter = new ScreenAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        screenViewModel.getScreenList().observe(getViewLifecycleOwner(), new Observer<List<Screen>>() {
            @Override
            public void onChanged(List<Screen> screens) {
                screenAdapter.addNewScreen(screens);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        rvScreen = view.findViewById(R.id.rvScreen);
        rvScreen.setAdapter(screenAdapter);

        etQuantity = view.findViewById(R.id.etJumlahSekat);
        etWeight = view.findViewById(R.id.etBbRata);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            int index = 0;

            @Override
            public void onClick(View v) {
                index += 1;

                if (!etQuantity.getText().toString().equalsIgnoreCase("")
                        && !etWeight.getText().toString().equalsIgnoreCase("")) {
                    screenViewModel.populateScreen(index,
                            Integer.valueOf(etQuantity.getText().toString()),
                            Double.valueOf(etWeight.getText().toString()));
                } else {
                    etQuantity.setError("Harus diisi");
                    etWeight.setError("Harus diisi");
                }
            }
        });

        btnNextScreen = view.findViewById(R.id.btnNextScreen);
        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenAdapter.getItemCount() > 0) {
                    entryViewModel.setmListScreen(screenViewModel.getScreenList().getValue());
                    NavHostFragment.findNavController(getParentFragment())
                            .navigate(R.id.action_screen_form_to_feed_and_dead_form);
                } else {
                    Snackbar.make(view, "Please insert shed's screen first", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
