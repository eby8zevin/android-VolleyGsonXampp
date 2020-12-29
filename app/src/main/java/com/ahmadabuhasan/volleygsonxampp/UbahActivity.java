package com.ahmadabuhasan.volleygsonxampp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.HashMap;
import java.util.Map;

/*
 * Created by Ahmad Abu Hasan on 29/12/2020
 */

public class UbahActivity extends AppCompatActivity {

    // implementasi
    private EditText editText_nim, editText_name, editText_class;
    // untuk menerima Data dari MainActivity
    private String edit_name, edit_class;
    private int edit_id, edit_nim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update);

        // deklarasi
        editText_nim = findViewById(R.id.et_nim);
        editText_name = findViewById(R.id.et_name);
        editText_class = findViewById(R.id.et_class);
        Button btn_save = findViewById(R.id.save_add_edit);

        // mengubah text pada buttom
        btn_save.setText(R.string.update);

        // menerima data dari MainActivity menggunakana "Bundle"
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            edit_id = intent.getInt("edit_id");
            edit_nim = intent.getInt("edit_nim");
            edit_name = intent.getString("edit_name");
            edit_class = intent.getString("edit_class");
        }

        // lalu "Bundle" ini, akan di set ke edittext
        editText_nim.setText(String.valueOf(edit_nim));
        editText_name.setText(edit_name);
        editText_class.setText(edit_class);

        // memberi action pada floating action buttom
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mengambil text dalam edittext
                String nim = editText_nim.getText().toString();
                String name = editText_name.getText().toString();
                String classs = editText_class.getText().toString();

                // validasi nim, name dan class tidak boleh kosong
                if (nim.isEmpty()) { // kode_barang tidak lebih dari 6 digit
                    Toast.makeText(UbahActivity.this, "NIM Still Empty!", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(UbahActivity.this, "Name Still Empty!", Toast.LENGTH_SHORT).show();
                } else if (classs.isEmpty()) { // harga barang tidak boleh dari 9 digit
                    Toast.makeText(UbahActivity.this, "Class Still Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // mengupdate data
                    updateData(edit_id, nim, name, classs);
                }
            }
        });
    }

    private void updateData(int id, String nim, String name, String classs) {
        String url = "http://ipaddressServer/volleygsonxampp/update";
        // buat StringRequest volley dan jangan lupa requestnya POST "Request.Method.POST"
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // menerapkan ke model class menggunakan GSON
                // mengkonversi JSON ke java object
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();

                // jika respon status kode yg dihasilkan 1 maka berhasil
                if (status_kode == 1) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                    MainActivity.mInstance.MuatData(); // memanggil MainActivity untuk memproses method MemuatData()
                    finish(); // keluar
                } else if (status_kode == 2) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(UbahActivity.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahActivity.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toast.makeText(UbahActivity.this, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahActivity.this, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahActivity.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahActivity.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                // set ke params
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("nim", nim);
                hashMap.put("name", name);
                hashMap.put("class", classs);

                return hashMap;
            }
        };

        // memanggil AppController dan menambahkan dalam antrin
        // text "edit_data" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "edit_data");
    }
}
