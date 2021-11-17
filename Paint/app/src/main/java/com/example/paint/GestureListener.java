package com.example.paint;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {

    private CanvasView canvas;

    void setCanvas(CanvasView canvas) {
        this.canvas = canvas;
    }

    ////////SimpleOnGestureListener
/*    @Override
    public void onLongPress(MotionEvent motionEvent) {
        canvas.changeBackground();
    }*/

    /////////OnDoubleTapListener


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("gesture", "gesture");
        //canvas.erase();
        canvas.erase();
        return false;
    }



    


}
