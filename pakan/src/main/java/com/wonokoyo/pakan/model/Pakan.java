package com.wonokoyo.pakan.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "pakan")
public class Pakan implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("no_sj")
    @Expose
    private String no_sj;

    @SerializedName("nomor")
    @Expose
    private int nomor;

    @SerializedName("berat")
    @Expose
    private double berat;

    @SerializedName("tgl_terima")
    @Expose
    private String tgl_terima;

    private int stat_upload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_sj() {
        return no_sj;
    }

    public void setNo_sj(String no_sj) {
        this.no_sj = no_sj;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public String getTgl_terima() {
        return tgl_terima;
    }

    public void setTgl_terima(String tgl_terima) {
        this.tgl_terima = tgl_terima;
    }

    public int getStat_upload() {
        return stat_upload;
    }

    public void setStat_upload(int stat_upload) {
        this.stat_upload = stat_upload;
    }
}
