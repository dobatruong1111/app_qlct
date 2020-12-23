package com.example.projecttwonew;

public class Card {
    private int iconCard;
    private String nameCard;
    private String noteCard;
    private String time;

    public Card() {
    }

    public Card(int iconCard, String nameCard, String noteCard, String time) {
        this.iconCard = iconCard;
        this.nameCard = nameCard;
        this.noteCard = noteCard;
        this.time = time;
    }

    public int getIconCard() {
        return iconCard;
    }

    public void setIconCard(int iconCard) {
        this.iconCard = iconCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNoteCard() {
        return noteCard;
    }

    public void setNoteCard(String noteCard) {
        this.noteCard = noteCard;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
