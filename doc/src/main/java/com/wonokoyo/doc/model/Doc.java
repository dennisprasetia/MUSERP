package com.wonokoyo.doc.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "doc")
public class Doc implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("noOpDoc")
    @Expose
    private String noOpDoc;

    @SerializedName("tanggalDoc")
    @Expose
    private String tanggalDoc;

    @SerializedName("mitra")
    @Expose
    private String mitra;

    @SerializedName("noreg")
    @Expose
    private String noreg;

    @SerializedName("kandang")
    @Expose
    private int kandang;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("populasi")
    @Expose
    private int populasi;

    @SerializedName("jumlahBox")
    @Expose
    private int jumlahBox;

    @SerializedName("nopol")
    @Expose
    private String nopol;

    @SerializedName("sopir")
    @Expose
    private String sopir;

    @SerializedName("kedatangan")
    @Expose
    private String kedatangan;

    @SerializedName("keKandang")
    @Expose
    private String keKandang;

    @SerializedName("checkIn")
    @Expose
    private String checkIn;

    @SerializedName("penerimaan")
    @Expose
    private String penerimaan;

    @SerializedName("noSj")
    @Expose
    private String noSj;

    @SerializedName("jenis")
    @Expose
    private String jenis;

    @SerializedName("terimaBox")
    @Expose
    private int terimaBox;

    @SerializedName("ekorMati")
    @Expose
    private int mati;

    @SerializedName("ekorTerima")
    @Expose
    private int ekorTerima;

    @SerializedName("bbRata")
    @Expose
    private double bbRata;

    @SerializedName("taraBox")
    @Expose
    private double taraBox;

    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("urlSign")
    @Expose
    private String urlSign;

    @Ignore
    @SerializedName("voadips")
    @Expose
    private List<Voadip> voadips;

    @Ignore
    @SerializedName("location")
    @Expose
    private Loc loc;

    private int stat_track;

    private int stat_entry;

    public Doc() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoOpDoc() {
        return noOpDoc;
    }

    public void setNoOpDoc(String noOpDoc) {
        this.noOpDoc = noOpDoc;
    }

    public String getTanggalDoc() {
        return tanggalDoc;
    }

    public void setTanggalDoc(String tanggalDoc) {
        this.tanggalDoc = tanggalDoc;
    }

    public String getNoSj() {
        return noSj;
    }

    public void setNoSj(String noSj) {
        this.noSj = noSj;
    }

    public String getMitra() {
        return mitra;
    }

    public void setMitra(String mitra) {
        this.mitra = mitra;
    }

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public int getKandang() {
        return kandang;
    }

    public void setKandang(int kandang) {
        this.kandang = kandang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getPopulasi() {
        return populasi;
    }

    public void setPopulasi(int populasi) {
        this.populasi = populasi;
    }

    public int getJumlahBox() {
        return jumlahBox;
    }

    public void setJumlahBox(int jumlahBox) {
        this.jumlahBox = jumlahBox;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getSopir() {
        return sopir;
    }

    public void setSopir(String sopir) {
        this.sopir = sopir;
    }

    public String getKedatangan() {
        return kedatangan;
    }

    public void setKedatangan(String kedatangan) {
        this.kedatangan = kedatangan;
    }

    public String getKeKandang() {
        return keKandang;
    }

    public void setKeKandang(String keKandang) {
        this.keKandang = keKandang;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getPenerimaan() {
        return penerimaan;
    }

    public void setPenerimaan(String penerimaan) {
        this.penerimaan = penerimaan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getTerimaBox() {
        return terimaBox;
    }

    public void setTerimaBox(int terimaBox) {
        this.terimaBox = terimaBox;
    }

    public int getMati() {
        return mati;
    }

    public void setMati(int mati) {
        this.mati = mati;
    }

    public int getEkorTerima() {
        return ekorTerima;
    }

    public void setEkorTerima(int ekorTerima) {
        this.ekorTerima = ekorTerima;
    }

    public double getBbRata() {
        return bbRata;
    }

    public void setBbRata(double bbRata) {
        this.bbRata = bbRata;
    }

    public double getTaraBox() {
        return taraBox;
    }

    public void setTaraBox(double taraBox) {
        this.taraBox = taraBox;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public List<Voadip> getVoadips() {
        return voadips;
    }

    public void setVoadips(List<Voadip> voadips) {
        this.voadips = voadips;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public int getStat_track() {
        return stat_track;
    }

    public void setStat_track(int stat_track) {
        this.stat_track = stat_track;
    }

    public int getStat_entry() {
        return stat_entry;
    }

    public void setStat_entry(int stat_entry) {
        this.stat_entry = stat_entry;
    }
}
