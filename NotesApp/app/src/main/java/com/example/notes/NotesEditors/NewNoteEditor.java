package com.example.notes.NotesEditors;

import static com.example.notes.MainActivity.*;
import static com.example.notes.fragments.ToBuyFragment.*;
import static com.example.notes.fragments.ToDoFragment.toDoSharedPrefs;
import static com.example.notes.fragments.ToSearchFragment.toSearchSharedPrefs;
import static com.example.notes.fragments.ToWatchFragment.toWatchSharedPrefs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.fragments.*;
import com.example.notes.recyclerView.NoteModel;

public class NewNoteEditor extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_new_note);

        Intent intent=getIntent();                      //receive the intent to be allow to connect the two activites

        EditText newNoteTitle=findViewById(R.id.new_note_title);
        EditText newNoteContent=findViewById(R.id.new_note_content);
        Button newNoteConfirm=findViewById(R.id.new_note_confirm);

        newNoteConfirm.setOnClickListener((View view)->{
            switch(currentFragment) {
                case MainActivity.TO_DO_FRAGMENT:
                    ToDoFragment.toDoNotes.add(toDoSharedPrefs.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    toDoSharedPrefs.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToDoFragment.toDoRecyclerView.setAdapter(adapter);
                    break;
                case MainActivity.TO_BUY_FRAGMENT:
                    ToBuyFragment.toBuyNotes.add(toBuySharedPrefs.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    toBuySharedPrefs.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToBuyFragment.toBuyRecyclerView.setAdapter(adapter);
                    break;
                case MainActivity.TO_SEARCH_FRAGMENT:
                    ToSearchFragment.toSearchNotes.add(toSearchSharedPrefs.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    toSearchSharedPrefs.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToSearchFragment.toSearchRecyclerView.setAdapter(adapter);
                    break;
                default:
                    ToWatchFragment.toWatchNotes.add(toWatchSharedPrefs.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    toWatchSharedPrefs.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToWatchFragment.toWatchRecyclerView.setAdapter(adapter);
            }
        });

    }
}
