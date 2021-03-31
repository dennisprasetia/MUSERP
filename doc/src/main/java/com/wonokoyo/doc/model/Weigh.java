package com.wonokoyo.doc.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "weigh")
public class Weigh {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;

    @ForeignKey(entity = Doc.class, parentColumns = "id_spj", childColumns = "id_spj")
    private String id_spj;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    @SerializedName("nomor")
    @Expose
    private int nomor;

    @SerializedName("berat")
    @Expose
    private double berat;

    @SerializedName("jmlBox")
    @Expose
    private int jmlBox;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
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

    public void setId_spj(String id_spj) {
        this.id_spj = id_spj;
    }

    public String getId_spj() {
        return id_spj;
    }

    public int getJmlBox() {
        return jmlBox;
    }

    public void setJmlBox(int jmlBox) {
        this.jmlBox = jmlBox;
    }
}
