package com.example.notesapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notesapp.POJO.NoteModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface NotesDao {

@Insert
Completable insertNote(Note note);

@Query("DELETE FROM notesTable WHERE noteTitle=:noteTitle")
Completable deleteNote(String noteTitle);

@Query("SELECT noteTitle,noteContent FROM notesTable WHERE noteSection=:fragmentName ORDER BY noteTitle")
Single<List<NoteModel>> getFragmentNotes(String fragmentName);

@Query("SELECT COUNT(*) FROM notesTable WHERE noteSection=:fragmentName")
Single<Integer> getFragmentNumberNotes(String fragmentName);

@Query("SELECT noteTitle,noteContent FROM notesTable ORDER BY noteTitle")
Single<List<NoteModel>> getAllNotes();

@Query("SELECT COUNT(*) FROM notesTable")
Single<Integer> getTotalNumberNotes();


@Query("DELETE FROM notesTable")
Completable clearRoom();




//@Delete
//Completable deleteNote(Note note);

@Query("DELETE FROM notesTable")
void deleteNodes();

@Query("SELECT * FROM notesTable WHERE noteTitle=:title")
Note getNote(String title);

}
