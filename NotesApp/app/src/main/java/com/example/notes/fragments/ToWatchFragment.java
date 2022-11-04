package com.example.notes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.NotesEditors.ToWatchEditor;
import com.example.notes.R;
import com.example.notes.recyclerView.Adapter;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.recyclerView.RecyclerViewInterface;
import com.example.notes.SharedPrefs;

import java.util.ArrayList;

public class ToWatchFragment extends Fragment implements RecyclerViewInterface {
    public ToWatchFragment() {}

    public static RecyclerView toWatchRecyclerView;
    public static ArrayList<NoteModel> toWatchNotes;
    public static Adapter adapter;
    public static SharedPrefs toWatchSharedPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_to_watch, container, false);

        toWatchSharedPrefs =new SharedPrefs(getContext(),"ToWatch");

        toWatchRecyclerView=view.findViewById(R.id.toWatch_rv);
        toWatchRecyclerView.setHasFixedSize(true);
        toWatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getActivity(), initDataFromSharedPrefs(), this);
        toWatchRecyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<NoteModel> initDataFromSharedPrefs(){
        toWatchNotes=new ArrayList<>();
        ArrayList<String>tempTitles= toWatchSharedPrefs.readKeys();
        ArrayList<String>tempNotes=toWatchSharedPrefs.readValues();
        for(int i=0;i<toWatchSharedPrefs.getSize();i++){
            toWatchNotes.add(new NoteModel(tempTitles.get(i),tempNotes.get(i)));
        }
        return toWatchNotes;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ToWatchEditor.class); //Intent used to link two acitvities
        intent.putExtra("ToWatchNoteID",position);          //NoteId represent position of tabbed note
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        String deletedNoteKey=toWatchNotes.get(position).getNoteTitle();
        toWatchNotes.remove(position);
        adapter.notifyDataSetChanged();
        toWatchSharedPrefs.remove(deletedNoteKey);
        toWatchRecyclerView.setAdapter(adapter);
    }
}
