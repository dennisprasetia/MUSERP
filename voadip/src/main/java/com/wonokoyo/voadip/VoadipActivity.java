package com.wonokoyo.voadip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;
import com.wonokoyo.voadip.util.SharedPrefManager;

public class VoadipActivity extends AppCompatActivity {

    NavController navController;

    public static String ID_USER;

    SharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blacky));
        setContentView(R.layout.activity_voadip);

        spm = new SharedPrefManager(this);

        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("id_user");

        spm.saveSPString(SharedPrefManager.SP_ID_USER, ID_USER);

        navController = Navigation.findNavController(this, R.id.fragment_voadip);
    }

    public VoadipViewModel getVoadipViewModel() {
        return ViewModelProviders.of(this).get(VoadipViewModel.class);
    }
}