package com.wonokoyo.doc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DocWithLoc {
    @Embedded
    private Doc doc;

    @Relation(parentColumn = "id", entityColumn = "id_doc", entity = Loc.class)
    Loc loc;

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }
}
