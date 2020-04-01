package com.wonokoyo.doc.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "location")
public class Loc implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Doc.class, parentColumns = "noreg", childColumns = "noreg_doc")
    private String noreg_doc;

    @Ignore
    private Doc doc;

    @SerializedName("latAwal")
    @Expose
    private String latAwal;

    @SerializedName("lngAwal")
    @Expose
    private String lngAwal;

    @SerializedName("latAkhir")
    @Expose
    private String latAkhir;

    @SerializedName("lngAkhir")
    @Expose
    private String lngAkhir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoreg_doc() {
        return noreg_doc;
    }

    public void setNoreg_doc(String noreg_doc) {
        this.noreg_doc = noreg_doc;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public String getLatAwal() {
        return latAwal;
    }

    public void setLatAwal(String latAwal) {
        this.latAwal = latAwal;
    }

    public String getLngAwal() {
        return lngAwal;
    }

    public void setLngAwal(String lngAwal) {
        this.lngAwal = lngAwal;
    }

    public String getLatAkhir() {
        return latAkhir;
    }

    public void setLatAkhir(String latAkhir) {
        this.latAkhir = latAkhir;
    }

    public String getLngAkhir() {
        return lngAkhir;
    }

    public void setLngAkhir(String lngAkhir) {
        this.lngAkhir = lngAkhir;
    }
}
