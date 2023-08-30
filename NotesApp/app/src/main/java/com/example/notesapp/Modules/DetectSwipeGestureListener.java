package com.example.notesapp.Modules;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.UI.Fragments.ToBuyFragment;
import com.example.notesapp.UI.Fragments.ToDoFragment;
import com.example.notesapp.UI.Fragments.ToReadFragment;
import com.example.notesapp.UI.Fragments.ToSearchFragment;
import com.example.notesapp.UI.Fragments.ToWatchFragment;
import com.example.notesapp.UI.Activities.MainActivity;
import com.example.notesapp.R;

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int MIN_SWIPE_DISTANCE_X =500;
    private static final int MIN_SWIPE_DISTANCE_Y=1000;
    private static final int MAX_SWIPE_DISTANCE_X=1000;
    private static final int MAX_SWIPE_DISTANCE_Y=1000;

    private RecyclerView recyclerView=null;
    public RecyclerView getRecyclerView(){
        return recyclerView;
    }
    public void setRecyclerView(RecyclerView recyclerView){this.recyclerView=recyclerView;}

    /* fragment to display the message */
    private ToBuyFragment toBuyFragment=null;
    private ToDoFragment toDoFragment=null;
    private ToReadFragment toReadFragment=null;
    private ToSearchFragment toSearchFragment=null;
    private ToWatchFragment toWatchFragment=null;
    public void setToBuyFragment(ToBuyFragment toBuyFragment){          this.toBuyFragment=toBuyFragment;       }
    public void setToDoFragment(ToDoFragment toDoFragment){             this.toDoFragment=toDoFragment;         }
    public void setToReadFragment(ToReadFragment toReadFragment){       this.toReadFragment=toReadFragment;     }
    public void setToSearchFragment(ToSearchFragment toSearchFragment){ this.toSearchFragment=toSearchFragment; }
    public void setToWatchFragment(ToWatchFragment toWatchFragment){    this.toWatchFragment=toWatchFragment;   }


    /* Casting the onFling function to OnRightSwipe & OnLeftSwipe */
    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            try {
                float deltaX = e1.getX() - e2.getX();
                float deltaY = e1.getY() - e2.getY();
                float deltaXAbs = Math.abs(deltaX);
                float deltaYAbs = Math.abs(deltaY);

                /* OnSwipe(Right or Left) */
                if (deltaXAbs >= MIN_SWIPE_DISTANCE_X && deltaXAbs <= MAX_SWIPE_DISTANCE_X && deltaXAbs>deltaYAbs) {
                    switch(MainActivity.CURRENT_FRAGMENT_ID){
                        case R.id.toBuy:    this.toBuyFragment.onSwipe();
                        case R.id.toDo:     this.toDoFragment.onSwipe();
                        case R.id.toRead:   this.toReadFragment.onSwipe();
                        case R.id.toSearch: this.toSearchFragment.onSwipe();
                        case R.id.toWatch:  this.toWatchFragment.onSwipe();
                    }
                }

            } catch (Exception e) {e.printStackTrace();}

        return true;
    }

}
