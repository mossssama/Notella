package com.example.notesapp.POJO;

public class NoteModel {
    String noteTitle;
    String noteContent;

    public NoteModel(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public String getNoteTitle() {
        return noteTitle;
    }
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {return noteContent;}
    public void setNoteContent(String noteContent) {this.noteContent = noteContent;}
}
