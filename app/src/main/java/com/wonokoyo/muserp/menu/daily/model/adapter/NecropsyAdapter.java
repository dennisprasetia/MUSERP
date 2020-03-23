package com.wonokoyo.muserp.menu.daily.model.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.muserp.R;
import com.wonokoyo.muserp.menu.daily.model.Necropsy;
import com.wonokoyo.muserp.menu.daily.model.viewmodel.NecropsyViewModel;

import java.util.ArrayList;
import java.util.List;

public class NecropsyAdapter extends RecyclerView.Adapter<NecropsyAdapter.NecropsyViewHolder> {
    private List<Necropsy> listNecropsy;
    private Context mContext;
    private NecropsyViewModel mNecropsyViewModel;

    public NecropsyAdapter(Context mContext, NecropsyViewModel necropsyViewModel) {
        this.mContext = mContext;
        this.mNecropsyViewModel = necropsyViewModel;

        this.listNecropsy = new ArrayList<>();
        this.listNecropsy.addAll(necropsyViewModel.getListNecropsy());
    }

    @NonNull
    @Override
    public NecropsyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.daily_necropsy_recycle, parent, false);
        return new NecropsyViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull NecropsyViewHolder holder, int position) {
        final Necropsy necropsy = listNecropsy.get(position);

        holder.tvParam.setText(necropsy.getParameter());
        holder.etParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNecropsyViewModel.updateDescNecropsy(necropsy.getId(), s.toString());
            }
        });
        holder.cbParam.setChecked(necropsy.isStatus());
        holder.cbParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNecropsyViewModel.updateNecropsy(necropsy.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNecropsy.size();
    }

    public static class NecropsyViewHolder extends RecyclerView.ViewHolder {
        TextView tvParam;
        CheckBox cbParam;
        EditText etParam;

        public NecropsyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvParam = itemView.findViewById(R.id.tvParam);
            cbParam = itemView.findViewById(R.id.cbParam);
            etParam = itemView.findViewById(R.id.etParam);
        }
    }
}
