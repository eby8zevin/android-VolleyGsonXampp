package com.ahmadabuhasan.volleygsonxampp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

/*
 * Created by Ahmad Abu Hasan on 29/12/2020
 * Update: Search on 07/01/2021
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private final Context context;
    ArrayList<ModelBarang> arrayModelBarangs, dataFilter;
    CustomFilter filter;

    // membuat kontruksi recyclerviewadapter
    public RecyclerViewAdapter(Context context, ArrayList<ModelBarang> arrayModelBarangs) {
        this.context = context;
        this.arrayModelBarangs = arrayModelBarangs;
        this.dataFilter = arrayModelBarangs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // mendapatkan posisi item
        ModelBarang modelBarang = arrayModelBarangs.get(position);

        // menset data
        holder.textView_nim.setText(String.valueOf(modelBarang.getMhs_nim()));
        holder.textView_name.setText(modelBarang.getMhs_name());
        holder.textView_class.setText(modelBarang.getMhs_class());
    }

    @Override
    public int getItemCount() {
        // mengembalikan data set
        return arrayModelBarangs.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(dataFilter, this);
        }
        return filter;
    }

    // membuat class viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        // implementasi textview
        private final TextView textView_nim;
        private final TextView textView_name;
        private final TextView textView_class;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // mendeklarasi textview
            textView_nim = itemView.findViewById(R.id.tv_nim);
            textView_name = itemView.findViewById(R.id.tv_name);
            textView_class = itemView.findViewById(R.id.tv_class);

            // mendeklarasi item ketika diklik
            itemView.setOnClickListener(v -> {
                // mendapatkan posisi pada adapter
                int position = getAdapterPosition();
                // mengambil posisi pada arraymodelbarang
                ModelBarang modelBarang = arrayModelBarangs.get(position);

                String[] pilihan = {"Lihat", "Ubah", "Hapus"};
                // menamplkan pilihan alertdialog
                new AlertDialog.Builder(context)
                        .setTitle("Pilihan")
                        .setItems(pilihan, (dialog, which) -> {
                            if (which == 0) { // 0 sama dengan Lihat
                                lihatDataBarang(modelBarang);
                            } else if (which == 1) { // 1 sama dengan Ubah
                                ubahDataBarang(modelBarang);
                            } else if (which == 2) { // 2 sama dengan Hapus
                                hapusDataBarang(position, modelBarang);
                            }
                        })
                        .create()
                        .show();
            });
        }

        // method untuk melihat data barang
        private void lihatDataBarang(@NonNull ModelBarang modelBarang) {
            // membuat rangkaiyan text deskripsi
            // fungsi tanda "\n" sama dengan ENTER.
            String deskripsi = "NIM: " + modelBarang.getMhs_nim() +
                    "\nName: " + modelBarang.getMhs_name() +
                    "\nClass: " + modelBarang.getMhs_class();

            // menampilkan deskripsi item ketika dipilih
            new AlertDialog.Builder(context)
                    .setTitle("Details")
                    .setMessage(deskripsi)
                    .setPositiveButton("Ok!", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }

        // method untuk mengubah data barang
        private void ubahDataBarang(@NonNull ModelBarang modelBarang) {
            // pindah ke UpdateActivity dan membawa data
            Intent intent = new Intent(context, UbahActivity.class);
            intent.putExtra("edit_id", modelBarang.getMhs_id());
            intent.putExtra("edit_nim", modelBarang.getMhs_nim());
            intent.putExtra("edit_name", modelBarang.getMhs_name());
            intent.putExtra("edit_class", modelBarang.getMhs_class());
            context.startActivity(intent);
        }

        // method untuk menghapus data barang
        private void hapusDataBarang(int position, @NonNull ModelBarang modelBarang) {
            String url = ApiClient.URL_DELETE + modelBarang.getMhs_id();
            // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                // menerapkan ke model class menggunakan GSON
                // mengkonversi JSON ke java object
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();

                // validasi status kode
                if (status_kode == 1) {
                    // menampilkan toast berhasil
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();

                    // menghapus pada tampilan arraylist!
                    notifyItemRemoved(position);
                    arrayModelBarangs.remove(position);
                } else if (status_kode == 2) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toast.makeText(context, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            });

            // memanggil AppController dan menambahkan dalam antrin
            // text "delete_data" anda bisa mengganti inisial yang lain
            AppController.getInstance().addToQueue(request, "delete_data");
        }
    }
}
