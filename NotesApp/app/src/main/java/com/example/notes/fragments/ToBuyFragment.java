package com.example.notes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.NotesEditors.ToBuyEditor;
import com.example.notes.R;
import com.example.notes.recyclerView.Adapter;
import com.example.notes.recyclerView.NoteModel;
import com.example.notes.recyclerView.RecyclerViewInterface;
import com.example.notes.SharedPrefs;

import java.util.ArrayList;

public class ToBuyFragment extends Fragment implements RecyclerViewInterface {
    public ToBuyFragment() {}

    public static RecyclerView toBuyRecyclerView;
    public static ArrayList<NoteModel> toBuyNotes;
    public static Adapter adapter;
    public static SharedPrefs toBuySharedPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_to_buy, container, false);

        toBuySharedPrefs =new SharedPrefs(getContext(),"ToBuy");

        toBuyRecyclerView=view.findViewById(R.id.toBuy_rv);
        toBuyRecyclerView.setHasFixedSize(true);
        toBuyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(getActivity(), initDataFromSharedPrefs(), this);
        toBuyRecyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<NoteModel> initDataFromSharedPrefs(){
        toBuyNotes=new ArrayList<>();
        ArrayList<String>tempTitles= toBuySharedPrefs.readKeys();
        ArrayList<String>tempNotes= toBuySharedPrefs.readValues();
        for(int i = 0; i< toBuySharedPrefs.getSize(); i++){
            toBuyNotes.add(new NoteModel(tempTitles.get(i),tempNotes.get(i)));
        }
        return toBuyNotes;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getContext(), ToBuyEditor.class); //Intent used to link two acitvities
        intent.putExtra("ToBuyNoteID",position);          //NoteId represent position of tabbed note
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        String deletedNoteKey=toBuyNotes.get(position).getNoteTitle();
        toBuyNotes.remove(position);
        adapter.notifyDataSetChanged();
        toBuySharedPrefs.remove(deletedNoteKey);
        toBuyRecyclerView.setAdapter(adapter);
    }
}


