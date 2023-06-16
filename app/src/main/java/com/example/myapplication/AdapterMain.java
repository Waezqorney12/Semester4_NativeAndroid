package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class AdapterMain extends PagerAdapter {
    private List<ModelMain> model;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterMain(List<ModelMain> model, Context context) {
        this.model = model;
        this.context = context;


    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_main, container, false);
            TextView id,nama,telepon,alamat;


            id = view.findViewById(R.id.idMain);
            nama = view.findViewById(R.id.outletMain);
            telepon = view.findViewById(R.id.nomorMain);
            alamat = view.findViewById(R.id.jalanMain);

            id.setText(model.get(position).getId());
            nama.setText(model.get(position).getNama());
            telepon.setText(model.get(position).getTelepon());
            alamat.setText(model.get(position).getAlamat());

            container.addView(view, 0);
            return view;
        }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
    public void openActivity(int position) {
        if (position >= 0 && position < model.size()) {
            ModelMain selectedModel = model.get(position);
            Intent intent;

            // Tentukan aktivitas yang akan dibuka berdasarkan posisi item
            if (position == 0) {
                intent = new Intent(context, TransactionActivity.class);
            } else if (position == 1) {
                intent = new Intent(context, TransactionActivity.class);
            } else if (position == 2) {
                intent = new Intent(context, TransactionActivity.class);
            } else {
                // Tambahkan kondisi lain di sini jika ada lebih banyak pilihan
                return;
            }

            String id = selectedModel.getId();
            intent.putExtra("id",id);
            context.startActivity(intent);
        }
    }

}
