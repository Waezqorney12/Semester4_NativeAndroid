package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.ViewHolder>{
    private List<ModelTransaction> models;
    int totalHargaTransaksi = 0 ;
    private Context context;
    private TextView subtotalTransaksi;


    public int getTotalHargaTransaksi() {
        return totalHargaTransaksi;
    }
    public AdapterTransaction(Context context) {this.context = context;}

    public void setData(List<ModelTransaction> models, TextView subtotalTransaksi) {
        this.models = models;
        this.subtotalTransaksi = subtotalTransaksi;
        calculateTotalHargaTransaksi();
        notifyDataSetChanged();
    }
    private void calculateTotalHargaTransaksi() {
        totalHargaTransaksi = 0;
        for (ModelTransaction model : models) {
            int harga = Integer.parseInt(model.getHarga());
            int qty = model.getQty();
            if (qty > 0) {
                int total = harga * qty;
                totalHargaTransaksi += total; // Menjumlahkan langsung ke totalHargaTransaksi
            }
        }
        Log.d("TAGS", "Total Akhir: " + totalHargaTransaksi);
        subtotalTransaksi.setText(String.valueOf(totalHargaTransaksi));
    }



    @NonNull
    @Override
    public AdapterTransaction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransaction.ViewHolder holder, int position) {
        ModelTransaction paket = models.get(position);
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
        private ImageView addIcon,minusIcon;
        private EditText qtyPaket;
        private TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket,totalHarga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addIcon = itemView.findViewById(R.id.addIcon);
            minusIcon = itemView.findViewById(R.id.minusIcon);
            qtyPaket = itemView.findViewById(R.id.qtyPaket);
            idOutlet = itemView.findViewById(R.id.idOutlet);
            idPaket = itemView.findViewById(R.id.idPaket);
            namaPaket = itemView.findViewById(R.id.namaPaket);
            jenisPaket = itemView.findViewById(R.id.jenisPaket);
            hargaPaket = itemView.findViewById(R.id.hargaPaket);
            totalHarga = itemView.findViewById(R.id.totalHarga);


            addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    ModelTransaction model = models.get(position);

                    String qtyAwal = qtyPaket.getText().toString();
                    int qty = qtyAwal.isEmpty() ? 0 : Integer.parseInt(qtyAwal);
                    qty++;
                    qtyPaket.setText(String.valueOf(qty));

                    int harga = Integer.parseInt(model.getHarga());
                    totalHargaTransaksi = harga * qty;

                    model.setQty(qty);
                    calculateTotalHargaTransaksi();
                    totalHarga.setText(String.valueOf(totalHargaTransaksi));


//
                }
            });


            minusIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    ModelTransaction model = models.get(position);

                    String qtyAwal = qtyPaket.getText().toString();
                    int qty = qtyAwal.isEmpty() ? 0 : Integer.parseInt(qtyAwal);
                    if (qty > 0){
                        qty--;
                        int harga = Integer.parseInt(model.getHarga());
                        totalHargaTransaksi = harga * qty;
                        model.setQty(qty);
                        calculateTotalHargaTransaksi();
                        totalHarga.setText(String.valueOf(totalHargaTransaksi));
                    }else{
                        qty = 0;
                    }
                    qtyPaket.setText(String.valueOf(qty));
                }
            });
        }
    }
}
