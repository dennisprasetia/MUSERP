package com.wonokoyo.doc.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "voadip_doc")
public class Voadip implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Doc.class, parentColumns = "id", childColumns = "id_doc")
    private int id_doc;

    @Ignore
    private Doc doc;

    @SerializedName("noOp")
    @Expose
    private String noOp;

    @SerializedName("supplier")
    @Expose
    private String supplier;

    @SerializedName("tglKirim")
    @Expose
    private String tglKirim;

    @Ignore
    @SerializedName("itemVoadip")
    @Expose
    private List<ItemVoadip> itemVoadips;

    public Voadip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_doc() {
        return id_doc;
    }

    public void setId_doc(int id_doc) {
        this.id_doc = id_doc;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public String getNoOp() {
        return noOp;
    }

    public void setNoOp(String noOp) {
        this.noOp = noOp;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTglKirim() {
        return tglKirim;
    }

    public void setTglKirim(String tglKirim) {
        this.tglKirim = tglKirim;
    }

    public List<ItemVoadip> getItemVoadips() {
        return itemVoadips;
    }

    public void setItemVoadips(List<ItemVoadip> itemVoadips) {
        this.itemVoadips = itemVoadips;
    }
}
