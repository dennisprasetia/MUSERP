package com.wonokoyo.doc.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Weigh;

import java.util.ArrayList;
import java.util.List;

public class WeighAdapter extends RecyclerView.Adapter<WeighAdapter.WeighViewHolder> {
    private List<Weigh> weighs;
    private Context context;

    public WeighAdapter(Context context) {
        this.weighs = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public WeighViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_weigh, parent, false);
        return new WeighViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeighViewHolder holder, int position) {
        Weigh w = weighs.get(position);

        holder.tvNomor.setText(String.valueOf(w.getNomor()));
        holder.tvBox.setText(String.valueOf(w.getJmlBox()));
        holder.tvTipe.setText(w.getTipe());
        holder.tvBerat.setText(String.valueOf(w.getBerat()));
    }

    @Override
    public int getItemCount() {
        return weighs.size();
    }

    public void update(List<Weigh> weighs) {
        this.weighs.clear();
        this.weighs.addAll(weighs);
        this.notifyDataSetChanged();
    }

    public static class WeighViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomor;
        TextView tvBox;
        TextView tvTipe;
        TextView tvBerat;

        public WeighViewHolder(View itemView) {
            super(itemView);

            tvNomor = itemView.findViewById(R.id.tv_nomor);
            tvBox = itemView.findViewById(R.id.tv_box);
            tvTipe = itemView.findViewById(R.id.tv_tipe);
            tvBerat = itemView.findViewById(R.id.tv_berat);
        }
    }
}
