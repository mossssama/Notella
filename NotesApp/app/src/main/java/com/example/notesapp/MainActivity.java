package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.notesapp.Fragments.ToBuyFragment;
import com.example.notesapp.Fragments.ToDoFragment;
import com.example.notesapp.Fragments.ToReadFragment;
import com.example.notesapp.Fragments.ToSearchFragment;
import com.example.notesapp.Fragments.ToWatchFragment;
import com.example.notesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /* To allow binding the Fragments */
    ActivityMainBinding binding;

    /* To track current Fragment */
    static int CURRENT_FRAGMENT_ID;

    private GestureDetectorCompat gestureDetectorCompat=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Using OnSwipe Module */
        SwipeGestureListener detectGestureListener=new SwipeGestureListener();
        detectGestureListener.setActivity(this);
        gestureDetectorCompat=new GestureDetectorCompat(this,detectGestureListener);

        /* MainActivity Views the FrameLayout */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Default Fragment in tempFrame is ToDoFragment */
        feedTempFrameWithFragment(new ToDoFragment());
        CURRENT_FRAGMENT_ID=R.id.toDo;

        /* Change the Fragment in tempFrame when an icon from bottomNav is clicked */
        binding.bottomNavigationBar.setOnItemSelectedListener( item -> {
            switch(item.getItemId()){
                case R.id.toDo:     feedTempFrameWithFragment(new ToDoFragment());      CURRENT_FRAGMENT_ID=R.id.toDo;        break;
                case R.id.toBuy:    feedTempFrameWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID=R.id.toBuy;       break;
                case R.id.toSearch: feedTempFrameWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID=R.id.toSearch;    break;
                case R.id.toWatch:  feedTempFrameWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID=R.id.toWatch;     break;
                case R.id.toRead:   feedTempFrameWithFragment(new ToReadFragment());    CURRENT_FRAGMENT_ID=R.id.toRead;      break;
            }
            return true;
        });


    }

    private void feedTempFrameWithFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tempFrame,fragment);
        fragmentTransaction.commit();
    }

    public void onRightSwipe(int menuItemId){
        switch (menuItemId){
            case R.id.toDo:     feedTempFrameWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID=R.id.toBuy;     binding.bottomNavigationBar.getMenu().findItem(R.id.toBuy).setChecked(true);        break;
            case R.id.toBuy:    feedTempFrameWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID=R.id.toSearch;  binding.bottomNavigationBar.getMenu().findItem(R.id.toSearch).setChecked(true);     break;
            case R.id.toSearch: feedTempFrameWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID=R.id.toWatch;   binding.bottomNavigationBar.getMenu().findItem(R.id.toWatch).setChecked(true);      break;
            case R.id.toWatch:  feedTempFrameWithFragment(new ToReadFragment());    CURRENT_FRAGMENT_ID=R.id.toRead;    binding.bottomNavigationBar.getMenu().findItem(R.id.toRead).setChecked(true);       break;
        }
    }

    public void onLeftSwipe(int menuItemId){
        switch (menuItemId){
            case R.id.toRead:   feedTempFrameWithFragment(new ToWatchFragment());   CURRENT_FRAGMENT_ID=R.id.toWatch;   binding.bottomNavigationBar.getMenu().findItem(R.id.toWatch).setChecked(true);      break;
            case R.id.toWatch:  feedTempFrameWithFragment(new ToSearchFragment());  CURRENT_FRAGMENT_ID=R.id.toSearch;  binding.bottomNavigationBar.getMenu().findItem(R.id.toSearch).setChecked(true);     break;
            case R.id.toSearch: feedTempFrameWithFragment(new ToBuyFragment());     CURRENT_FRAGMENT_ID=R.id.toBuy;     binding.bottomNavigationBar.getMenu().findItem(R.id.toBuy).setChecked(true);        break;
            case R.id.toBuy:    feedTempFrameWithFragment(new ToDoFragment());      CURRENT_FRAGMENT_ID=R.id.toDo;      binding.bottomNavigationBar.getMenu().findItem(R.id.toDo).setChecked(true);        break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }
}