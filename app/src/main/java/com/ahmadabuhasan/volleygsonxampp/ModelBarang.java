package com.ahmadabuhasan.volleygsonxampp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Ahmad Abu Hasan on 29/12/2020
 */

public class ModelBarang {

    @SerializedName("mhs_id") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private int mhs_id;
    @SerializedName("mhs_nim") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private int mhs_nim;
    @SerializedName("mhs_name") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private String mhs_name;
    @SerializedName("mhs_class") //samakan dengan key/id yang akan kita ambil dari API
    @Expose
    private String mhs_class;

    public ModelBarang(int mhs_id, int mhs_nim, String mhs_name, String mhs_class) {
        this.mhs_id = mhs_id;
        this.mhs_nim = mhs_nim;
        this.mhs_name = mhs_name;
        this.mhs_class = mhs_class;
    }

    public int getMhs_id() {
        return mhs_id;
    }

    public void setMhs_id(int mhs_id) {
        this.mhs_id = mhs_id;
    }

    public int getMhs_nim() {
        return mhs_nim;
    }

    public void setMhs_nim(int mhs_nim) {
        this.mhs_nim = mhs_nim;
    }

    public String getMhs_name() {
        return mhs_name;
    }

    public void setMhs_name(String mhs_name) {
        this.mhs_name = mhs_name;
    }

    public String getMhs_class() {
        return mhs_class;
    }

    public void setMhs_class(String mhs_class) {
        this.mhs_class = mhs_class;
    }
}
