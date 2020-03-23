package com.wonokoyo.doc.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.doc.R;
import com.wonokoyo.doc.model.Voadip;

import java.util.ArrayList;
import java.util.List;

public class PlanningVoadipAdapter extends RecyclerView.Adapter<PlanningVoadipAdapter.VoadipViewHolder> {
    List<Voadip> listVoadip;
    Context mContext;
    Activity mActivity;

    public PlanningVoadipAdapter(Context mContext, Activity mActivity) {
        this.listVoadip = new ArrayList<>();
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public VoadipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.planning_voadip_listing, parent, false);
        return new VoadipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VoadipViewHolder holder, int position) {
        Voadip voadip = listVoadip.get(position);

        VoadipItemAdapter adapter = new VoadipItemAdapter(mContext, mActivity);

        holder.tvSupplier.setText(voadip.getSupplier());
        holder.tvSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.llChildVoadip.getVisibility() == View.VISIBLE) {
                    holder.llChildVoadip.setVisibility(View.GONE);
                    holder.tvSupplier.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_down_doc, 0);
                } else {
                    holder.llChildVoadip.setVisibility(View.VISIBLE);
                    holder.tvSupplier.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,
                            R.drawable.ic_arrow_up_doc, 0);
                }
            }
        });

        holder.tvMitraVoadip.setText(voadip.getDoc().getMitra());
        holder.tvNoregVoadip.setText(voadip.getDoc().getNoreg());
        holder.tvAlamatVoadip.setText(voadip.getDoc().getAlamat());
        holder.tvNoOpVoadip.setText(voadip.getNoOp());
        holder.tvSupplierVoadip.setText(voadip.getSupplier());
        holder.tvTglKirim.setText(voadip.getTglKirim());

        holder.rvDetailVoadip.setAdapter(adapter);
        adapter.syncItemDetailVoadip(voadip.getItemVoadips());
    }

    @Override
    public int getItemCount() {
        return listVoadip.size();
    }

    public void syncDetailPlanning(List<Voadip> listVoadip) {
        this.listVoadip.clear();
        this.listVoadip.addAll(listVoadip);
        notifyDataSetChanged();
    }

    public static class VoadipViewHolder extends RecyclerView.ViewHolder {
        TextView tvSupplier;
        TextView tvMitraVoadip;
        TextView tvNoregVoadip;
        TextView tvAlamatVoadip;
        TextView tvNoOpVoadip;
        TextView tvSupplierVoadip;
        TextView tvTglKirim;
        RecyclerView rvDetailVoadip;
        LinearLayout llChildVoadip;

        public VoadipViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSupplier = itemView.findViewById(R.id.tvSupplier);
            tvMitraVoadip = itemView.findViewById(R.id.tvMitraVoadip);
            tvNoregVoadip = itemView.findViewById(R.id.tvNoregVoadip);
            tvAlamatVoadip = itemView.findViewById(R.id.tvAlamatVoadip);
            tvNoOpVoadip = itemView.findViewById(R.id.tvOpVoadip);
            tvSupplierVoadip = itemView.findViewById(R.id.tvSupplierVoadip);
            tvTglKirim = itemView.findViewById(R.id.tvTglKirim);
            rvDetailVoadip = itemView.findViewById(R.id.rvDetailVoadip);
            llChildVoadip = itemView.findViewById(R.id.llChildDetailVoadip);
        }
    }
}
