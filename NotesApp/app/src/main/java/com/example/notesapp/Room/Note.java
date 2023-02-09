package com.example.notesapp.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "notesTable")
public class Note {

    @PrimaryKey(autoGenerate = false)
    @NotNull
    private String noteTitle;
    private String noteContent;
    private String noteSection;

    public Note(String noteTitle, String noteContent, String noteSection) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteSection = noteSection;
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
    public String getNoteSection() {
        return noteSection;
    }
    public void setNoteSection(String noteSection) {
        this.noteSection = noteSection;
    }
}
