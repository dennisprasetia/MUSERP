package com.wonokoyo.muserp.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.login.LoginActivity;
import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.util.SharedPrefManager;
import com.wonokoyo.pakan.PakanActivity;
import com.wonokoyo.voadip.VoadipActivity;

public class HomeFragment extends Fragment {

    private CardView cvDocin;
    private CardView cvVoadip;
    private CardView cvRhk;
    private CardView cvPakan;
    private CardView cvPanen;
    private CardView cvTestTimbang;
    private GridLayout gridLayout;
    private TextView tvNamaUser;

    SharedPrefManager manager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new SharedPrefManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!manager.getSpLogin()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent, 100);
        }

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        gridLayout = view.findViewById(R.id.glHome);

        tvNamaUser = view.findViewById(R.id.tvNamaUser);
        tvNamaUser.setText(manager.getSpNamaUser());

        NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_up)
                    .setExitAnim(R.anim.slide_out_up)
                    .setPopEnterAnim(R.anim.slide_in_down)
                    .setPopExitAnim(R.anim.slide_out_down).build();

        cvDocin = view.findViewById(R.id.cvDocin);
        cvDocin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DocActivity.class);
                intent.putExtra("id_user", manager.getSpIdUser());
                startActivity(intent);
//                onFocus(v);
            }
        });

        cvVoadip = view.findViewById(R.id.cvVoadip);
        cvVoadip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), VoadipActivity.class);
//                intent.putExtra("id_user", manager.getSpIdUser());
//                startActivity(intent);
//                onFocus(v);
            }
        });

        cvRhk = view.findViewById(R.id.cvRhk);
        cvRhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.nav_daily);
//                onFocus(v);
            }
        });

        cvPakan = view.findViewById(R.id.cvPakan);
        cvPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PakanActivity.class);
                intent.putExtra("id_user", manager.getSpIdUser());
                startActivity(intent);
//                onFocus(v);
            }
        });

        cvPanen = view.findViewById(R.id.cvPanen);
        cvPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.nav_team);
//                onFocus(v);
            }
        });

        cvTestTimbang = view.findViewById(R.id.cvTestTimbangan);
        cvTestTimbang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_home_to_test_timbangan);
            }
        });
    }

    public void onFocus(View view) {
         for (int i = 0; i < gridLayout.getChildCount(); i++) {
             View v = gridLayout.getChildAt(i);
             v.setBackgroundResource(R.drawable.bg_card_default);
         }

         view.setBackgroundResource(R.drawable.bg_card_home);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                manager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                manager.saveSPString(SharedPrefManager.SP_ID_USER, data.getStringExtra("id_user"));
                manager.saveSPString(SharedPrefManager.SP_NAMA_USER, data.getStringExtra("nama_user"));

                tvNamaUser.setText(manager.getSpNamaUser());
            } else {
                manager.saveSPString(SharedPrefManager.SP_ID_USER, "");
                manager.saveSPString(SharedPrefManager.SP_NAMA_USER, "");
                getActivity().finishAndRemoveTask();
            }
        }
    }
}
