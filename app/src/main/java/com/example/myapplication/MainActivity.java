package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    AdapterMain adapter;
    List<ModelMain> models;
//    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        models = new ArrayList<>();
        models.add(new ModelMain(R.drawable.setrika_foto,"Paket Setrika","Khusus untuk menyetrika pakaian","Rp.12000"));
        models.add(new ModelMain(R.drawable.cuci_basah_foto,"Paket Setrika","Khusus untuk mencuci pakaian","Rp.15000"));
        models.add(new ModelMain(R.drawable.dry_cleaning_foto,"Paket Setrika","Khusus untuk mengeringkan pakaian","Rp.10000"));

        adapter = new AdapterMain(models,this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

//        Integer[] colors_temp = {
//                getResources().getColor(R.color.white),
//                getResources().getColor(R.color.white),
//                getResources().getColor(R.color.white),
//                getResources().getColor(R.color.white)
//        };
//        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position<(adapter.getCount()-1) && position < (colors.length - 1)){
//                    viewPager.setBackground(
//                            (Integer)argbEvaluator.evaluate(
//                            positionOffset,
//                            colors[position],
//                            colors[position + 1]
//                    )
//                    );
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}