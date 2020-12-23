package com.example.projecttwonew.object;

public class Cards {

    private String nameC;
    private String noteC;
    private String timeC;

    public Cards() {
    }

    public Cards(String nameC, String noteC, String timeC) {
        this.nameC = nameC;
        this.noteC = noteC;
        this.timeC = timeC;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getNoteC() {
        return noteC;
    }

    public void setNoteC(String noteC) {
        this.noteC = noteC;
    }

    public String getTimeC() {
        return timeC;
    }

    public void setTimeC(String timeC) {
        this.timeC = timeC;
    }
}
