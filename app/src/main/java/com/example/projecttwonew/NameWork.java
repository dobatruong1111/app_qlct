package com.example.projecttwonew;

import java.io.Serializable;

public class NameWork implements Serializable {

    private String nameW;
    private boolean active;

    public NameWork(String nameW) {
        this.nameW = nameW;
        this.active = false;
    }

    public NameWork(String nameW, boolean active) {
        this.nameW = nameW;
        this.active = active;
    }

    public String getNameW() {
        return nameW;
    }

    public void setNameW(String nameW) {
        this.nameW = nameW;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.nameW;
    }
}

