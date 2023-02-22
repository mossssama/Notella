package com.example.notesapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.SharedPrefs;
import com.example.notesapp.UI.Fragments.*;

import com.example.notesapp.Room.NotesDatabase;
import com.example.notesapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    int intentFragment;

    /* To save preferences(controlling notifying messages to user) */
    public static SharedPrefs sharedPrefs;

    /* To track current Fragment */
    public static int CURRENT_FRAGMENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Using dataBinding to isolate UI from code*/
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        /* Default Fragment in tempFrame is ToDoFragment */
        loadContainerWithFragment(new ToDoFragment());
        CURRENT_FRAGMENT_ID=R.id.toDo;

        /* Instance to deal with SharedPrefs */
        sharedPrefs=new SharedPrefs(getApplicationContext(),"AppData");

        /* Change the Fragment in tempFrame when an icon from bottomNav is clicked */
        binding.bottomNavigationBar.setOnItemSelectedListener( item -> {
            switch(item.getItemId()){
                case R.id.toDo:     loadContainerWithFragment(new ToDoFragment());      CURRENT_FRAGMENT_ID=R.id.toDo;        break;
                case R.id.toBuy:    loadContainerWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID=R.id.toBuy;       break;
                case R.id.toSearch: loadContainerWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID=R.id.toSearch;    break;
                case R.id.toWatch:  loadContainerWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID=R.id.toWatch;     break;
                case R.id.toRead:   loadContainerWithFragment(new ToReadFragment());    CURRENT_FRAGMENT_ID=R.id.toRead;      break;
            }
            return true;
        });

        /* Return to the same fragment after adding a new Note */
        if(getIntent().getExtras()!=null) {
            intentFragment = getIntent().getExtras().getInt("fragment");
            switch (intentFragment) {
                case R.id.toDo:     loadContainerWithFragment(new ToDoFragment());      CURRENT_FRAGMENT_ID = R.id.toDo;        binding.bottomNavigationBar.getMenu().getItem(0).setChecked(true); break;
                case R.id.toBuy:    loadContainerWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID = R.id.toBuy;       binding.bottomNavigationBar.getMenu().getItem(1).setChecked(true); break;
                case R.id.toSearch: loadContainerWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID = R.id.toSearch;    binding.bottomNavigationBar.getMenu().getItem(2).setChecked(true); break;
                case R.id.toWatch:  loadContainerWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID = R.id.toWatch;     binding.bottomNavigationBar.getMenu().getItem(3).setChecked(true); break;
                case R.id.toRead:   loadContainerWithFragment(new ToReadFragment());    CURRENT_FRAGMENT_ID = R.id.toRead;      binding.bottomNavigationBar.getMenu().getItem(4).setChecked(true); break;
            }

            /* Inform the new user how to edit or delete a note */
            if(sharedPrefs.readMap("hasAddedFirstNote",false)==false){
                Toast.makeText(this, R.string.HowToEditANote, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, R.string.HowToDeleteANote, Toast.LENGTH_SHORT).show();
                sharedPrefs.write("hasAddedFirstNote",true);
            }

            /* Inform the user about the succession of adding new note */
            Toast.makeText(this, R.string.NoteSaved, Toast.LENGTH_SHORT).show();
        }

        /* Inform the new user how to add a note */
        if(sharedPrefs.readMap("IsVisited",false)==false){
            Toast.makeText(this, R.string.HowToAddANote, Toast.LENGTH_SHORT).show();
            sharedPrefs.write("IsVisited",true);
        }

    }


    /* Function to load the fragmentContainer with specific fragment */
    public void loadContainerWithFragment(Fragment fragment){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.fragContainer,fragment);
        ft.commit();
    }

}