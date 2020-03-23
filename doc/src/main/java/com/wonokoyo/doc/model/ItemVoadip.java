package com.wonokoyo.doc.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "item_voadip")
public class ItemVoadip {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Voadip.class, parentColumns = "id", childColumns = "id_voadip")
    private int id_voadip;

    @Ignore
    private Voadip voadip;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("packing")
    @Expose
    private String packing;

    @SerializedName("ammount")
    @Expose
    private int ammount;

    public ItemVoadip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_voadip() {
        return id_voadip;
    }

    public void setId_voadip(int id_voadip) {
        this.id_voadip = id_voadip;
    }

    public Voadip getVoadip() {
        return voadip;
    }

    public void setVoadip(Voadip voadip) {
        this.voadip = voadip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }
}
