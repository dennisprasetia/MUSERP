package com.wonokoyo.pakan.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.pakan.R;

import java.util.ArrayList;
import java.util.List;

public class PakanAdapter extends RecyclerView.Adapter<PakanAdapter.PakanAdapterHolder> {
    private List<Pakan> pakans;

    public PakanAdapter() {
        pakans = new ArrayList<>();
    }

    @NonNull
    @Override
    public PakanAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_pakan, parent, false);
        return new PakanAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PakanAdapterHolder holder, int position) {
        Pakan pakan = pakans.get(position);

        holder.tvNomor.setText(String.valueOf(pakan.getNomor()));
        holder.tvBerat.setText(String.valueOf(pakan.getBerat()));
    }

    @Override
    public int getItemCount() {
        return pakans.size();
    }

    public void updateData(List<Pakan> data) {
        this.pakans = data;
        this.notifyDataSetChanged();
    }

    public class PakanAdapterHolder extends RecyclerView.ViewHolder {
        private TextView tvNomor;
        private TextView tvBerat;

        public PakanAdapterHolder(View view) {
            super(view);

            tvNomor = view.findViewById(R.id.tvNomor);
            tvBerat = view.findViewById(R.id.tvBerat);
        }
    }
}
