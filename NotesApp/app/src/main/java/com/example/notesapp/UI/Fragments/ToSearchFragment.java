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
import com.example.notesapp.recyclerView.RecyclerViewItemClick;
import com.example.notesapp.ui.activities.NewNoteEditor;
import com.example.notesapp.recyclerView.RecyclerViewAdapter;
import com.example.notesapp.pojo.NoteModel;
import com.example.notesapp.R;
import com.example.notesapp.room.NotesDatabase;
import com.example.notesapp.databinding.FragmentToSearchBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToSearchFragment extends Fragment implements RecyclerViewItemClick {

    public static ArrayList<NoteModel> toSearchList =new ArrayList<>();

    /* Instance to deal with Gesture Module */
    private GestureDetectorCompat gestureDetectorCompat=null;

    /* Instance to deal with Room */
    NotesDatabase notesRoom;

    public ToSearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentToSearchBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_search,container,false);

        /* Handling SwipeGesture */
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setToSearchFragment(this);
        gestureDetectorCompat= new GestureDetectorCompat(this.requireActivity(),gestureListener);

        /* Instance to deal with Room */
        notesRoom = NotesDatabase.getInstance(getContext());

        notesRoom.notesDao().getFragmentNotes("ToSearch")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NoteModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<NoteModel> notes) {
                        toSearchList=ToArrayList.convert(notes);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),toSearchList,ToSearchFragment.this);
                        binding.toSearchRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });

        binding.toSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.toSearchRecyclerView.setOnTouchListener((View v, MotionEvent e) ->{
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
        editNote.putExtra("NoteTitle",toSearchList.get(position).getNoteTitle());
        editNote.putExtra("NoteContent",toSearchList.get(position).getNoteContent());
        editNote.putExtra("NoteFragment","ToSearch");
        startActivity(editNote);
    }

    private void deleteNote(int position) {
        notesRoom.notesDao().deleteNote(toSearchList.get(position).getNoteTitle())
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