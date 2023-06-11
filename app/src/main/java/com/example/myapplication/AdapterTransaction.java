package com.example.myapplication;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
        private ImageView addIcon,minusIcon;
        private EditText qtyPaket;
        private TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addIcon = itemView.findViewById(R.id.addIcon);
            minusIcon = itemView.findViewById(R.id.minusIcon);
            qtyPaket = itemView.findViewById(R.id.qtyPaket);

            addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion = getBindingAdapterPosition();
                    TransactionModel model = models.get(postion);

                    String qtyAwal = qtyPaket.getText().toString();

                    int qty = qtyAwal.isEmpty() ? 0 : Integer.parseInt(qtyAwal);
                    qty++;
                    qtyPaket.setText(String.valueOf(qty));

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
                    }else{
                        qty = 0;
                    }
                    qtyPaket.setText(String.valueOf(qty));
                }
            });

            idOutlet = itemView.findViewById(R.id.idOutlet);
            idPaket = itemView.findViewById(R.id.idPaket);
            namaPaket = itemView.findViewById(R.id.namaPaket);
            jenisPaket = itemView.findViewById(R.id.jenisPaket);
            hargaPaket = itemView.findViewById(R.id.hargaPaket);


        }
    }
}
