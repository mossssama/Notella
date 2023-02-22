package com.example.notesapp.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.Converters.ToArrayListConverter;
import com.example.notesapp.Modules.DetectSwipeGestureListener;
import com.example.notesapp.UI.Activities.NewNoteEditor;
import com.example.notesapp.RecyclerView.RecyclerViewAdapter;
import com.example.notesapp.POJO.NoteModel;
import com.example.notesapp.R;
import com.example.notesapp.RecyclerView.RecyclerViewItemClick;
import com.example.notesapp.Room.NotesDatabase;
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
    NotesDatabase notesRoom = NotesDatabase.getInstance(getContext());

    public ToBuyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentToBuyBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_to_buy,container,false);
        View rootView = binding.getRoot();

        /* Handling SwipeGesture */
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setToBuyFragment(this);
        gestureDetectorCompat= new GestureDetectorCompat(this.getActivity(),gestureListener);

        notesRoom.notesDao().getFragmentNotes("ToBuy")
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<NoteModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {}

                            @Override
                            public void onNext(List<NoteModel> notes) {
                                toBuyList= ToArrayListConverter.toArrayList(notes);
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),toBuyList,ToBuyFragment.this);
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

        return rootView;
    }

    /* to edit a note */
    @Override
    public void onItemClick(int position) {
        Intent editNote = new Intent(getContext(), NewNoteEditor.class);
        editNote.putExtra("NoteTitle",toBuyList.get(position).getNoteTitle());
        editNote.putExtra("NoteContent",toBuyList.get(position).getNoteDescription());
        editNote.putExtra("NoteFragment","ToBuy");
        startActivity(editNote);
    }

    /* to delete a note */
    @Override
    public void onItemLongClick(int position) {
        notesRoom.notesDao().deleteNote(toBuyList.get(position).getNoteTitle())
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getContext(), R.string.NoteDeleted, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /* to add a note onSwipe */
    public void onSwipe(){
        Intent addNewNote = new Intent(getContext(),NewNoteEditor.class);
        startActivity(addNewNote);
    }

}