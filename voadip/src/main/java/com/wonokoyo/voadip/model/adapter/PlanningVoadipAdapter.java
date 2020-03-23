package com.wonokoyo.voadip.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.model.Voadip;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.planning_voadip, parent, false);
        return new VoadipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VoadipViewHolder holder, int position) {
        Voadip voadip = listVoadip.get(position);

        VoadipItemAdapter adapter = new VoadipItemAdapter(mContext, mActivity);
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

        public VoadipViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
