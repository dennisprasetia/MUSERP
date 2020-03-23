package com.wonokoyo.voadip.menu.work;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.VoadipActivity;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.adapter.VoadipItemAdapter;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;

import java.io.File;

public class VoadipResultFragment extends Fragment {

    private TextView tvHead;
    private TextView tvMitra;
    private TextView tvNoreg;
    private TextView tvAlamat;
    private TextView tvNoOp;
    private TextView tvNoSj;
    private TextView tvSupplier;
    private TextView tvTglTerima;
    private TextView tvPenerima;
    private RecyclerView rvDetailResult;
    private ImageView ivSignResult;
    private Button btnDone;

    VoadipItemAdapter adapter;

    VoadipViewModel voadipViewModel;

    public VoadipResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        voadipViewModel = ((VoadipActivity) getActivity()).getVoadipViewModel();
        voadipViewModel.init(getActivity().getApplication());

        adapter = new VoadipItemAdapter(getContext(), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voadip_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Voadip voadip = voadipViewModel.getLiveVoadip().getValue();

        tvHead = view.findViewById(R.id.tvHead);
        tvHead.setText("No. BPB " + voadip.getNoOp());
        tvMitra = view.findViewById(R.id.tvMitraResult);
        tvMitra.setText(voadip.getMitra());
        tvNoreg = view.findViewById(R.id.tvNoregResult);
        tvNoreg.setText(voadip.getNoreg());
        tvAlamat = view.findViewById(R.id.tvAlamatResult);
        tvAlamat.setText(voadip.getAlamat());
        tvNoOp = view.findViewById(R.id.tvNoOpResult);
        tvNoOp.setText(voadip.getNoOp());
        tvNoSj = view.findViewById(R.id.tvNoSjResult);
        tvNoSj.setText(voadip.getNoSj());
        tvSupplier = view.findViewById(R.id.tvSupplierResult);
        tvSupplier.setText(voadip.getSupplier());
        tvTglTerima = view.findViewById(R.id.tvTglTerimaResult);
        tvTglTerima.setText(voadip.getTglTerima());
        tvPenerima = view.findViewById(R.id.tvPenerimaResult);
        tvPenerima.setText(voadip.getPenerima());

        rvDetailResult = view.findViewById(R.id.rvItemVoadipResult);
        rvDetailResult.setAdapter(adapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.syncItemDetailVoadip(voadip.getItemVoadips());
            }
        },500);

        File file = new File(voadip.getUrlSign());
        Bitmap bitmap = new BitmapDrawable(getContext().getResources(), file.getAbsolutePath()).getBitmap();

        ivSignResult = view.findViewById(R.id.ivSignResult);
        ivSignResult.setImageBitmap(bitmap);

        btnDone = view.findViewById(R.id.btnDoneResult);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_voadip_result_to_voadip_home);
            }
        });
    }
}
