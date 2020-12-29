package com.ahmadabuhasan.volleygsonxampp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Ahmad Abu Hasan on 29/12/2020
 * "com.android.tools.build:gradle:4.1.1"
 */

public class MainActivity extends AppCompatActivity {

    // implementasi
    private RecyclerView recyclerView;
    private FloatingActionButton fab_tambah;

    private RecyclerViewAdapter adapter;
    private ArrayList<ModelBarang> arrayModelBarangs;

    public static MainActivity mInstance;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mendeklarasi
        recyclerView = findViewById(R.id.recyclerview);
        fab_tambah = findViewById(R.id.fab_tambah_barang);

        textView = findViewById(R.id.textView);

        // agar method mainActivity bisa diakses
        mInstance = this;

        // membuat linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // menset ke recyclerview layoutmanger
        recyclerView.setLayoutManager(linearLayoutManager);

        // memberi event klik pada fab (floating action buttom)
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // berpindah ke class TambahActivity
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        // untuk menghemat ruang kita buat method MuadData
        MuatData();
    }

    // method MuadData kita set ke public
    public void MuatData() {
        String url = "http://ipaddressServer/volleygsonxampp/read";
        // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // dikarenakan hasil json diawali dengan array maka membuat type
                    Type typeModelBarang = new TypeToken<ArrayList<ModelBarang>>() {
                    }.getType();
                    // menerapkan ke model class menggunakan GSON
                    // mengkonversi JSON ke java object
                    arrayModelBarangs = new Gson().fromJson(response, typeModelBarang);

                    // memanggil kontruktor adapter dan mengimplementasikannya
                    adapter = new RecyclerViewAdapter(MainActivity.this, arrayModelBarangs);
                    // lalu menset ke recyclerview adapter
                    recyclerView.setAdapter(adapter);
                    textView.setText(R.string.connect_server);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika data tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MainActivity.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MainActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // memanggil AppController dan menambahkan dalam antrin
        // text "data_mhs" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "data_mhs");
    }
}