package com.wonokoyo.muserp.menu.daily.model;

import java.io.Serializable;

public class Attachment implements Serializable {
    private int id;
    private String type;
    private String fileuri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileuri() {
        return fileuri;
    }

    public void setFileuri(String fileuri) {
        this.fileuri = fileuri;
    }
}
