package com.wonokoyo.voadip.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "voadip")
public class Voadip implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("mitra")
    @Expose
    private String mitra;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("noOp")
    @Expose
    private String noOp;

    @SerializedName("noreg")
    @Expose
    private String noreg;

    @SerializedName("populasi")
    @Expose
    private String populasi;

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

    @SerializedName("noSj")
    @Expose
    private String noSj;

    @SerializedName("penerima")
    @Expose
    private String penerima;

    @SerializedName("tglTerima")
    @Expose
    private String tglTerima;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("urlSign")
    @Expose
    private String urlSign;

    @SerializedName("upload")
    @Expose
    private int upload;

    public Voadip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMitra() {
        return mitra;
    }

    public void setMitra(String mitra) {
        this.mitra = mitra;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoOp() {
        return noOp;
    }

    public void setNoOp(String noOp) {
        this.noOp = noOp;
    }

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public String getPopulasi() {
        return populasi;
    }

    public void setPopulasi(String populasi) {
        this.populasi = populasi;
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

    public String getNoSj() {
        return noSj;
    }

    public void setNoSj(String noSj) {
        this.noSj = noSj;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getTglTerima() {
        return tglTerima;
    }

    public void setTglTerima(String tglTerima) {
        this.tglTerima = tglTerima;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlSign() {
        return urlSign;
    }

    public void setUrlSign(String urlSign) {
        this.urlSign = urlSign;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }
}
