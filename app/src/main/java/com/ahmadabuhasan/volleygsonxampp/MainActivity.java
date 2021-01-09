package com.ahmadabuhasan.volleygsonxampp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

    private RecyclerViewAdapter adapter;
    private ArrayList<ModelBarang> arrayModelBarangs;

    public static MainActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mendeklarasi
        recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab_tambah = findViewById(R.id.fab_tambah_barang);

        // agar method mainActivity bisa diakses
        mInstance = this;

        // membuat linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // menset ke recyclerview layoutmanger
        recyclerView.setLayoutManager(linearLayoutManager);

        // memberi event klik pada fab (floating action buttom)
        fab_tambah.setOnClickListener(v -> {
            // berpindah ke class TambahActivity
            startActivity(new Intent(MainActivity.this, TambahActivity.class));
        });

        // untuk menghemat ruang kita buat method MuadData
        MuatData();
    }

    // method MuadData kita set ke public
    public void MuatData() {
        String url = ApiClient.URL_READ;
        // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
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
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> {
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
        });

        // memanggil AppController dan menambahkan dalam antrin
        // text "data_mhs" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "data_mhs");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Data...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}