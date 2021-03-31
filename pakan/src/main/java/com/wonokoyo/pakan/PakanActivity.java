package com.wonokoyo.pakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wonokoyo.pakan.model.Pakan;
import com.wonokoyo.pakan.model.PakanViewModel;

import java.util.List;

public class PakanActivity extends AppCompatActivity {
    private CardView btnWork;
    private CardView btnUpload;
    private TextView tvData;

    PakanViewModel viewModel;

    private List<Pakan> pakans;
    private LifecycleOwner owner = this;
    private Observer<List<Pakan>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakan);

        viewModel = new PakanViewModel();
        viewModel.init(getApplication(), this);

        tvData = findViewById(R.id.tvData);

        btnWork = findViewById(R.id.btnWork);
        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWork();
            }
        });

        observer = new Observer<List<Pakan>>() {
            @Override
            public void onChanged(List<Pakan> list) {
                if (list.size() > 0) {
                    viewModel.uploadPakanTimbang(list);
                } else {
                    Toast toast = Toast.makeText(PakanActivity.this, "Data Kosong / Data Sudah Upload", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                viewModel.getAllPakanNotUploaded().removeObserver(observer);
                viewModel.getAllPakanNotUploaded().removeObservers(owner);
            }
        };

        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getAllPakanNotUploaded().observe(owner, observer);
            }
        });

        viewModel.countData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvData.setText(String.valueOf(integer));
            }
        });
    }

    public void goToWork() {
        Intent intent = new Intent(PakanActivity.this, SjActivity.class);
        startActivity(intent);
        finish();
    }
}