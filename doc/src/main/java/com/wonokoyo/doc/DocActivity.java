package com.wonokoyo.doc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.model.viewmodel.VoadipViewModel;
import com.wonokoyo.doc.util.SharedPrefManager;

public class DocActivity extends AppCompatActivity {

    NavController navController;

    private Toolbar toolbar;
    private AppBarConfiguration appBarConfig;

    SharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blacky));
        setContentView(R.layout.activity_doc);

        spm = new SharedPrefManager(this);

        Intent intent = getIntent();
        spm.saveSPString(SharedPrefManager.SP_ID_USER, intent.getStringExtra("id_user"));

        navController = Navigation.findNavController(this, R.id.fragment_doc);

        /*
        toolbar = findViewById(R.id.tbDoc);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleStyle);
        setSupportActionBar(toolbar);

        appBarConfig = new AppBarConfiguration.Builder().build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        */
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment_doc), appBarConfig) ||
                super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public DocViewModel getDocViewModel() {
        return ViewModelProviders.of(this).get(DocViewModel.class);
    }

    public VoadipViewModel getVoadipViewModel() {
        return ViewModelProviders.of(this).get(VoadipViewModel.class);
    }
}
