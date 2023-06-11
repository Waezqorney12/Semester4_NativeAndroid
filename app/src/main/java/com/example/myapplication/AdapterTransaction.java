package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.ViewHolder>{
    private List<TransactionModel> models;
    int totalHargaTransaksi = 0 ;
    String harga;


    public int getTotalHargaTransaksi() {
        return totalHargaTransaksi;
    }







    public void setData(List<TransactionModel> models) {
        this.models = models;
        calculateTotalHargaTransaksi();
        notifyDataSetChanged();
    }
    private void calculateTotalHargaTransaksi() {
        List<Integer> subtotalList = new ArrayList<>(); // Membuat list untuk menyimpan subtotal
        for (TransactionModel model : models) {
            int harga = Integer.parseInt(model.getHarga());
            Log.d("TAGS", "Harga: " + harga);
            int qty = model.getQty();
            Log.d("TAGS", "QTY: " + qty);
            if (qty > 0) {
                int total = harga * qty;
                Log.d("Tags", "Total: " + total);
                subtotalList.add(total); // Menyimpan subtotal ke dalam list
                Log.d("TAGS", "SubTotal" + total);
            }
        }

        totalHargaTransaksi = 0;
        for (int subtotal : subtotalList) {
            totalHargaTransaksi += subtotal; // Menjumlahkan semua subtotal dalam list
        }
        Log.d("TAGS", "Total Akhir: " + totalHargaTransaksi);
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

            harga = totalHarga.getText().toString();

            addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    TransactionModel model = models.get(position);

                    String qtyAwal = qtyPaket.getText().toString();
                    int qty = qtyAwal.isEmpty() ? 0 : Integer.parseInt(qtyAwal);
                    qty++;
                    qtyPaket.setText(String.valueOf(qty));

                    int harga = Integer.parseInt(model.getHarga());
                    totalHargaTransaksi = harga * qty;

                    model.setQty(qty);

                    totalHarga.setText(String.valueOf(totalHargaTransaksi));
                    calculateTotalHargaTransaksi();

//
                }
            });


            minusIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    TransactionModel model = models.get(position);

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
