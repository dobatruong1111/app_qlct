package com.example.projecttwonew.object;

import java.util.HashMap;

public class Table {

    private String nameTable;
    //private HashMap<String,Cards> hashMap = new HashMap<>();

    public Table() {
    }

    public Table(String nameTable) {
        this.nameTable = nameTable;
    }

//    public Table(HashMap<String, Cards> hashMap) {
//        this.hashMap = hashMap;
//    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

//    public HashMap<String, Cards> getHashMap() {
//        return hashMap;
//    }
//
//    public void setHashMap(HashMap<String, Cards> hashMap) {
//        this.hashMap = hashMap;
//    }
}
