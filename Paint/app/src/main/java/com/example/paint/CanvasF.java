package com.example.paint;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;


public class CanvasF extends Fragment {

    private CanvasView view;
    public View viewTudo;

    private int colorBack;

    public CanvasF() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //colorBack = getArguments().getInt("color");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState !=  null) {
            //colorBack = savedInstanceState.getInt("color");
        }

        //View v = inflater.inflate(R.layout.fragment_canvas, container, false);

        //v.setBackgroundColor(colorBack);



        viewTudo = inflater.inflate(R.layout.fragment_canvas, container, false);

        //verificar a view
        if (view == null) {
            GestureListener mGestureListener = new GestureListener();
            GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
            mGestureDetector.setIsLongpressEnabled(true);
            mGestureDetector.setOnDoubleTapListener(mGestureListener);

            CanvasView paintCanvas = new CanvasView(getContext(), null, mGestureDetector);
            mGestureListener.setCanvas(paintCanvas);
            view = paintCanvas;
        }


        return view;
    }


    public void changeBrushColor(int color) {
        view.changeBrushColor(color);
    }

    public void changeBrushSize(int size) {
        view.changeBrushSize(size);
    }

/*    public void changeBackground() {
        view.changeBackground();
    }*/

    public void changeBackground(int color) {
        view.changeBackground(color);
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putInt("color", colorBack);


    }

    public void eraseCanvas() {
        view.erase();
    }


    @Override
    public void onConfigurationChanged(Configuration cf) {


        super.onConfigurationChanged(cf);



        // Checks the orientation of the screen
        if (cf.orientation == Configuration.ORIENTATION_LANDSCAPE) {




        } else if (cf.orientation == Configuration.ORIENTATION_PORTRAIT){


        }


    }

/*    public void loadPaths(Object p) {
        view.loadPaths(p);

    }

    public void loadPaints(ArrayList<Paint> pa) {
        view.loadPaints(pa);
    }*/

    public Map<String, ArrayList<Par<Float,Float>>> getPathsPoints() {
        return view.getPathsPoints();
    }

    public ArrayList<Par<Integer,Float>> getPaintsOptions() {
        return view.getPaintsOptions();
    }

    public int getBackgroundColor() {
        return view.getbackgroundColor();
    }

    public void setPaths(Map<String,ArrayList<Par<Float, Float>>> pathsPoints) {
        view.setPaths(pathsPoints);
    }

    public void setPaints(ArrayList<Par<Integer, Float>> paintsOptions) {
        view.setPaints(paintsOptions);
    }

    public void updateDraw() {
        view.updateDraw();
    }

    public int getBrushSize() {
        return view.getBrushSize();
    }

    public void eraseAllCanvas() {
        view.deleteAll();
    }
}