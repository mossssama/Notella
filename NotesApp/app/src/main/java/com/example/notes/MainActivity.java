package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.notes.NotesEditors.NewNoteEditor;
import com.example.notes.fragments.ToBuyFragment;
import com.example.notes.fragments.ToDoFragment;
import com.example.notes.fragments.ToSearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TO_DO_FRAGMENT = "ToDo";
    public static final String TO_BUY_FRAGMENT ="ToBuy";
    public static final String TO_SEARCH_FRAGMENT ="ToSearch";

    public static String currentFragment= TO_DO_FRAGMENT;

    public static BottomNavigationView bottomNavigationView;

    ToDoFragment toDoFragment=new ToDoFragment();
    ToBuyFragment toBuyFragment=new ToBuyFragment();
    ToSearchFragment toSearchFragment=new ToSearchFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {         //To inflate created menu
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.drop_down_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //To jump to NoteEditorActivity; whenever menu option is clicked
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add_note){
            Intent intent =new Intent(getApplicationContext(), NewNoteEditor.class);
            startActivity(intent);    return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,toDoFragment).commit();
        bottomNavigationView.setOnItemSelectedListener((MenuItem item)-> {
                switch(item.getItemId()) {
                    case R.id.toDo:     getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, toDoFragment).commit();    currentFragment= TO_DO_FRAGMENT; return true;
                    case R.id.toBuy:    getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, toBuyFragment).commit();   currentFragment= TO_BUY_FRAGMENT; return true;
                    case R.id.toSearch: getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,toSearchFragment).commit(); currentFragment= TO_SEARCH_FRAGMENT; return true;
                }
                return false;
        });

    }

}