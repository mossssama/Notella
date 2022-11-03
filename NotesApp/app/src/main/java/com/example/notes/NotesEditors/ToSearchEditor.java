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
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToSearch;

public class ToSearchEditor extends AppCompatActivity {
    int noteID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_to_search);

        EditText toSearchTitle=findViewById(R.id.toSearch_note_title);
        EditText toSearchContent=findViewById(R.id.toSearch_note_content);
        Button toSearchConfirmEdit=findViewById(R.id.toSearch_confirm_edit);

        Intent intent=getIntent();                      //receive the intent to be allow to connect the two activites
        noteID=intent.getIntExtra("ToSearchNoteID",-1);                 //if any error occured while sending NoteID; -1 will be sent instead
        if(noteID!=-1){
            toSearchTitle.setText(ToSearchFragment.toSearchNotes.get(noteID).getNoteTitle());
            toSearchContent.setText(ToSearchFragment.toSearchNotes.get(noteID).getNoteContent());
        }    //load the editText with the contents of the selected Note from ListView
        else{
            NoteModel temp=new NoteModel("","");
            ToSearchFragment.toSearchNotes.add(temp);   noteID=ToSearchFragment.toSearchNotes.size()-1; ToSearchFragment.adapter.notifyDataSetChanged();
        } //empty current ArrayList location; dec noteID

        toSearchConfirmEdit.setOnClickListener((View view)->{

            ToSearchFragment.toSearchNotes.set(noteID,new NoteModel(toSearchTitle.getText()+"",toSearchContent.getText()+""));
            adapter.notifyDataSetChanged();
            SINGLETON_SharedPrefToSearch.remove(ToSearchFragment.toSearchNotes.get(noteID).getNoteTitle());
            SINGLETON_SharedPrefToSearch.write(toSearchTitle.getText()+"",toSearchContent.getText()+"");
            ToSearchFragment.toSearchRecyclerView.setAdapter(adapter);

        });
    }

}
