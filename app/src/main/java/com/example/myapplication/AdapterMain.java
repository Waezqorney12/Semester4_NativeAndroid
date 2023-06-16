package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
            ImageView buttonTelepon,buttonLocation,buttonFavorite;


            id = view.findViewById(R.id.idMain);
            nama = view.findViewById(R.id.outletMain);
            telepon = view.findViewById(R.id.nomorMain);
            alamat = view.findViewById(R.id.jalanMain);
            buttonTelepon = view.findViewById(R.id.phoneCall);
            buttonLocation = view.findViewById(R.id.locationMain);
            buttonFavorite = view.findViewById(R.id.favoriteBorder);




            id.setText(model.get(position).getId());
            nama.setText(model.get(position).getNama());
            telepon.setText(model.get(position).getTelepon());
            alamat.setText(model.get(position).getAlamat());

        buttonTelepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telepons = telepon.getText().toString();
                Log.d("TAGS","Nomor: " + telepons);

                // Mengarahkan ke WhatsApp menggunakan Intent.ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Halo, kita bisa ngobrol di WhatsApp?");
                intent.putExtra(Intent.ACTION_VIEW,telepons); // Menambahkan nomor telepon dengan format WhatsApp

                intent.setPackage("com.whatsapp"); // Mengatur paket aplikasi WhatsApp

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "WhatsApp tidak terpasang di perangkat ini.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


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
