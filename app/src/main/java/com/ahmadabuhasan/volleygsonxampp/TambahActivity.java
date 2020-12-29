package com.ahmadabuhasan.volleygsonxampp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * Created by Ahmad Abu Hasan on 29/12/2020
 */

public class TambahActivity extends AppCompatActivity {

    // implementasi
    private EditText editText_nim, editText_name, editText_class;
    private Button btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update);

        // deklarasi
        editText_nim = findViewById(R.id.et_nim);
        editText_name = findViewById(R.id.et_name);
        editText_class = findViewById(R.id.et_class);
        btn_save = findViewById(R.id.save_add_edit);

        // memberi action pada floating action buttom
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mengambil text dalam edittext
                String nim = editText_nim.getText().toString();
                String name = editText_name.getText().toString();
                String classs = editText_class.getText().toString();

                // validasi nim, name dan classs tidak boleh kosong
                if (nim.isEmpty()) {
                    Toast.makeText(TambahActivity.this, "NIM Still Empty!", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(TambahActivity.this, "Name Still Empty!", Toast.LENGTH_SHORT).show();
                } else if (classs.isEmpty()) {
                    Toast.makeText(TambahActivity.this, "Class Still Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // menyimpan data ke database
                    try {
                        simpanData(nim, name, classs);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void simpanData(String nim, String name, String classs) throws UnsupportedEncodingException {
        // karena text ini kita masukkan ke-link maka kode dan nama kita konversikan ke bentuk "utf-8"
        // contoh : "Samsung Galaxy M2" ==> dikonversi menjadi "Samsung+Galaxy+M2"
        //
        // dan ketika di simpan ke database text tidak akan berupa text konversi melaikan text aslinya.
        String convert_name = URLEncoder.encode(name, "utf-8");
        String convert_class = URLEncoder.encode(classs, "utf-8");

        String url = "http://ipaddressServer/volleygsonxampp/create?nim=" + nim + "&name=" + convert_name + "&class=" + convert_class;
        // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // menerapkan ke model class menggunakan GSON
                // mengkonversi JSON ke java object
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();

                // jika status kode sama dengan 1 maka berhasil
                if (status_kode == 1) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                    MainActivity.mInstance.MuatData(); // memanggil MainActivity untuk memproses method MemuatData()
                    finish(); // keluar/selesai
                } else if (status_kode == 2) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(TambahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahActivity.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahActivity.this, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahActivity.this, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahActivity.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahActivity.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // memanggil AppController dan menambahkan dalam antrin
        // text "add_data" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "add_data");
    }
}
