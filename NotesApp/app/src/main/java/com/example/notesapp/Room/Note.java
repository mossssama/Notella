package com.example.notesapp.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notesTable")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String noteTitle;
    private String noteContent;
    private String noteFragment;

    public Note(String noteTitle, String noteContent, String noteFragment) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteFragment = noteFragment;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNoteTitle() {
        return noteTitle;
    }
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }
    public String getNoteContent() {
        return noteContent;
    }
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
    public String getNoteFragment() {
        return noteFragment;
    }
    public void setNoteFragment(String noteFragment) {
        this.noteFragment = noteFragment;
    }
}
