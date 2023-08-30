package com.example.notesapp.UI.Fragments;

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
import com.example.notesapp.Modules.DetectSwipeGestureListener;
import com.example.notesapp.UI.Activities.NewNoteEditor;
import com.example.notesapp.RecyclerView.RecyclerViewAdapter;
import com.example.notesapp.POJO.NoteModel;
import com.example.notesapp.R;
import com.example.notesapp.RecyclerView.RecyclerViewItemClick;
import com.example.notesapp.Room.NotesDatabase;
import com.example.notesapp.databinding.FragmentToReadBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToReadFragment extends Fragment implements RecyclerViewItemClick {

    public static ArrayList<NoteModel> toReadList =new ArrayList<>();

    /* Instance to deal with Gesture Module */
    private GestureDetectorCompat gestureDetectorCompat=null;

    /* Instance to deal with Room */
    NotesDatabase notesRoom;

    public ToReadFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentToReadBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_read,container,false);

        /* Handling SwipeGesture */
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setToReadFragment(this);
        gestureDetectorCompat= new GestureDetectorCompat(this.requireActivity(),gestureListener);

        /* Instance to deal with Room */
        notesRoom = NotesDatabase.getInstance(getContext());

        notesRoom.notesDao().getFragmentNotes("ToRead")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NoteModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<NoteModel> notes) {
                        toReadList=ToArrayList.convert(notes);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),toReadList,ToReadFragment.this);
                        binding.toReadRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });

        binding.toReadRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.toReadRecyclerView.setOnTouchListener((View v, MotionEvent e) ->{
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
        editNote.putExtra("NoteTitle",toReadList.get(position).getNoteTitle());
        editNote.putExtra("NoteContent",toReadList.get(position).getNoteContent());
        editNote.putExtra("NoteFragment","ToRead");
        startActivity(editNote);
    }

    private void deleteNote(int position) {
        notesRoom.notesDao().deleteNote(toReadList.get(position).getNoteTitle())
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