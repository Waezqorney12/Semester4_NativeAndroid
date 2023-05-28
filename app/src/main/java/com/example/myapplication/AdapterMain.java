package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            ImageView imageView;
            TextView paket,keterangan,harga;

            imageView = view.findViewById(R.id.imageMain);
            paket = view.findViewById(R.id.paketMain);
            keterangan = view.findViewById(R.id.keteranganMain);
            harga = view.findViewById(R.id.hargaMain);

            imageView.setImageResource(model.get(position).getImage());
            paket.setText(model.get(position).getTittle());
            keterangan.setText(model.get(position).getDesc());
            harga.setText(model.get(position).getPrice());

            container.addView(view, 0);
            return view;
        }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
