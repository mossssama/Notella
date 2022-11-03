package com.example.notes.NotesEditors;

import static com.example.notes.fragments.ToSearchFragment.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.fragments.ToSearchFragment;
import com.example.notes.fragments.ToWatchFragment;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToSearch;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToWatch;

public class ToWatchEditor extends AppCompatActivity {

    int noteID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_to_watch);

        EditText toWatchTitle=findViewById(R.id.toWatch_note_title);
        EditText toWatchContent=findViewById(R.id.toWatch_note_content);
        Button toWatchConfirmEdit=findViewById(R.id.toWatch_confirm_edit);

        Intent intent=getIntent();                      //receive the intent to be allow to connect the two activites
        noteID=intent.getIntExtra("ToWatchNoteID",-1);                 //if any error occured while sending NoteID; -1 will be sent instead
        if(noteID!=-1){
            toWatchTitle.setText(ToWatchFragment.toWatchNotes.get(noteID).getNoteTitle());
            toWatchContent.setText(ToWatchFragment.toWatchNotes.get(noteID).getNoteContent());
        }    //load the editText with the contents of the selected Note from ListView
        else{
            NoteModel temp=new NoteModel("","");
            ToWatchFragment.toWatchNotes.add(temp);   noteID=ToWatchFragment.toWatchNotes.size()-1; ToWatchFragment.adapter.notifyDataSetChanged();
        } //empty current ArrayList location; dec noteID

        toWatchConfirmEdit.setOnClickListener((View view)->{

            ToWatchFragment.toWatchNotes.set(noteID,new NoteModel(toWatchTitle.getText()+"",toWatchContent.getText()+""));
            adapter.notifyDataSetChanged();
            SINGLETON_SharedPrefToWatch.remove(ToWatchFragment.toWatchNotes.get(noteID).getNoteTitle());
            SINGLETON_SharedPrefToWatch.write(toWatchTitle.getText()+"",toWatchContent.getText()+"");
            ToWatchFragment.toWatchRecyclerView.setAdapter(adapter);

        });
    }

}
