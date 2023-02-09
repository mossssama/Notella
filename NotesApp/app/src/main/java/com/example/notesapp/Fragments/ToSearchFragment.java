package com.example.notesapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.Converters.ToArrayListConverter;
import com.example.notesapp.NewNoteEditor;
import com.example.notesapp.RecyclerView.RecyclerViewAdapter;
import com.example.notesapp.POJO.NoteModel;
import com.example.notesapp.R;
import com.example.notesapp.RecyclerView.RecyclerViewItemClick;
import com.example.notesapp.Room.NotesDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToSearchFragment extends Fragment implements RecyclerViewItemClick {

    public static ArrayList<NoteModel> toSearchList =new ArrayList<>();

    /* Instance to deal with Room */
    NotesDatabase notesRoom = NotesDatabase.getInstance(getContext());

    public ToSearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_search, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.toSearchRecyclerView);

        notesRoom.notesDao().getFragmentNotes("ToSearch")
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<List<NoteModel>>() {
                                @Override
                                public void onSubscribe(Disposable d) {}

                                @Override
                                public void onSuccess(List<NoteModel> notes) {
                                    toSearchList= ToArrayListConverter.toArrayList(notes);
                                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),toSearchList,ToSearchFragment.this);
                                    recyclerView.setAdapter(adapter);
                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       return view;
    }

    /* to edit a note */
    @Override
    public void onItemClick(int position) {
        Intent editNote = new Intent(getContext(), NewNoteEditor.class);
        editNote.putExtra("NoteTitle",toSearchList.get(position).getNoteTitle());
        editNote.putExtra("NoteContent",toSearchList.get(position).getNoteDescription());
        editNote.putExtra("NoteFragment","ToSearch");
        startActivity(editNote);
    }

    /* to delete a note */
    @Override
    public void onItemLongClick(int position) {
        notesRoom.notesDao().deleteNote(toSearchList.get(position).getNoteTitle())
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}