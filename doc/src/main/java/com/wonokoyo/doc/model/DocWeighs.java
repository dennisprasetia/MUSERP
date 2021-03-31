package com.wonokoyo.doc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocWeighs {
    @Embedded
    private Doc doc;

    @Relation(parentColumn = "id_spj", entityColumn = "id_spj", entity = Weigh.class)
    List<Weigh> weighs;

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public List<Weigh> getWeighs() {
        return weighs;
    }

    public void setWeighs(List<Weigh> weighs) {
        this.weighs = weighs;
    }
}
