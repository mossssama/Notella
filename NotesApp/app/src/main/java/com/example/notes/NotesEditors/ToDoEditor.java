package com.example.notes.NotesEditors;

import static com.example.notes.fragments.ToDoFragment.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notes.R;
import com.example.notes.fragments.ToDoFragment;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToDo;

public class ToDoEditor extends AppCompatActivity {
    int noteID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_to_do);

        EditText toDoTitle=findViewById(R.id.toDo_note_title);
        EditText toDoContent=findViewById(R.id.toDo_note_content);
        Button toDoConfirmEdit=findViewById(R.id.toDo_confirm_edit);

        Intent intent=getIntent();                      //receive the intent to be allow to connect the two activites
        noteID=intent.getIntExtra("ToDoNoteID",-1);                 //if any error occured while sending NoteID; -1 will be sent instead
        if(noteID!=-1){
            toDoTitle.setText(ToDoFragment.toDoNotes.get(noteID).getNoteTitle());
            toDoContent.setText(ToDoFragment.toDoNotes.get(noteID).getNoteContent());
        }    //load the editText with the contents of the selected Note from ListView
        else{
            NoteModel temp=new NoteModel("","");
            ToDoFragment.toDoNotes.add(temp);   noteID=ToDoFragment.toDoNotes.size()-1; ToDoFragment.adapter.notifyDataSetChanged();
        } //empty current ArrayList location; dec noteID

        toDoConfirmEdit.setOnClickListener((View view)-> {

            ToDoFragment.toDoNotes.set(noteID,new NoteModel(toDoTitle.getText()+"",toDoContent.getText()+""));
            adapter.notifyDataSetChanged();
            SINGLETON_SharedPrefToDo.remove(ToDoFragment.toDoNotes.get(noteID).getNoteTitle());
            SINGLETON_SharedPrefToDo.write(toDoTitle.getText()+"",toDoContent.getText()+"");
            ToDoFragment.toDoRecyclerView.setAdapter(adapter);

        });

    }

}