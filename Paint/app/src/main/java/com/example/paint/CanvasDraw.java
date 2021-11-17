package com.example.paint;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CanvasDraw {

    private ArrayList<Par<Integer,Float>> paintsOptions;
    private Map<String, ArrayList<Par<Float,Float>>> pathsPoints;
    private int background;

    public CanvasDraw() {}

    public void setPaintsOptions(ArrayList<Par<Integer,Float>> paints){
        paintsOptions = paints;
    }



    public void setPathsPoints(Map<String,ArrayList<Par<Float,Float>>> paths){

        pathsPoints = paths;
    }

    public void setBackground( int backgroundColor){
        background = backgroundColor;
    }



    public ArrayList<Par<Integer, Float>> getPaintsOptions(){
        return paintsOptions;
    }



    public Map<String, ArrayList<Par<Float, Float>>> getPathsPoints(){

        return pathsPoints;
    }

    public int getBackground(){
        return background;
    }

}
