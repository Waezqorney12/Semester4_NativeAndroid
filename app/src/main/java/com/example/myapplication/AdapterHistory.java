package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.image.setImageResource(history.getImage());
        holder.title.setText(history.getNamaPaket());
        holder.order.setText(history.getOrder_status());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImageHistory);
            title = itemView.findViewById(R.id.itemTittleHistory);
            order = itemView.findViewById(R.id.itemOrderHistory);
        }
    }
}
