package com.example.notes.NotesEditors;

import static com.example.notes.fragments.ToBuyFragment.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.fragments.ToBuyFragment;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToBuy;


public class ToBuyEditor extends AppCompatActivity {
    int noteID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_to_buy);

        EditText toBuyTitle=findViewById(R.id.toBuy_note_title);
        EditText toBuyContent=findViewById(R.id.toBuy_note_content);
        Button toBuyConfirmEdit=findViewById(R.id.toBuy_confirm_edit);

        Intent intent=getIntent();                      //receive the intent to be allow to connect the two activites
        noteID=intent.getIntExtra("ToBuyNoteID",-1);                 //if any error occured while sending NoteID; -1 will be sent instead
        if(noteID!=-1){
            toBuyTitle.setText(ToBuyFragment.toBuyNotes.get(noteID).getNoteTitle());
            toBuyContent.setText(ToBuyFragment.toBuyNotes.get(noteID).getNoteContent());
        }    //load the editText with the contents of the selected Note from ListView
        else{
            NoteModel temp=new NoteModel("","");
            ToBuyFragment.toBuyNotes.add(temp);   noteID=ToBuyFragment.toBuyNotes.size()-1; adapter.notifyDataSetChanged();
        } //empty current ArrayList location; dec noteID

        toBuyConfirmEdit.setOnClickListener((View view)->{

                ToBuyFragment.toBuyNotes.set(noteID,new NoteModel(toBuyTitle.getText()+"",toBuyContent.getText()+""));
                adapter.notifyDataSetChanged();
                SINGLETON_SharedPrefToBuy.remove(ToBuyFragment.toBuyNotes.get(noteID).getNoteTitle());
                SINGLETON_SharedPrefToBuy.write(toBuyTitle.getText()+"",toBuyContent.getText()+"");
                ToBuyFragment.toBuyRecyclerView.setAdapter(adapter);

        });

    }
}
