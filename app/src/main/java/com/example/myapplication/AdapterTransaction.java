package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.ViewHolder> {

    private Context context;
    private List<TransactionModel> list;

    public AdapterTransaction(Context context, List<TransactionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterTransaction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransaction.ViewHolder holder, int position) {
        TransactionModel transactionModel = list.get(position);

        holder.idOutlet.setText(transactionModel.getId_outlet());
        holder.idPaket.setText(transactionModel.getId_paket());
        holder.namaPaket.setText(transactionModel.getNama_paket());
        holder.jenisPaket.setText(transactionModel.getJenis());
        holder.hargaPaket.setText(transactionModel.getHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             idOutlet = itemView.findViewById(R.id.idOutlet);
             idPaket = itemView.findViewById(R.id.idPaket);
             namaPaket = itemView.findViewById(R.id.namaPaket);
             jenisPaket = itemView.findViewById(R.id.jenisPaket);
             hargaPaket = itemView.findViewById(R.id.hargaPaket);

        }
    }
}
