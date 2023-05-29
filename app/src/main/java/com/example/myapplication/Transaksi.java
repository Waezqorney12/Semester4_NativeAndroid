package com.example.londry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final CheckBox paket1 = findViewById(R.id.paketkiloan);
        final CheckBox paket2 = findViewById(R.id.paketselimut);
        final CheckBox paket3 = findViewById(R.id.paketjaket);
        final CheckBox paket4 = findViewById(R.id.paketkarpet);
        final CheckBox paket5 = findViewById(R.id.paketjeans);


        //JUMLAH Paket
        final EditText jml_pkt1 = findViewById(R.id.jumlahpaketkiloan);
        final EditText jml_pkt2 = findViewById(R.id.jumlahpaketselimut);
        final EditText jml_pkt3 = findViewById(R.id.jumlahpaketjaket);
        final EditText jml_pkt4 = findViewById(R.id.jumlahpaketkarpet);
        final EditText jml_pkt5 = findViewById(R.id.jumlahpaketjeans);
        Button btn = findViewById(R.id.submit);

        //HARGA Paket
        final int harga_paketkiloan = 5000;
        final int harga_paketselimut = 10000;
        final int harga_paketjaket = 8000;
        final int harga_paketkarpet = 15000;
        final int harga_paketjeans = 10000;

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!paket1.isChecked() && !paket2.isChecked() && !paket3.isChecked() && paket4.isChecked() && !paket5.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Layanan Paket", Toast.LENGTH_SHORT).show();
                } else {
                    String text = "";
                    int totOrder = 0;
                    int discount = 0;
                    if (paket1.isChecked()) {
                        String hasil = "Paket Kiloan";
                        int jml = Integer.parseInt(String.valueOf(jml_pkt1.getText()));
                        int cal = jml * harga_paketkiloan;
                        text += jml + "\t\t" + hasil + "\t\t\t\t\t\t\t\t\t" + "Rp. " + cal + "\n\n";
                        totOrder = totOrder + cal;

                    }
                    if (paket2.isChecked()) {
                        String hasil = "Paket Kiloan ";
                        int jml = Integer.parseInt(String.valueOf(jml_pkt2.getText()));
                        int cal = jml * harga_paketselimut;
                        text += jml + "\t\t" + hasil + "\t\t\t\t\t\t\t\t\t" + "Rp. " + cal + "\n\n";
                        totOrder = totOrder + cal;
                    }
                    if (paket3.isChecked()) {
                        String hasil = "Paket Kiloan ";
                        int jml = Integer.parseInt(String.valueOf(jml_pkt3.getText()));
                        int cal = jml * harga_paketjaket;
                        text += jml + "\t\t" + hasil + "\t\t\t\t\t\t\t\t\t" + "Rp. " + cal + "\n\n";
                        totOrder = totOrder + cal;
                    }

                    if (paket4.isChecked()) {
                        String hasil = "Paket Kiloan ";
                        int jml = Integer.parseInt(String.valueOf(jml_pkt4.getText()));
                        int cal = jml * harga_paketkarpet;
                        text += jml + "\t\t" + hasil + "\t\t\t\t\t\t\t\t\t" + "Rp. " + cal + "\n\n";
                        totOrder = totOrder + cal;
                    }
                    if (paket5.isChecked()) {
                        String hasil = "Paket Kiloan ";
                        int jml = Integer.parseInt(String.valueOf(jml_pkt5.getText()));
                        int cal = jml * harga_paketjeans;
                        text += jml + "\t\t" + hasil + "\t\t\t\t\t\t\t\t\t" + "Rp. " + cal + "\n\n";
                        totOrder = totOrder + cal;
                    }

                    if (totOrder > 100000) {
                        discount = 10000;
                    }
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("Coba", text);
                    intent.putExtra("Bayar", totOrder);
                    intent.putExtra("Discout", discount);
                    startActivity(intent);
                }
            }

        });

    }


}
