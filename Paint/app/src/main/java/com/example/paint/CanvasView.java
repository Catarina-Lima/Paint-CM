package com.example.paint;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CanvasView extends View implements View.OnTouchListener {

    private ArrayList<Paint> paints = new ArrayList<>();
    private ArrayList<Par<Integer,Float>> paintsOptions = new ArrayList<>();

    private ArrayList<Path> paths = new ArrayList<>();
    private Map<String,ArrayList<Par<Float,Float>>> pathsPoints = new HashMap<>();
    private int index = -1;
    private int backGroundColor = Color.WHITE;
    private GestureDetector mGestureDetector;
    private Timestamp timeStamp;

    private static final double RESPONSE = 0.000001;
    private int brushColor = Color.YELLOW;
    private float brushSize = 20f;

/*    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        initPaint();
    }*/

    public CanvasView(Context context, AttributeSet attrs, GestureDetector mGestureDetector) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        //Paint p = new Paint();
        //initPaint(p, brushColor, brushSize);
        //paints.add(p);
        //paths.add(new Path());
       // Pair<Float, Float> pair = new Pair<>();
        //pathsPoints.add(new Pair<Float,Float>(0.00001f, 0.00001f));
        //index++;

    }




    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i <= index; i++){
            canvas.drawPath(paths.get(i), paints.get(i));// draws the path with the paint
        }

    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //super.onTouchEvent(event);

        //event.getAction();


        Log.d("touch", "touch");
        mGestureDetector.onTouchEvent(event);
        //verificar o k volta
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        long milliseconds = 0;
        int seconds = 0;
        boolean teste = timeStamp == null;



        float eventX = event.getX();
        float eventY = event.getY();

        // quando mudar a cor e o tamanho no fragmento palette enviar
        // fazer troca de informação de cores : atraves
        // verificar se a cor e o tamanho estao o mesmo.

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                if (!teste) {
                    Timestamp current = new Timestamp(System.currentTimeMillis());
                    milliseconds  = current.getTime() - timeStamp.getTime();
                    seconds = (int) milliseconds / 1000;
                    seconds = (seconds % 3600) % 60;
                }

                if ( teste || milliseconds > 200 || seconds > 0) {

                    index++;
                    Paint p = new Paint();

                    //fazer uma funcao
                    initPaint(p, brushColor, brushSize);
                    //p.setColor(brushColor);
                    paints.add(p);
                    paths.add(index, new Path());

                    //pathsPoints.add(index, new ArrayList<>());
                    paintsOptions.add(new Par<>(brushColor, brushSize));
                    //pathsPoints = new HashMap<>();



                    paths.get(index).moveTo(eventX, eventY);// updates the path initial point



                    ArrayList<Par<Float,Float>> aux = new ArrayList<>();
                    aux.add(new Par<>(eventX, eventY));
                    pathsPoints.put(String.valueOf(index), aux);
                }



                return true;

            case MotionEvent.ACTION_MOVE:


                if (!teste) {
                    Timestamp current = new Timestamp(System.currentTimeMillis());
                    milliseconds  = current.getTime() - timeStamp.getTime();
                    seconds = (int) milliseconds / 1000;
                    seconds = (seconds % 3600) % 60;
                }

                Log.d("teste ", String.valueOf(teste));
                Log.d("mili ", String.valueOf(milliseconds));
                Log.d("mili ", String.valueOf(seconds));

                if ( teste || milliseconds > 200 || seconds > 0) {
                    paths.get(index).lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                    ArrayList aux2 = pathsPoints.get(String.valueOf(index));
                    aux2.add(new Par<>(eventX, eventY));
                    pathsPoints.put(String.valueOf(index), aux2);
                }
                break;

            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                timeStamp = new Timestamp(System.currentTimeMillis());
                break;
            default:
                return false; //false
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

/*    public void changeBackground(){

        setBackgroundColor(backGroundColor);
    }*/

    public void changeBackground(int color){
        backGroundColor = color;
        setBackgroundColor(color);
    }

    public void erase(){

        if (index > 0){
            paints.remove(index);
            paths.remove(index);
            pathsPoints.remove(String.valueOf(index));
            paintsOptions.remove(index);
            index--;

            if (index == 0){
                paints.clear();
                //Paint p = new Paint();
                //initPaint(p, brushColor, brushSize);
                //paints.add(index, p);
                paths.clear();
                //paths.add(index, new Path());
                paintsOptions.clear();
                pathsPoints.clear();
                index--;
            }
            else{
                paints.remove(index);
                paths.remove(index);
                pathsPoints.remove(String.valueOf(index));
                paintsOptions.remove(index);
                index--;
            }

//            paints.remove(index);
//            paths.remove(index);
//            index--;
            invalidate();
        }
        //penso k nunca vai entrar aqui - so se apagar tela vazia
        else if (index == 0) {
            paints.clear();
            //Paint p = new Paint();
            //initPaint(p, brushColor, brushSize);
            //paints.add(index, p);
            paths.clear();
           // paths.add(index, new Path());
            paintsOptions.clear();
            pathsPoints.clear();
            index--;

            invalidate();
        }
        else if(index < 0){

        }
        /*index = 0;
        paints = new ArrayList<>();
        paths = new ArrayList<>();
        Paint p = new Paint();
        initPaint(p, brushColor);
        paints.add(index, p);
        paths.add(index, new Path());*/

        //paint.setColor(backGroundColor);

        //invalidate();
    }

    public void changeBrushColor(int color){

        brushColor = color;

/*        index++;
        Paint p = new Paint();

        //fazer uma funcao
        initPaint(p, color, brushSize);
        p.setColor(color);
        paints.add(p);
        paths.add(index, new Path());*/
        //paint.setColor(backGroundColor);
    }

/*    private void initPaint(){
        paints.get(index).setAntiAlias(true);
        paints.get(index).setStrokeWidth(brushSize);
        paints.get(index).setColor(Color.YELLOW);
        paints.get(index).setStyle(Paint.Style.STROKE);
        paints.get(index).setStrokeJoin(Paint.Join.ROUND);
    }*/

    public void initPaint(Paint p, int color, float size){
        p.setAntiAlias(true);
        p.setStrokeWidth(size);
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
    }

    public void changeBrushSize(int size) {

        brushSize = size;
    }

    public void deleteAll(){

        index = -1;
        paints.clear();
        paths.clear();
        pathsPoints.clear();
        paintsOptions.clear();
        invalidate();

    }


/*    public void loadPaths(Object p) {
        paths = (ArrayList<Path>) p;
    }

    public void loadPaints(ArrayList<Paint> pa) {
        paints = pa;

    }*/

    public Map<String,ArrayList<Par<Float, Float>>> getPathsPoints() {
        return pathsPoints;
    }

    public ArrayList<Par<Integer, Float>> getPaintsOptions() {
        return paintsOptions;
    }

    public int getbackgroundColor() {
        return backGroundColor;
    }



    public void setPaths(Map<String, ArrayList<Par<Float, Float>>> pathsP) {

        ArrayList<Path> newPaths = new ArrayList<>();


        for (Map.Entry<String, ArrayList<Par<Float, Float>>> p :pathsP.entrySet()) {

            Path newP = new Path();

            for (int i = 0; i < p.getValue().size(); i++){
                if (i == 0)
                    newP.moveTo(p.getValue().get(i).first, p.getValue().get(i).second);


                else
                    newP.lineTo(p.getValue().get(i).first, p.getValue().get(i).second);

            }

            newPaths.add(newP);

        }

        paths = newPaths;
        pathsPoints = pathsP;

        index = newPaths.size() - 1;
        System.out.println("Index:"+index);



    }

    public void setPaints(ArrayList<Par<Integer, Float>> paintsO) {

        ArrayList<Paint> newPaints = new ArrayList<>();

        for (int i = 0; i < paintsO.size(); i++){

            Paint newPaint = new Paint();
            initPaint(newPaint, paintsO.get(i).first,  paintsO.get(i).second);
            newPaints.add(newPaint);

        }
        paintsOptions = paintsO;
        paints = newPaints;
    }

    public void updateDraw(){
        invalidate();
    }

    public int getBrushSize() {
        return (int) brushSize;
    }
}