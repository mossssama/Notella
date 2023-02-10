package com.example.notesapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.notesapp.R;
import com.example.notesapp.UI.Fragments.*;

import com.example.notesapp.Room.NotesDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    /* To track current Fragment */
    public static int CURRENT_FRAGMENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* MainActivity Views the FrameLayout */
        bottomNavigationView=findViewById(R.id.bottomNavigationBar);

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

    }


    /* Function to load the fragmentContainer with specific fragment */
    public void loadContainerWithFragment(Fragment fragment){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.fragContainer,fragment);
        ft.commit();
    }

}