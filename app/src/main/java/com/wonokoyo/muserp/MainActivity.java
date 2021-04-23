package com.wonokoyo.muserp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.wonokoyo.login.LoginActivity;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.AttachmentViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.EntryViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.NecropsyViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.PartnerViewModel;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.ScreenViewModel;
import com.wonokoyo.muserp.util.SharedPrefManager;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfig;
    private ImageView ivLogout;

    SharedPrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blacky));
        setContentView(R.layout.activity_main);

        setupNavigation();

        manager = new SharedPrefManager(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment_container), appBarConfig) ||
                super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                manager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                manager.saveSPString(SharedPrefManager.SP_ID_USER, data.getStringExtra("id_user"));
                manager.saveSPString(SharedPrefManager.SP_NAMA_USER, data.getStringExtra("nama_user"));
            } else {
                finishAndRemoveTask();
            }
        }
    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleStyle);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.fragment_container);

        final Set<Integer> topLevelDestinations = new ArraySet<>();
        topLevelDestinations.add(R.id.nav_home);
        topLevelDestinations.add(R.id.nav_daily);
        topLevelDestinations.add(R.id.nav_team);
        topLevelDestinations.add(R.id.nav_sales);

        appBarConfig = new AppBarConfiguration.Builder(topLevelDestinations)
//                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        NavigationUI.setupWithNavController(navigationView, navController);

        ivLogout = findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.saveSPBoolean(SharedPrefManager.SP_LOGIN, false);
                manager.saveSPString(SharedPrefManager.SP_ID_USER, "");
                manager.saveSPString(SharedPrefManager.SP_NAMA_USER, "");

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    public EntryViewModel getEntryViewModel() {
        return ViewModelProviders.of(this).get(EntryViewModel.class);
    }
    
    public PartnerViewModel getPartnerViewModel() {
        return ViewModelProviders.of(this).get(PartnerViewModel.class);
    }

    public ScreenViewModel getScreenViewModel() {
        return ViewModelProviders.of(this).get(ScreenViewModel.class);
    }

    public NecropsyViewModel getNecropsyViewModel() {
        return ViewModelProviders.of(this).get(NecropsyViewModel.class);
    }

    public AttachmentViewModel getAttachmentViewModel() {
        return ViewModelProviders.of(this).get(AttachmentViewModel.class);
    }
}
