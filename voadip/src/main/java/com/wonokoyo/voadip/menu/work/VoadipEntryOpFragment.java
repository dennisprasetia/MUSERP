package com.wonokoyo.voadip.menu.work;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;

public class VoadipEntryOpFragment extends Fragment {

    private EditText etNoOp;
    private Button btnSearch;

    VoadipViewModel voadipViewModel;

    ProgressDialog pd;

    public VoadipEntryOpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        pd = new ProgressDialog(getContext());
        pd.setMessage("Mohon tunggu..");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voadip_entry_op, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etNoOp = view.findViewById(R.id.etNoOpVoadip);

        voadipViewModel.getLiveVoadip().observe(this, new Observer<Voadip>() {
            @Override
            public void onChanged(final Voadip voadip) {
                if (voadip != null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            NavHostFragment.findNavController(getParentFragment()).navigate(
                                    R.id.action_voadip_entry_op_to_voadip_confirm);
                        }
                    }, 1000);
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "No OP Tidak ditemukan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },1500);
                }
            }
        });

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                voadipViewModel.loadVoadipByOp(etNoOp.getText().toString()).observe(getParentFragment(),
                        new Observer<VoadipWithItem>() {
                    @Override
                    public void onChanged(VoadipWithItem voadipWithItem) {
                        Voadip voadip = voadipWithItem.getVoadip();
                        voadip.setItemVoadips(voadipWithItem.getVoadipItems());

                        voadipViewModel.setVoadipMutableLiveData(voadip);
                    }
                });
            }
        });
    }
}
