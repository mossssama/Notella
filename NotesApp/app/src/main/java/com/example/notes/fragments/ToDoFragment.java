package com.example.notes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.NotesEditors.ToDoEditor;
import com.example.notes.R;
import com.example.notes.recyclerView.Adapter;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.recyclerView.RecyclerViewInterface;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToDo;


import java.util.ArrayList;

public class ToDoFragment extends Fragment implements RecyclerViewInterface{
    public ToDoFragment() {}

    public static RecyclerView toDoRecyclerView;
    public static ArrayList<NoteModel> toDoNotes;
    public static Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_to_buy, container, false);

        SINGLETON_SharedPrefToDo.init(getContext());

        toDoRecyclerView=view.findViewById(R.id.toBuy_rv);
        toDoRecyclerView.setHasFixedSize(true);
        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getActivity(), initDataFromSharedPrefs(), this);
        toDoRecyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<NoteModel> initDataFromSharedPrefs(){
        toDoNotes=new ArrayList<>();
        ArrayList<String>tempTitles=SINGLETON_SharedPrefToDo.readKeys();
        ArrayList<String>tempNotes=SINGLETON_SharedPrefToDo.readValues();
        for(int i=0;i<SINGLETON_SharedPrefToDo.getSize();i++){
            toDoNotes.add(new NoteModel(tempTitles.get(i),tempNotes.get(i)));
        }
        return toDoNotes;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ToDoEditor.class); //Intent used to link two acitvities
        intent.putExtra("ToDoNoteID",position);          //NoteId represent position of tabbed note
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        String deletedNoteKey=toDoNotes.get(position).getNoteTitle();
        toDoNotes.remove(position);
        adapter.notifyDataSetChanged();
        SINGLETON_SharedPrefToDo.remove(deletedNoteKey);
        toDoRecyclerView.setAdapter(adapter);
    }
}