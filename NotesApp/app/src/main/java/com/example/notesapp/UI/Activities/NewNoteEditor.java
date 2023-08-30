package com.example.notesapp.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.notesapp.R;
import com.example.notesapp.Room.Note;
import com.example.notesapp.Room.NotesDatabase;
import com.example.notesapp.databinding.EditorNewNoteBinding;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewNoteEditor extends AppCompatActivity {

    String currentFragmentName="";

    /* Instance to deal with Room */
    NotesDatabase notesRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditorNewNoteBinding binding= DataBindingUtil.setContentView(this,R.layout.editor_new_note);

        notesRoom = NotesDatabase.getInstance(this);                               /* Instance to deal with Room */

        /* to edit a note */
        Intent editNote=getIntent();
        String noteTitle = editNote.getStringExtra("NoteTitle");
        String noteContent = editNote.getStringExtra("NoteContent");
        currentFragmentName=editNote.getStringExtra("NoteFragment");

        binding.newNoteTitle.setText(noteTitle);
        binding.newNoteContent.setText(noteContent);

        updateCurrentFragmentName(MainActivity.CURRENT_FRAGMENT_ID);                                         /* Getting currentFragment Name */

        /* Adding new note or Editing old note */
        binding.newNoteConfirm.setOnClickListener((View view)-> {
            deleteNote(noteTitle);                                  /* Delete old note in case of editing old note */
            saveNote(binding.newNoteTitle.getText(),binding.newNoteContent.getText(),currentFragmentName);                    /* Save latest version of current note*/

            Intent returnToFragment=new Intent(this, MainActivity.class);
            returnToFragment.putExtra("fragment",MainActivity.CURRENT_FRAGMENT_ID);
            startActivity(returnToFragment);
        });

    }

    private void updateCurrentFragmentName(int currentFragmentId) {
        switch(currentFragmentId){
            case R.id.toDo:     currentFragmentName="ToDo";     break;
            case R.id.toBuy:    currentFragmentName="ToBuy";    break;
            case R.id.toRead:   currentFragmentName="ToRead";   break;
            case R.id.toSearch: currentFragmentName="ToSearch"; break;
            case R.id.toWatch:  currentFragmentName="ToWatch";  break;
        }
    }

    private void saveNote(Editable noteTitle, Editable noteContent, String fragmentName) {
        notesRoom.notesDao().insertNote(new Note(noteTitle.toString(),noteContent.toString(),fragmentName))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(), R.string.NoteSaved, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {}
                });
    }

    private void deleteNote(String noteTitle) {
        notesRoom.notesDao().deleteNote(noteTitle)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {}
                });
    }

}
