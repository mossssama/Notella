package com.example.notesapp.RecyclerView;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

/* Interface abstracting the RecyclerView item clicks
* It's implemented in each Fragment containing RecyclerView */
public interface RecyclerViewItemClick {
    void onItemClick(int position);
    void onItemLongClick(int position);
}
