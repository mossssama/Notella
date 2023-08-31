package com.example.notesapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.Converters.ToArrayList;
import com.example.notesapp.gestures.DetectSwipeGestureListener;
import com.example.notesapp.ui.activities.NewNoteEditor;
import com.example.notesapp.recyclerView.RecyclerViewAdapter;
import com.example.notesapp.pojo.NoteModel;
import com.example.notesapp.R;
import com.example.notesapp.recyclerView.RecyclerViewItemClick;
import com.example.notesapp.room.NotesDatabase;
import com.example.notesapp.databinding.FragmentToBuyBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToBuyFragment extends Fragment implements RecyclerViewItemClick {

    public static ArrayList<NoteModel> toBuyList =new ArrayList<>();

    /* Instance to deal with Gesture Module */
    private GestureDetectorCompat gestureDetectorCompat=null;

    /* Instance to deal with Room */
    NotesDatabase notesRoom;

    public ToBuyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentToBuyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_buy, container, false);

        /* Handling SwipeGesture */
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setToBuyFragment(this);
        gestureDetectorCompat= new GestureDetectorCompat(this.requireActivity(),gestureListener);

        /* Instance to deal with Room */
        notesRoom = NotesDatabase.getInstance(getContext());

        notesRoom.notesDao().getFragmentNotes("ToBuy")
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<NoteModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {}

                            @Override
                            public void onNext(List<NoteModel> notes) {
                                toBuyList=ToArrayList.convert(notes);
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),ToArrayList.convert(notes),ToBuyFragment.this);
                                binding.toBuyRecyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onError(Throwable e) {}

                            @Override
                            public void onComplete() {}
                        });


        binding.toBuyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.toBuyRecyclerView.setOnTouchListener((View v, MotionEvent e) ->{
                gestureDetectorCompat.onTouchEvent(e);
                return false;
        });

        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        editNote(position);
    }

    @Override
    public void onItemLongClick(int position) {
        deleteNote(position);
    }

    public void onSwipe(){
        addNote();
    }

    private void editNote(int position) {
        Intent editNote = new Intent(getContext(), NewNoteEditor.class);
        editNote.putExtra("NoteTitle",toBuyList.get(position).getNoteTitle());
        editNote.putExtra("NoteContent",toBuyList.get(position).getNoteContent());
        editNote.putExtra("NoteFragment","ToBuy");
        startActivity(editNote);
    }

    private void deleteNote(int position) {
        notesRoom.notesDao().deleteNote(toBuyList.get(position).getNoteTitle())
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), R.string.NoteDeleted, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {}
                });
    }

    private void addNote() {
        Intent addNewNote = new Intent(getContext(),NewNoteEditor.class);
        startActivity(addNewNote);
    }



}