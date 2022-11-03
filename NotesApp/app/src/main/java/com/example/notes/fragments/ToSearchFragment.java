package com.example.notes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.NotesEditors.ToSearchEditor;
import com.example.notes.R;
import com.example.notes.recyclerView.Adapter;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.recyclerView.RecyclerViewInterface;
import com.example.notes.sharedPrefs.SINGLETON_SharedPrefToSearch;

import java.util.ArrayList;

public class ToSearchFragment extends Fragment implements RecyclerViewInterface{
    public ToSearchFragment() {}

    public static RecyclerView toSearchRecyclerView;
    public static ArrayList<NoteModel> toSearchNotes;
    public static Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_to_search, container, false);

        SINGLETON_SharedPrefToSearch.init(getContext());

        toSearchRecyclerView=view.findViewById(R.id.toSearch_rv);
        toSearchRecyclerView.setHasFixedSize(true);
        toSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getActivity(), initDataFromSharedPrefs(), this);
        toSearchRecyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<NoteModel> initDataFromSharedPrefs(){
        toSearchNotes=new ArrayList<>();
        ArrayList<String>tempTitles= SINGLETON_SharedPrefToSearch.readKeys();
        ArrayList<String>tempNotes=SINGLETON_SharedPrefToSearch.readValues();
        for(int i=0;i<SINGLETON_SharedPrefToSearch.getSize();i++){
            toSearchNotes.add(new NoteModel(tempTitles.get(i),tempNotes.get(i)));
        }
        return toSearchNotes;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ToSearchEditor.class); //Intent used to link two acitvities
        intent.putExtra("ToSearchNoteID",position);          //NoteId represent position of tabbed note
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        String deletedNoteKey=toSearchNotes.get(position).getNoteTitle();
        toSearchNotes.remove(position);
        adapter.notifyDataSetChanged();
        SINGLETON_SharedPrefToSearch.remove(deletedNoteKey);
        toSearchRecyclerView.setAdapter(adapter);
    }
}