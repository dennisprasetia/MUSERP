package com.wonokoyo.pakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonokoyo.pakan.model.Pakan;
import com.wonokoyo.pakan.model.PakanAdapter;
import com.wonokoyo.pakan.model.PakanViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimbangActivity extends AppCompatActivity {
    // variable socket timbangan
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private Thread threadReceive;

    private EditText etValue;
    private RecyclerView rvTimbang;
    private Button btnNext;
    private Button btnRefresh;
    private Button btnDone;
    private TextView tvTotal;

    private List<Pakan> pakans;
    private double total = 0.0;

    private PakanAdapter adapter;

    private PakanViewModel viewModel;

    private String no_sj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timbang);

        pakans = new ArrayList<>();

        viewModel = new PakanViewModel();
        viewModel.init(getApplication(), this);

        adapter = new PakanAdapter();

        etValue = findViewById(R.id.etValue);
        tvTotal = findViewById(R.id.tvTotal);

        rvTimbang = findViewById(R.id.rv_timbang);
        rvTimbang.setAdapter(adapter);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    calculate();
                }
            }
        });

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etValue.setText("");

                if (threadReceive.isAlive()) {
                    threadReceive.interrupt();
                }
                threadReceive = recieve();
                threadReceive.start();
            }
        });

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.savePakans(pakans);
                Intent intent = new Intent(TimbangActivity.this, PakanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        threadReceive = recieve();
        threadReceive.start();

        no_sj = getIntent().getStringExtra("no_sj");
    }

    public boolean validate() {
        if (etValue.getText().toString().equalsIgnoreCase("0.0") || etValue.getText().toString().equalsIgnoreCase("")) {
            etValue.setError("Awas Kosong");
            return false;
        }

        etValue.setError(null);
        return true;
    }

    public void calculate() {
        total += Double.valueOf(etValue.getText().toString());

        tvTotal.setText(String.format("%.1f", total));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());

        int nomor = pakans.size() + 1;
        Pakan pakan = new Pakan();
        pakan.setNo_sj(no_sj);
        pakan.setNomor(nomor);
        pakan.setBerat(Double.valueOf(etValue.getText().toString()));
        pakan.setTgl_terima(date);

        if (threadReceive.isAlive()) {
            threadReceive.interrupt();
        }
        threadReceive = recieve();
        threadReceive.start();

        pakans.add(pakan);
        adapter.updateData(pakans);

        rvTimbang.smoothScrollToPosition(pakans.size() - 1);
        etValue.setText("");
    }

    public Thread recieve() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(SERVER_IP, SERVERPORT);
                    if (socket.isConnected()) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        final String response = in.readLine();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response != null) {
                                    Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]+");
                                    Matcher matcher = pattern.matcher(response);

                                    if (matcher.find()) {
                                        etValue.setError(null);
                                        etValue.setText(matcher.group());
                                    }
                                }
                            }
                        });
                        socket.close();
                    }
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}