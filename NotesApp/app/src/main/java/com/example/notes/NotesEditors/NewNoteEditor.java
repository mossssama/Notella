package com.example.notes.NotesEditors;

import static com.example.notes.MainActivity.*;
import static com.example.notes.fragments.ToBuyFragment.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.fragments.ToBuyFragment;
import com.example.notes.fragments.ToDoFragment;
import com.example.notes.fragments.ToSearchFragment;
import com.example.notes.fragments.ToWatchFragment;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToBuy;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToDo;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToSearch;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToWatch;

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
                    ToDoFragment.toDoNotes.add(SINGLETON_SharedPrefToDo.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    SINGLETON_SharedPrefToDo.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToDoFragment.toDoRecyclerView.setAdapter(adapter);
                    break;
                case MainActivity.TO_BUY_FRAGMENT:
                    ToBuyFragment.toBuyNotes.add(SINGLETON_SharedPrefToBuy.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    SINGLETON_SharedPrefToBuy.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToBuyFragment.toBuyRecyclerView.setAdapter(adapter);
                    break;
                case MainActivity.TO_SEARCH_FRAGMENT:
                    ToSearchFragment.toSearchNotes.add(SINGLETON_SharedPrefToSearch.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    SINGLETON_SharedPrefToSearch.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToSearchFragment.toSearchRecyclerView.setAdapter(adapter);
                default:
                    ToWatchFragment.toWatchNotes.add(SINGLETON_SharedPrefToWatch.getSize(), new NoteModel(newNoteTitle.getText() + "", newNoteContent.getText() + ""));
                    adapter.notifyDataSetChanged();
                    SINGLETON_SharedPrefToWatch.write(newNoteTitle.getText() + "", newNoteContent.getText() + "");
                    ToWatchFragment.toWatchRecyclerView.setAdapter(adapter);
            }
        });

    }
}
