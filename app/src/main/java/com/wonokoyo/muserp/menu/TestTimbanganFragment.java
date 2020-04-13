package com.wonokoyo.muserp.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonokoyo.doc.menu.work.DocWeighFragment;
import com.wonokoyo.muserp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TestTimbanganFragment extends Fragment {

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private TextView tvHasilTest;
    private Button btnResetTest;

    Thread thread;

    public TestTimbanganFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_timbangan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHasilTest = view.findViewById(R.id.tvHasilTest);

        btnResetTest = view.findViewById(R.id.btnResetTest);
        btnResetTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHasilTest.setText("");
            }
        });

        startThread();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopThread();
    }

    public void startThread() {
        thread = new Thread(new RunTimbang());
        thread.start();
    }

    public void stopThread() {
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }

    public class RunTimbang implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SERVER_IP, SERVERPORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String res = in.readLine();

                if (res != null) {
                    tvHasilTest.setText(res);
                }

                this.run();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
