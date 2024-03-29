package com.example.notesapp.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notesapp.pojo.NoteModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface NotesDao {

@Insert
Completable insertNote(Note note);

@Query("DELETE FROM notesTable WHERE noteTitle=:noteTitle")
Completable deleteNote(String noteTitle);

@Query("SELECT noteTitle,noteContent FROM notesTable WHERE noteSection=:fragmentName ORDER BY noteTitle")
Observable<List<NoteModel>> getFragmentNotes(String fragmentName);



@Query("SELECT COUNT(*) FROM notesTable WHERE noteSection=:fragmentName")
Flowable<Integer> getFragmentNumberNotes(String fragmentName);

@Query("SELECT noteTitle,noteContent FROM notesTable ORDER BY noteTitle")
Flowable<List<NoteModel>> getAllNotes();

@Query("SELECT COUNT(*) FROM notesTable")
Flowable<Integer> getTotalNumberNotes();

@Query("DELETE FROM notesTable")
Completable clearRoom();

@Query("DELETE FROM notesTable")
void deleteNodes();

@Query("SELECT * FROM notesTable WHERE noteTitle=:title")
Note getNote(String title);

}
