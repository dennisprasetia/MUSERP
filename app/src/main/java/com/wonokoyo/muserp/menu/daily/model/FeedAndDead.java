package com.wonokoyo.muserp.menu.daily.model;

import java.io.Serializable;

public class FeedAndDead implements Serializable {
    private int receive;
    private int remain;
    private int death;
    private String description;

    public int getReceive() {
        return receive;
    }

    public void setReceive(int receive) {
        this.receive = receive;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
