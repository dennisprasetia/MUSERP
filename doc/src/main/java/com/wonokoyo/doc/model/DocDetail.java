package com.wonokoyo.doc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocDetail {
    @Embedded
    private Doc doc;

    @Relation(parentColumn = "noreg", entityColumn = "noreg_doc", entity = Voadip.class)
    List<VoadipDetail> voadips;

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public List<VoadipDetail> getVoadips() {
        return voadips;
    }

    public void setVoadips(List<VoadipDetail> voadips) {
        this.voadips = voadips;
    }
}
