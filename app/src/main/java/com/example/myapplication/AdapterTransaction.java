package com.example.myapplication;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.ViewHolder>{
    private List<TransactionModel> models;

    public void setData(List<TransactionModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterTransaction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransaction.ViewHolder holder, int position) {
        TransactionModel paket = models.get(position);
        holder.idOutlet.setText(paket.getId_outlet());
        holder.idPaket.setText(paket.getId_paket());
        holder.namaPaket.setText(paket.getNama_paket());
        holder.jenisPaket.setText(paket.getJenis());
        holder.hargaPaket.setText(paket.getHarga());
    }

    @Override
    public int getItemCount() {
        return models!=null?models.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket;
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
