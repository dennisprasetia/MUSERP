package com.wonokoyo.pakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.wonokoyo.pakan.model.PakanViewModel;

public class SjActivity extends AppCompatActivity {
    private Spinner spMitra;
    private Spinner spNoreg;
    private Spinner spNoSj;
    private Button btnNext;

    private PakanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sj);

        viewModel = new PakanViewModel();
        viewModel.init(getApplication(), this);
        viewModel.getMitra();

        spMitra = findViewById(R.id.spMitra);
        spMitra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mitra = spMitra.getItemAtPosition(position).toString();
                viewModel.getNoregByMitra(mitra);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getLiveDataMitra().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                ArrayAdapter<String> adapterMitra = new ArrayAdapter<>(SjActivity.this, android.R.layout.simple_spinner_dropdown_item,
                        strings);
                spMitra.setAdapter(adapterMitra);
            }
        });

        spNoreg = findViewById(R.id.spNoreg);
        spNoreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String noreg = spNoreg.getItemAtPosition(position).toString();
                viewModel.getSjByNoreg(noreg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.getLiveDataNoreg().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                ArrayAdapter<String> adapterNoreg = new ArrayAdapter<>(SjActivity.this, android.R.layout.simple_spinner_dropdown_item,
                        strings);
                spNoreg.setAdapter(adapterNoreg);
            }
        });

        spNoSj = findViewById(R.id.spNoSj);

        viewModel.getLiveDataSj().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                ArrayAdapter<String> adapterSj = new ArrayAdapter<>(SjActivity.this, android.R.layout.simple_spinner_dropdown_item,
                        strings);
                spNoSj.setAdapter(adapterSj);
            }
        });

        btnNext = findViewById(R.id.btnNextTimbang);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimbang();
            }
        });
    }

    public void toTimbang() {
        Intent intent = new Intent(this, TimbangActivity.class);
        intent.putExtra("no_sj", spNoSj.getItemAtPosition(spNoSj.getSelectedItemPosition()).toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SjActivity.this, PakanActivity.class);
        startActivity(intent);
        finish();
    }
}