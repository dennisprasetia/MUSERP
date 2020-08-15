package com.wonokoyo.muserp.menu.daily.entry;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.wonokoyo.muserp.MainActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.AttachmentViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.NecropsyViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.ScreenViewModel;
import com.wonokoyo.muserp.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class SolutionFormFragment extends Fragment {

    private CheckBox cbSolution1;
    private CheckBox cbSolution2;
    private CheckBox cbSolution3;
    private CheckBox cbSolution4;
    private Button btnDoneSave;

    List<String> stringSolution;

    ScreenViewModel screenViewModel;
    NecropsyViewModel necropsyViewModel;
    AttachmentViewModel attachmentViewModel;
    EntryViewModel entryViewModel;

    private ProgressDialog progressDialog;

    SharedPrefManager manager;

    public SolutionFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenViewModel = ((MainActivity) getActivity()).getScreenViewModel();
        necropsyViewModel = ((MainActivity) getActivity()).getNecropsyViewModel();
        attachmentViewModel = ((MainActivity) getActivity()).getAttachmentViewModel();
        entryViewModel = ((MainActivity) getActivity()).getEntryViewModel();

        stringSolution = new ArrayList<>();

        manager = new SharedPrefManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solution_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cbSolution1 = view.findViewById(R.id.cbSolution1);
        cbSolution2 = view.findViewById(R.id.cbSolution2);
        cbSolution3 = view.findViewById(R.id.cbSolution3);
        cbSolution4 = view.findViewById(R.id.cbSolution4);

        btnDoneSave = view.findViewById(R.id.btnDoneSave);
        btnDoneSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbSolution1.isChecked())
                    stringSolution.add(cbSolution1.getText().toString());
                if (cbSolution2.isChecked())
                    stringSolution.add(cbSolution2.getText().toString());
                if (cbSolution3.isChecked())
                    stringSolution.add(cbSolution3.getText().toString());
                if (cbSolution4.isChecked())
                    stringSolution.add(cbSolution4.getText().toString());

                entryViewModel.setmListSolution(stringSolution);

                new AsyncTask<String, String, String>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("Uploading Data");
                        progressDialog.setMessage("Please wait a moment");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        entryViewModel.saveRhk(manager.getSpIdUser());
                        entryViewModel.uploadAttachment();

                        return "Saved";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        screenViewModel.clear();
                        necropsyViewModel.clear();
                        attachmentViewModel.clear();
                        entryViewModel.clearAllData();

                        progressDialog.dismiss();
                        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_solution_form_to_home);
                    }
                }.execute();
            }
        });
    }
}