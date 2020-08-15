package com.wonokoyo.rhk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "screen")
public class Screen implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int sequence;
    private int quantity;
    private double bbavg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBbavg() {
        return bbavg;
    }

    public void setBbavg(double bbavg) {
        this.bbavg = bbavg;
    }
}
