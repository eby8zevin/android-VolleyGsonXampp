package com.ahmadabuhasan.volleygsonxampp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Ahmad Abu Hasan on 29/12/2020
 */

public class ResponStatus {

    @SerializedName("status_kode") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private int status_kode;
    @SerializedName("status_pesan") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private String status_pesan;

    public ResponStatus(int status_kode, String status_pesan) {
        this.status_kode = status_kode;
        this.status_pesan = status_pesan;
    }

    public int getStatus_kode() {
        return status_kode;
    }

    public void setStatus_kode(int status_kode) {
        this.status_kode = status_kode;
    }

    public String getStatus_pesan() {
        return status_pesan;
    }

    public void setStatus_pesan(String status_pesan) {
        this.status_pesan = status_pesan;
    }
}
