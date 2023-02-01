package com.example.notesapp.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface NotesDao {

@Insert
Completable insertNote(Note note);

@Query("SELECT * FROM notesTable WHERE noteFragment=:noteFragmentName")
Single<List<Note>> getNotes(String noteFragmentName);


}
