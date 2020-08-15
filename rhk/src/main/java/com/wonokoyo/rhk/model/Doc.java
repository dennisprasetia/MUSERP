package com.wonokoyo.rhk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "doc")
public class Doc implements Serializable {
    @PrimaryKey
    @SerializedName("idRdim")
    @Expose
    private int idRdim;

    @SerializedName("namaMitra")
    @Expose
    private String namaMitra;

    @SerializedName("noreg")
    @Expose
    private String noreg;

    @SerializedName("kandang")
    @Expose
    private String kandang;

    @SerializedName("populasi")
    @Expose
    private int populasi;

    @SerializedName("umur")
    @Expose
    private int umur;

    public int getIdRdim() {
        return idRdim;
    }

    public void setIdRdim(int idRdim) {
        this.idRdim = idRdim;
    }

    public String getNamaMitra() {
        return namaMitra;
    }

    public void setNamaMitra(String nama) {
        this.namaMitra = nama;
    }

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public int getPopulasi() {
        return populasi;
    }

    public void setPopulasi(int populasi) {
        this.populasi = populasi;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }
}
