package com.wonokoyo.muserp.menu.daily.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Screen;

import java.util.ArrayList;
import java.util.List;

public class ScreenAdapter extends RecyclerView.Adapter<ScreenAdapter.ScreenViewHolder> {
    private List<Screen> listScreen;
    Context mContext;

    public ScreenAdapter(Context context) {
        listScreen = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public ScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.daily_screen_content_recycle, parent, false);
        return new ScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenViewHolder holder, int position) {
        Screen screen = listScreen.get(position);

        holder.tvNumber.setText(String.valueOf(screen.getSequence()));
        holder.tvScreenNumber.setText(String.valueOf(screen.getQuantity()));
        holder.tvScreenWeight.setText(String.valueOf(screen.getBbavg()));
    }

    @Override
    public int getItemCount() {
        return listScreen.size();
    }

    public void addNewScreen(List<Screen> screens) {
        this.listScreen.clear();
        this.listScreen.addAll(screens);
        this.notifyDataSetChanged();
    }

    public static class ScreenViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvScreenNumber;
        TextView tvScreenWeight;

        public ScreenViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvScreenNumber = itemView.findViewById(R.id.tvScreenNumber);
            tvScreenWeight = itemView.findViewById(R.id.tvScreenWeight);
        }
    }
}
