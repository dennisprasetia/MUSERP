package com.wonokoyo.voadip.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VoadipWithItem {

    public VoadipWithItem() {
    }

    @Embedded
    private Voadip voadip;

    @Relation(parentColumn = "id", entityColumn = "id_voadip", entity = ItemVoadip.class)
    private List<ItemVoadip> voadipItems;

    public Voadip getVoadip() {
        return voadip;
    }

    public void setVoadip(Voadip voadip) {
        this.voadip = voadip;
    }

    public List<ItemVoadip> getVoadipItems() {
        return voadipItems;
    }

    public void setVoadipItems(List<ItemVoadip> voadipItems) {
        this.voadipItems = voadipItems;
    }
}
