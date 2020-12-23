package com.example.projecttwonew;

public class ListTable {

    public String nameTable;
    private int iconTable;

    public ListTable() {
    }

    public ListTable(String nameTable, int iconTable) {
        this.nameTable = nameTable;
        this.iconTable = iconTable;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public int getIconTable() {
        return iconTable;
    }

    public void setIconTable(int iconTable) {
        this.iconTable = iconTable;
    }
}
