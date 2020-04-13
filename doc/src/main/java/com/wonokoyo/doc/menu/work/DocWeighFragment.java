package com.wonokoyo.doc.menu.work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DocWeighFragment extends Fragment {

    // variable untuk membaca sender
    private static final int SERVERPORT = 5000;
//    private static final String SERVER_IP = "192.168.0.3";
    private static final String SERVER_IP = "192.168.100.10";

    private int indexTimbang = 1;

    private EditText etBerat;
    private TextView tvTimbang1;
    private TextView tvTimbang2;
    private TextView tvTimbang3;
    private EditText etBoxTimbang1;
    private EditText etBoxTimbang2;
    private EditText etBoxTimbang3;
    private TextView tvBbDoc;
    private TextView tvBbTara;
    private CardView cvHasilHitung;
    private TextView tvSelesaiTimbang;

    Handler handler = new Handler();

    Doc mDoc;

    Thread thread;

    public DocWeighFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDoc = (Doc) getArguments().getSerializable("doc");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_weigh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etBerat = view.findViewById(R.id.etBerat);

        tvTimbang1 = view.findViewById(R.id.tvTimbang1);
        tvTimbang2 = view.findViewById(R.id.tvTimbang2);
        tvTimbang3 = view.findViewById(R.id.tvTimbang3);

        etBoxTimbang1 = view.findViewById(R.id.etBoxTimbang1);
        etBoxTimbang2 = view.findViewById(R.id.etBoxTimbang2);
        etBoxTimbang3 = view.findViewById(R.id.etBoxTimbang3);

        tvBbDoc = view.findViewById(R.id.tvBbDoc);
        tvBbTara = view.findViewById(R.id.tvBbTara);

        cvHasilHitung = view.findViewById(R.id.cvHasilHitung);
        tvSelesaiTimbang = view.findViewById(R.id.tvSelesaiTimbang);

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

    public void startHitung() {
        double tim1 = Double.valueOf(tvTimbang1.getText().toString());
        double tim2 = Double.valueOf(tvTimbang2.getText().toString());
        double tim3 = Double.valueOf(tvTimbang3.getText().toString());

        int box1 = Integer.valueOf(etBoxTimbang1.getText().toString());
        int box2 = Integer.valueOf(etBoxTimbang2.getText().toString());
        int box3 = Integer.valueOf(etBoxTimbang3.getText().toString());

        double bbDoc = ((tim1 + tim2 - tim3) / ((box1 + box2) * 102)) * 1000;
        double bbTara = tim3 / box3;

        tvBbDoc.setText(String.format("%.2f", bbDoc));
        tvBbTara.setText(String.format("%.2f", bbTara));

        cvHasilHitung.setVisibility(View.VISIBLE);
        tvSelesaiTimbang.setVisibility(View.VISIBLE);

        mDoc.setBbRata(bbDoc);
        mDoc.setTaraBox(bbTara);
    }

    public class RunTimbang implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SERVER_IP, SERVERPORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String res = in.readLine();

                if (res != null) {
                    String trim = res.trim();
//                    String[] result = res.split("\\+");
                    String replace_char = trim.replaceAll("[a-zA-Z]", "");
                    String replace_sym = replace_char.replaceAll("\\+", "");
                    String replace_sym2 = replace_sym.replaceAll(",", "");
                    final double dec = Double.valueOf(replace_sym2);

                    etBerat.setText(String.format("%.2f", dec));

                    if (indexTimbang == 1) {
                        tvTimbang1.post(new Runnable() {
                            @Override
                            public void run() {
                                tvTimbang1.setText(String.format("%.2f", dec));
                                indexTimbang++;
                            }
                        });
                    }

                    if (indexTimbang == 2) {
                        tvTimbang2.post(new Runnable() {
                            @Override
                            public void run() {
                                tvTimbang2.setText(String.format("%.2f", dec));
                                indexTimbang++;
                            }
                        });
                    }

                    if (indexTimbang == 3) {
                        tvTimbang3.post(new Runnable() {
                            @Override
                            public void run() {
                                tvTimbang3.setText(String.format("%.2f", dec));
                                startHitung();
                            }
                        });

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("doc", mDoc);

                                NavHostFragment.findNavController(getParentFragment())
                                        .navigate(R.id.action_doc_weigh_to_doc_entry_form, bundle);
                            }
                        }, 10000);
                    }
                }

                this.run();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
