package com.wonokoyo.voadip.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.menu.plan.VoadipPlanFragmentDirections;
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
        final Voadip voadip = listVoadip.get(position);

        holder.cvPlanDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoadipPlanFragmentDirections.ActionVoadipPlanToVoadipDetailPlan direction =
                        VoadipPlanFragmentDirections.actionVoadipPlanToVoadipDetailPlan(voadip);

                Navigation.findNavController(mActivity, R.id.fragment_voadip).navigate(direction);
            }
        });

        holder.tvPlanTgl.setText(voadip.getTglKirim());
        holder.tvPlanMitra.setText(voadip.getMitra() + " (" + voadip.getNoreg() + ")");
        holder.tvPlanPopulasi.setText(voadip.getPopulasi());
        holder.tvPlanSupplier.setText(voadip.getSupplier());

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
        private CardView cvPlanDetail;
        private TextView tvPlanTgl;
        private TextView tvPlanMitra;
        private TextView tvPlanPopulasi;
        private TextView tvPlanSupplier;

        public VoadipViewHolder(@NonNull View itemView) {
            super(itemView);

            cvPlanDetail = itemView.findViewById(R.id.cvPlanDetail);
            tvPlanTgl = itemView.findViewById(R.id.tvPlanTgl);
            tvPlanMitra = itemView.findViewById(R.id.tvPlanMitra);
            tvPlanPopulasi = itemView.findViewById(R.id.tvPlanPopulasi);
            tvPlanSupplier = itemView.findViewById(R.id.tvPlanSupplier);
        }
    }
}
