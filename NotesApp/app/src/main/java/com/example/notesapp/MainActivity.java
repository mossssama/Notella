package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notesapp.Fragments.*;

import com.example.notesapp.Room.NotesDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button addNewNote;

    /* To track current Fragment */
    public static int CURRENT_FRAGMENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* MainActivity Views the FrameLayout */
        bottomNavigationView=findViewById(R.id.bottomNavigationBar);
        addNewNote=findViewById(R.id.addNewNoteBtn);

        /* Default Fragment in tempFrame is ToDoFragment */
        loadContainerWithFragment(new ToDoFragment());
        CURRENT_FRAGMENT_ID=R.id.toDo;

        /* Change the Fragment in tempFrame when an icon from bottomNav is clicked */
        bottomNavigationView.setOnItemSelectedListener( item -> {
            switch(item.getItemId()){
                case R.id.toDo:     loadContainerWithFragment(new ToDoFragment());      CURRENT_FRAGMENT_ID=R.id.toDo;        break;
                case R.id.toBuy:    loadContainerWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID=R.id.toBuy;       break;
                case R.id.toSearch: loadContainerWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID=R.id.toSearch;    break;
                case R.id.toWatch:  loadContainerWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID=R.id.toWatch;     break;
                case R.id.toRead:   loadContainerWithFragment(new ToReadFragment());    CURRENT_FRAGMENT_ID=R.id.toRead;      break;
            }
            return true;
        });

        /* Go to New Note or Edit Old Note */
        addNewNote.setOnClickListener((View view)-> {
                Intent intent =new Intent(getApplicationContext(), NewNoteEditor.class);
                startActivity(intent);
        });

        /* Instance to deal with Room */
        NotesDatabase notesRoom = NotesDatabase.getInstance(this);

//        notesRoom.notesDao().clearRoom()
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {}
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Deletion Done", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Deletion Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

//        notesRoom.notesDao().insertNote(new Note("Write conclusion to GrokkingAlgorithms","to Channel","ToDo"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(MainActivity.this, "Subscribe", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

//        notesRoom.notesDao().insertNote(new Note("Asatak","none","ToBuy"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(MainActivity.this, "Subscribe", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

//        notesRoom.notesDao().insertNote(new Note("CS crash course","important","ToWatch"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(MainActivity.this, "Subscribe", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

//        notesRoom.notesDao().insertNote(new Note("KMP vs KMM","cross plaform vs KMM","ToSearch"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(MainActivity.this, "Subscribe", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });


//        notesRoom.notesDao().insertNote(new Note("KMM","important Book","ToRead"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(MainActivity.this, "Subscribe", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });



//        notesRoom.notesDao().deleteNote("Write conclusion to GrokkingAlgorithms")
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });

        notesRoom.notesDao().getFragmentNumberNotes("ToDo")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(Integer integer) {
                        Toast.makeText(MainActivity.this, ""+integer, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        notesRoom.notesDao().getFragmentNumberNotes("ToBuy")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(Integer integer) {
                        Toast.makeText(MainActivity.this, ""+integer, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        notesRoom.notesDao().getTotalNumberNotes()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onSuccess(Integer integer) {
                                Toast.makeText(MainActivity.this, "Success :"+integer, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


    }
    /* Function to load the fragmentContainer with specific fragment */
    public void loadContainerWithFragment(Fragment fragment){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.fragContainer,fragment);
        ft.commit();
    }

}