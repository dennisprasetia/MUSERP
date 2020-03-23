package com.wonokoyo.doc.menu.work;

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

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocDetail;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DocEntryOpFragment extends Fragment {

    private EditText etNoOp;
    private Button btnSearch;

    DocViewModel docViewModel;

    ProgressDialog pd;

    public DocEntryOpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        pd = new ProgressDialog(getContext());
        pd.setMessage("Mohon tunggu..");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_entry_op, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etNoOp = view.findViewById(R.id.etNoOpDoc);

        docViewModel.getLiveDoc().observe(this, new Observer<Doc>() {
            @Override
            public void onChanged(final Doc doc) {
                if (doc != null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("doc", doc);

                            NavHostFragment.findNavController(getParentFragment()).navigate(
                                    R.id.action_doc_entry_op_to_doc_confirm, bundle);
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
                docViewModel.loadDocByOp(etNoOp.getText().toString()).observe(getParentFragment(),
                        new Observer<Doc>() {
                            @Override
                            public void onChanged(Doc doc) {
                                docViewModel.setDocMutableLiveData(doc);
                            }
                        });
            }
        });
    }
}
