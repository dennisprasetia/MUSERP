package com.wonokoyo.muserp.menu.daily.model;

import java.io.Serializable;

public class Screen implements Serializable {
    private int sequence;
    private int quantity;
    private double bbavg;

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
