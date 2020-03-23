package com.wonokoyo.voadip.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.model.ItemVoadip;

import java.util.ArrayList;
import java.util.List;

public class VoadipItemAdapter extends RecyclerView.Adapter<VoadipItemAdapter.ItemVoadipViewHolder> {
    List<ItemVoadip> listItemVoadip;
    Context mContext;
    Activity mActivity;

    public VoadipItemAdapter(Context mContext, Activity mActivity) {
        this.listItemVoadip = new ArrayList<>();
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ItemVoadipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.planning_voadip_item, parent, false);
        return new ItemVoadipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVoadipViewHolder holder, int position) {
        ItemVoadip itemVoadip = listItemVoadip.get(position);

        holder.tvNoItem.setText(String.valueOf(position + 1));
        holder.tvItemName.setText(itemVoadip.getName());
        holder.tvItemPacking.setText(itemVoadip.getPacking());
        holder.tvItemAmmount.setText(String.valueOf(itemVoadip.getAmmount()));
    }

    @Override
    public int getItemCount() {
        return listItemVoadip.size();
    }

    public void syncItemDetailVoadip(List<ItemVoadip> listItemVoadip) {
        this.listItemVoadip.clear();
        this.listItemVoadip.addAll(listItemVoadip);
        notifyDataSetChanged();
    }

    public static class ItemVoadipViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoItem;
        TextView tvItemName;
        TextView tvItemPacking;
        TextView tvItemAmmount;

        public ItemVoadipViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNoItem = itemView.findViewById(R.id.tvDetailId);
            tvItemName = itemView.findViewById(R.id.tvDetailBarang);
            tvItemPacking = itemView.findViewById(R.id.tvDetailKemasan);
            tvItemAmmount = itemView.findViewById(R.id.tvDetailJumlah);
        }
    }
}
