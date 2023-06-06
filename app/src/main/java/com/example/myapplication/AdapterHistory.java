package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {

    private Context context;
    private List<ModelHistory> list;

    public AdapterHistory(Context context, List<ModelHistory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int position) {
        ModelHistory history = list.get(position);

        holder.invoice.setText(history.getKd_invoice());
        holder.status.setText(history.getStatus());
        holder.tanggal.setText(history.getTanggal_pesan());
        holder.name.setText(history.getId_outlet_data().getNama());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,invoice, status, tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.namaTokoHistory);
             invoice = itemView.findViewById(R.id.invoiceHistory);
             status = itemView.findViewById(R.id.statusHistory);
             tanggal = itemView.findViewById(R.id.tanggalHistory);
        }
    }
}
