package com.wonokoyo.doc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VoadipDetail {
    @Embedded
    private Voadip voadip;

    @Relation(parentColumn = "id", entityColumn = "id_voadip", entity = ItemVoadip.class)
    private List<ItemVoadip> itemVoadips;

    public Voadip getVoadip() {
        return voadip;
    }

    public void setVoadip(Voadip voadip) {
        this.voadip = voadip;
    }

    public List<ItemVoadip> getItemVoadips() {
        return itemVoadips;
    }

    public void setItemVoadips(List<ItemVoadip> itemVoadips) {
        this.itemVoadips = itemVoadips;
    }
}
