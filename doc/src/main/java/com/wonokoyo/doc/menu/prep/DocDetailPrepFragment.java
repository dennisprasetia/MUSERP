package com.wonokoyo.doc.menu.prep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.Voadip;
import com.wonokoyo.doc.model.adapter.PlanningVoadipAdapter;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;
import com.wonokoyo.doc.model.viewmodel.VoadipViewModel;
import com.wonokoyo.doc.util.biometric.BiometricCallback;
import com.wonokoyo.doc.util.biometric.BiometricManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class DocDetailPrepFragment extends Fragment implements BiometricCallback {

    BiometricManager mBiometricManager;

    private LinearLayout llChildDetail;
    private RecyclerView rvDetailVoadip;

    // variable detail
    private TextView tvNoOpDoc;
    private TextView tvRitDoc;
    private TextView tvTglDoc;
    private TextView tvMitraDoc;
    private TextView tvNoregDoc;
    private TextView tvKandangDoc;
    private TextView tvAlamatDoc;
    private TextView tvPopulasiDoc;
    private TextView tvJumlahBoxDoc;
    private TextView tvRencanaTiba;
    private TextView tvKeKandang;
    private TextView tvCheckIn;
    private Button btnKeKandang;
    private Button btnSelesai;

    String lat, lng;

    Doc mDoc;

    PlanningVoadipAdapter voadipAdapter;

    DocViewModel docViewModel;

    Handler handler;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;

    public DocDetailPrepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        docViewModel = ((DocActivity) getActivity()).getDocViewModel();
        docViewModel.init(getActivity().getApplication());

        // Inflate the layout for this fragment
        if (getArguments() != null)
            mDoc = (Doc) getArguments().getSerializable("doc");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_detail_prep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        voadipAdapter = new PlanningVoadipAdapter(getContext(), getActivity());

        llChildDetail = view.findViewById(R.id.llChildDetail);
        rvDetailVoadip = view.findViewById(R.id.rvVoadip);
        rvDetailVoadip.setAdapter(voadipAdapter);

        tvNoOpDoc = view.findViewById(R.id.tvNoOpDoc);
        tvRitDoc = view.findViewById(R.id.tvRitDoc);
        tvTglDoc = view.findViewById(R.id.tvTglDoc);
        tvMitraDoc = view.findViewById(R.id.tvMitraDoc);
        tvNoregDoc = view.findViewById(R.id.tvNoregDoc);
        tvKandangDoc = view.findViewById(R.id.tvKandangDoc);
        tvAlamatDoc = view.findViewById(R.id.tvAlamatDoc);
        tvPopulasiDoc = view.findViewById(R.id.tvPopulasiDoc);
        tvJumlahBoxDoc = view.findViewById(R.id.tvJumlahBox);
        tvRencanaTiba = view.findViewById(R.id.tvRencanaTiba);
        tvKeKandang = view.findViewById(R.id.tvKeKandang);
        tvCheckIn = view.findViewById(R.id.tvCheckIn);

        btnKeKandang = view.findViewById(R.id.btnKeKandang);
        btnKeKandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBiometricManager = new BiometricManager.BiometricBuilder(getContext())
                        .setTitle("Verifikasi")
                        .setSubtitle("Verifikasi Fingerprint")
                        .setDescription("Lakukan verifikasi finger untuk memulai perjalanan !")
                        .setNegativeButtonText("Batal")
                        .build();

                //start authentication
                mBiometricManager.authenticate(DocDetailPrepFragment.this);
            }
        });

        btnSelesai = view.findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docViewModel.loadDocByNoreg(mDoc.getNoreg()).observe(getViewLifecycleOwner(), new Observer<DocWithLoc>() {
                    @Override
                    public void onChanged(DocWithLoc docWithLoc) {
                        Doc docLoc = docWithLoc.getDoc();
                        docLoc.setLoc(docWithLoc.getLoc());
                        docLoc.setStat_track(1);

                        docViewModel.savePrepDoc(docLoc);
                        docViewModel.setEventLiveData("selesai");
//                        docViewModel.saveSpjTsLoc(docLoc);
                    }
                });
            }
        });

        docViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("selesai")) {
                    docViewModel.resetEvent();
                    NavHostFragment.findNavController(getParentFragment())
                            .navigate(R.id.action_doc_detail_prep_to_doc_home);
                }
            }
        });

        docViewModel.loadDocByNoreg(mDoc.getNoreg()).observe(getViewLifecycleOwner(), new Observer<DocWithLoc>() {
            @Override
            public void onChanged(DocWithLoc docWithLoc) {
                mDoc = docWithLoc.getDoc();
            }
        });

        setupDetailDoc(mDoc);

        getLastLocation();
    }

    public void setupDetailDoc(Doc doc) {
        tvNoOpDoc.setText(doc.getNoOpDoc());
        tvRitDoc.setText(doc.getRit());
        tvTglDoc.setText(doc.getTanggalDoc());
        tvMitraDoc.setText(doc.getMitra());
        tvNoregDoc.setText(doc.getNoreg());
        tvKandangDoc.setText(String.valueOf(doc.getKandang()));
        tvAlamatDoc.setText(doc.getAlamat());
        tvPopulasiDoc.setText(String.format("%,d", doc.getPopulasi()));
        tvJumlahBoxDoc.setText(String.valueOf(doc.getJumlahBox()));
        tvRencanaTiba.setText(doc.getKedatangan());
        if (doc.getKeKandang() == null) {
            tvKeKandang.setText(" -");
        } else {
            tvKeKandang.setText(doc.getKeKandang());
            btnKeKandang.setVisibility(View.GONE);
        }

        if (doc.getKeKandang() == null && doc.getCheckIn() == null) {
            tvCheckIn.setText(" -");
        } else {
            tvCheckIn.setText(doc.getCheckIn());
            btnSelesai.setVisibility(View.VISIBLE);
        }
    }

    // Authentication
    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getActivity().getApplicationContext(),
                "SDK Version is not supported", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Device doesn't support Biometric Authentication", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getActivity().getApplicationContext(),
                "No Fingeprint is registered in device", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Permission is not granted by User", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Failed to login", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Authentication is canceled by User", Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mDoc.setKeKandang(spf.format(new Date()));

        Loc loc = new Loc();
        loc.setNoreg_doc(mDoc.getNoreg());
        loc.setLatAwal(lat);
        loc.setLngAwal(lng);

        docViewModel.saveLocDoc(loc);

        Bundle bundle = new Bundle();
        bundle.putSerializable("doc", mDoc);

        NavHostFragment.findNavController(getParentFragment()).navigate(
                R.id.action_doc_detail_prep_to_doc_track_prep, bundle);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }

    // Get current location from device
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat = String.valueOf(location.getLatitude());
                                    lng = String.valueOf(location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            if (mLastLocation != null) {
                lat = String.valueOf(mLastLocation.getLatitude());
                lng = String.valueOf(mLastLocation.getLongitude());
            }
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}