package com.wonokoyo.doc.menu.work;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonokoyo.doc.DocActivity;
import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.Weigh;
import com.wonokoyo.doc.model.adapter.WeighAdapter;
import com.wonokoyo.doc.model.viewmodel.DocViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocWeighFragment extends Fragment {

    // variable untuk membaca sender
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private int nomor = 0;
    private int max_count = 0;
    private List<Weigh> weighs = new ArrayList<>();

    private EditText etBerat;
    private EditText etBox;
    private RecyclerView rvTimbang;
    private EditText etJmlTimbang;
    private EditText etBoxTara;
    private TextView tvBbDoc;
    private TextView tvBbTara;
    private CardView cvHasilHitung;
    private TextView tvSelesaiTimbang;
    private Button btnLanjut;
    private Button btnTara;
    private Button btnSelesai;
    private Button btnRefresh;

    private WeighAdapter adapter;

    Handler handler = new Handler();

    Doc mDoc;

    private Thread thread;

    DocViewModel model;

    public DocWeighFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new WeighAdapter(getContext());

        model = ((DocActivity) getActivity()).getDocViewModel();
        model.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_weigh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDoc = model.getLiveDoc().getValue();
        max_count = mDoc.getJumlahBox() / 5;

        etBerat = view.findViewById(R.id.etBerat);
        etBox = view.findViewById(R.id.etBox);

        rvTimbang = view.findViewById(R.id.rv_det_timbang);
        rvTimbang.setAdapter(adapter);

        etJmlTimbang = view.findViewById(R.id.etJmlTimbang);
        etBoxTara = view.findViewById(R.id.etBoxTara);
        tvBbDoc = view.findViewById(R.id.tvBbDoc);
        tvBbTara = view.findViewById(R.id.tvBbTara);

        cvHasilHitung = view.findViewById(R.id.cvHasilHitung);
        tvSelesaiTimbang = view.findViewById(R.id.tvSelesaiTimbang);

        btnLanjut = view.findViewById(R.id.btnLanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (nomor < max_count) {
                        nomor++;

                        Weigh w = new Weigh();
                        w.setId_spj(mDoc.getId_spj());
                        w.setNomor(nomor);
                        w.setTipe("chick");
                        w.setJmlBox(Integer.valueOf(etBox.getText().toString()));
                        w.setBerat(Double.valueOf(etBerat.getText().toString()));
                        weighs.add(w);

                        etBerat.setText("");
                        adapter.update(weighs);
                        rvTimbang.smoothScrollToPosition(weighs.size() - 1);
                        mDoc.setWeigh(weighs);

                        model.saveWeigh(w);

                        // RESET SOCKET TERIMA DATA TIMBANG
                        if (thread.isAlive()) {
                            thread.interrupt();
                            thread = null;
                        }

                        thread = recieve();
                        thread.start();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Informasi");
                        alert.setMessage("Timbang doc mencapai batas, silahkan timbang tara");
                        alert.create().show();
                    }
                }
            }
        });

        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread.isAlive()) {
                    thread.interrupt();
                    thread = null;

                    etBerat.setText("");
                    etBerat.setError(null);
                }

                thread = recieve();
                thread.start();
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Peringatan !");
        alert.setMessage("Apakah anda yakin mengakhiri dengan Tara ?");
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lastTara();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        btnTara = view.findViewById(R.id.btnTara);
        btnTara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });

        btnSelesai = view.findViewById(R.id.btnSimpan);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHitung();
            }
        });

        thread = recieve();
        thread.start();

        resumeTimbang();
    }

    public boolean validate() {
        if (etBerat.getText().toString().equalsIgnoreCase("0.0") || etBerat.getText().toString().equalsIgnoreCase("")) {
            etBerat.setError("Awas Kosong");
            return false;
        }

        etBerat.setError(null);
        return true;
    }

    public void lastTara() {
        nomor++;

        Weigh w = new Weigh();
        w.setId_spj(mDoc.getId_spj());
        w.setNomor(nomor);
        w.setTipe("tara");
        w.setJmlBox(Integer.valueOf(etBox.getText().toString()));
        w.setBerat(Double.valueOf(etBerat.getText().toString()));
        weighs.add(w);

        etBerat.setText("");
        adapter.update(weighs);
        rvTimbang.smoothScrollToPosition(weighs.size() - 1);
        mDoc.setWeigh(weighs);

        model.saveWeigh(w);

        startHitung();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public void resumeTimbang() {
        if (mDoc.getWeigh().size() > 0) {
            weighs = mDoc.getWeigh();
            nomor = mDoc.getWeigh().size();

            adapter.update(weighs);
            rvTimbang.smoothScrollToPosition(weighs.size() - 1);

            Weigh w = mDoc.getWeigh().get(nomor - 1);
            if (w.getTipe().equalsIgnoreCase("tara")) {
                if (thread.isAlive()) {
                    thread.interrupt();
                }

                startHitung();
            }
        }
    }

    public void startHitung() {
        double total = 0.0;
        int box = 0;

        for (Weigh w : weighs) {
            if (w.getTipe().equalsIgnoreCase("chick")) {
                total += w.getBerat();
                box += w.getJmlBox();
            }
        }

        double bbTara = (weighs.get(weighs.size() - 1).getBerat() / 10) * 1000;
        double bbDoc = (((total / box) - (bbTara / 1000)) / 102) * 1000;

        tvBbDoc.setText(String.format("%.0f", bbDoc));
        tvBbTara.setText(String.format("%.0f", bbTara));
        etJmlTimbang.setText(String.valueOf(box));
        etBoxTara.setText(String.valueOf(weighs.get(weighs.size() - 1).getJmlBox()));

        cvHasilHitung.setVisibility(View.VISIBLE);
        tvSelesaiTimbang.setVisibility(View.VISIBLE);
        btnTara.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        btnLanjut.setVisibility(View.GONE);

        mDoc.setBbRata(bbDoc);
        mDoc.setTaraBox(bbTara);
        mDoc.setEkorTerima(box * 100);
        mDoc.setTerimaBox(box);

        if (mDoc.getWeigh() == null) {
            mDoc.setWeigh(weighs);
        }

//        model.saveWeighs(weighs);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putSerializable("doc", mDoc);

                NavHostFragment.findNavController(getParentFragment())
                        .navigate(R.id.action_doc_weigh_to_doc_entry_form, bundle);
            }
        }, 5000);
    }

    public class RunTimbang implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SERVER_IP, SERVERPORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String res = in.readLine();

                if (res != null) {
                    Pattern pattern = Pattern.compile("\\d+\\.\\d+");
                    Matcher matcher = pattern.matcher(res);

                    if (matcher.find()) {
                        etBerat.setText(matcher.group());
                    }
                }

                this.run();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
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
                        if (response != null) {
                            Pattern pattern = Pattern.compile("\\d+\\.\\d+");
                            Matcher matcher = pattern.matcher(response);

                            if (matcher.find()) {
                                etBerat.setText(matcher.group());
                            }
                        }
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