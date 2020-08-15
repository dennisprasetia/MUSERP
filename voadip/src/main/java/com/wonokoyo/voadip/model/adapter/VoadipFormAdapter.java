package com.wonokoyo.voadip.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.voadip.R;
import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.viewmodel.VoadipViewModel;

import java.util.ArrayList;
import java.util.List;

public class VoadipFormAdapter extends RecyclerView.Adapter<VoadipFormAdapter.ItemFormViewHolder> {
    List<ItemVoadip> listItemVoadip;
    Context mContext;
    Activity mActivity;
    VoadipViewModel voadipViewModel;

    public VoadipFormAdapter(Context context, Activity activity, VoadipViewModel viewModel) {
        this.listItemVoadip = new ArrayList<>();
        this.mContext = context;
        this.mActivity = activity;
        this.voadipViewModel = viewModel;
    }

    @NonNull
    @Override
    public ItemFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.voadip_form_detail_item, parent, false);
        return new ItemFormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemFormViewHolder holder, final int position) {
        ItemVoadip item = listItemVoadip.get(position);

        holder.txtNamaItem.setText(item.getName());
        holder.etKemasan.setText(item.getPacking());
        holder.etJumlah.setText(String.valueOf(item.getAmmount()));

        holder.etKeterangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (holder.etKemasan.getText().toString() != "" &&
                            Integer.parseInt(holder.etJumlah.getText().toString()) > 0) {
                        voadipViewModel.saveItemDetailToVoadip(position, holder.etKemasan.getText().toString(),
                                Integer.valueOf(holder.etJumlah.getText().toString()), s.toString());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemVoadip.size();
    }

    public void syncItemVoadip(List<ItemVoadip> list) {
        this.listItemVoadip.clear();
        this.listItemVoadip.addAll(list);
        notifyDataSetChanged();
    }

    public static class ItemFormViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaItem;
        private EditText etKemasan;
        private EditText etJumlah;
        private EditText etKeterangan;

        public ItemFormViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamaItem = itemView.findViewById(R.id.tvFormNamaItem);
            etKemasan = itemView.findViewById(R.id.etFormKemasan);
            etJumlah = itemView.findViewById(R.id.etFormJumlah);
            etKeterangan = itemView.findViewById(R.id.etFormKeterangan);
        }
    }
}