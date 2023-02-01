package com.example.notesapp;

import android.view.GestureDetector;
import android.view.MotionEvent;


public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static int MIN_SWIPE_DISTANCE_X=100;
    private static int MIN_SWIPE_DISTANCE_Y=100;
    private static int MAX_SWIPE_DISTANCE_X=1000;
    private static int MAX_SWIPE_DISTANCE_Y=1000;

    private MainActivity activity=null;
    public MainActivity getActivity(){
        return activity;
    }
    public void setActivity(MainActivity activity){
        this.activity=activity;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX,float velocityY){
        float deltaX=e1.getX()-e2.getX();
        float deltaY=e1.getY()-e2.getY();
        float deltaXAbs=Math.abs(deltaX);
        float deltaYAbs=Math.abs(deltaY);

        /* To Detect Left & Right Swipe */
        if (deltaXAbs>=MIN_SWIPE_DISTANCE_X && deltaXAbs<=MAX_SWIPE_DISTANCE_X){
            if(deltaX>0){
                this.activity.onLeftSwipe(this.activity.CURRENT_FRAGMENT_ID);
            }
            else{
                this.activity.onRightSwipe(this.activity.CURRENT_FRAGMENT_ID);
            }
        }

//        /* Up & Down */
//        if (deltaYAbs>=MIN_SWIPE_DISTANCE_Y && deltaYAbs<=MIN_SWIPE_DISTANCE_Y){
//            if(deltaY>0){
//                this.activity.displayMessage("UpSwipe");
//            }
//            else{
//                this.activity.displayMessage("DownSwipe");
//            }
//        }

        return true;
    }


}
