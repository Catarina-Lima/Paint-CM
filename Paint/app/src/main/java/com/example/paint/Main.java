package com.example.paint;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main extends AppCompatActivity  implements SensorEventListener{

    //cor de fundo
    private int background;
    View atual;
    private String mode = "";

    //juncao apps - *
    private Boolean showingPalette = false;
    private Boolean firstTime = true;
    protected int  brush_color= Color.YELLOW;

    public CanvasF canvas;



    int count = 1;
    private boolean init;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private float x1, x2, x3;
    private static final float ERROR = (float) 40.0;

    private Sensor ligth;
    private Sensor rotation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //s = new SensorActivity(canvas);

        if (savedInstanceState == null){
            background = Color.WHITE;
        }
        else {
            background = savedInstanceState.getInt("BackgroundColor");

            // *
            showingPalette= savedInstanceState.getBoolean("showingP");
            firstTime= savedInstanceState.getBoolean("ft");
            brush_color = savedInstanceState.getInt("color");
        }



        init = false;

        //counter = (TextView) findViewById(R.id.counter);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);



        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        ligth = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, ligth, SensorManager.SENSOR_DELAY_NORMAL);

        rotation = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, rotation,1000000);



        onConfigurationChanged(this.getResources().getConfiguration());



    }

/*    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);


        savedInstanceState.putInt("BackgroundColor", background);

        // *
        savedInstanceState.putBoolean("showingP", showingPalette);
        savedInstanceState.putBoolean("ft", firstTime);

        if (showingPalette) {
            PaletteF paletteAtual = (PaletteF) getSupportFragmentManager().findFragmentById(R.id.palette_retrato);
            //brush_color = paletteAtual.colorButton;
        }
        savedInstanceState.putInt("color", brush_color);

    }*/


    // nao funcionou, fazer diretamente no oncreate foi melhor
/*    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        background = savedInstanceState.getInt("BackgroundColor");


    }*/




    @Override
    public void onConfigurationChanged(Configuration cf) {


        super.onConfigurationChanged(cf);


        FragmentManager fm = this.getSupportFragmentManager();
        //FragmentTransaction ft = fm.beginTransaction();


        // Checks the orientation of the screen
         if (cf.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Log.d("teste", "entrar");
            setContentView(R.layout.activity_main2);


            loadLandscapeFragments(fm);



        } else if (cf.orientation == Configuration.ORIENTATION_PORTRAIT){

             setContentView(R.layout.activity_main2);

            //Log.d("ft", firstTime.toString());



            // se nao aparecia antes de ter virado, deve continuar assim
            if (!firstTime) {

                //PaletteF paletteAtual = (PaletteF) getSupportFragmentManager().findFragmentById(R.id.fc2);
                //brush_color = paletteAtual.colorButton;

                if (showingPalette) {
                    showingPalette = false;
                    aparecerPalette(findViewById(R.id.button3));
                }

            } else {
                firstTime = false;
            }


            loadPortraitFragments(fm);

        }




    }

    private void loadPortraitFragments(FragmentManager fm) {

        //CanvasF fragmentC = new CanvasF();

        //FrameLayout canvas = findViewById(R.id.fc3);
        //FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) canvas.getLayoutParams();
        //param.height = 460;

        if (canvas == null)
            canvas = new CanvasF();
        else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(canvas);
            ft.commit();
            fm.executePendingTransactions();

        }

        FragmentTransaction ftt = fm.beginTransaction();

       /* Bundle bundle = new Bundle();
        background = background;
        bundle.putInt("color", background);
        fragmentC.setArguments(bundle);*/

        ftt.add(R.id.canvasF, canvas);

        //ft.add(R.id.fc, fragmentC);
        //ft.add(R.id.fc2, fragmentP);
        ftt.commit();

        /*CanvasF canvasAtual = (CanvasF) getSupportFragmentManager().findFragmentById(R.id.canvasF1);
        canvasAtual.getView().setBackgroundColor(background);*/

        //assignBackgroundColor(background);
    }

    private void loadLandscapeFragments(FragmentManager fm) {


        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(canvas);
        ft.commit();
        fm.executePendingTransactions();

        FragmentTransaction ftt = fm.beginTransaction();



        //manter a cor do fundo!!!
/*        CanvasF fragmentC = new CanvasF();
        Bundle bundleC = new Bundle();
        bundleC.putInt("color", background);
        fragmentC.setArguments(bundleC);*/


        PaletteF fragmentP = new PaletteF(canvas);
        Bundle bundleP = new Bundle();
        bundleP.putInt("colorB", brush_color);
        bundleP.putInt("colorBackground", background);
        fragmentP.setArguments(bundleP);

        //FrameLayout canvas = findViewById(R.id.fc3);
        //FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) canvas.getLayoutParams();
        //param.height = 460;


        ftt.add(R.id.canvasF, canvas);
        ftt.add(R.id.fc2, fragmentP);

        //ft.add(R.id.fc, fragmentC);
        //ft.add(R.id.fc2, fragmentP);
        ftt.commit();

/*        ScrollView s = findViewById(R.id.teste);
        s.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        s.setFocusable(false);
        s.setFocusableInTouchMode(false);
        s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });*/


/*        View c = findViewById(R.id.canvasF);
        c.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        int[] t = new int[2];
        c.getLocationOnScreen(t);
        Rect r = new Rect(c.getLeft(), c.getTop(),c.getRight(), c.getBottom());
        s.requestChildRectangleOnScreen(c,r,true);
        s.requestDisallowInterceptTouchEvent(true);*/




        //assignBackgroundColor(background);

    }

    public void aparecerPalette(View v) {

        //ImageButton b = findViewById(R.id.button3);
        ImageButton b = (ImageButton) v;

        /*if (showingPalette == null) {
            showingPalette = false;

        }*/

        //Log.d("valor boolean", showingPalette.toString());

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FrameLayout canvas = (FrameLayout) findViewById(R.id.canvasF);
        ViewGroup.LayoutParams canvasParams = canvas.getLayoutParams();

        ConstraintLayout.LayoutParams paramsLeft = (ConstraintLayout.LayoutParams) b.getLayoutParams();

        if (!showingPalette) {

            showingPalette = true;
            //mudar botao - funcao
            b.setImageResource(R.drawable.arrow);
            paramsLeft.leftMargin = paramsLeft.leftMargin - 40;


            canvasParams.height = canvasParams.height - 200;

            canvas.setLayoutParams(canvasParams);




            PaletteF fragmentP = new PaletteF(this.canvas);

            Bundle bundle = new Bundle();
            bundle.putInt("colorB", brush_color);
            bundle.putInt("colorBackground", background);
            fragmentP.setArguments(bundle);

            ft.replace(R.id.palette_retrato, fragmentP);


        }
        else {
            showingPalette = false;

            //mudar botao - funcao
            b.setImageResource(R.drawable.palette);
            //b.setScaleType(ImageView.ScaleType.FIT_XY);
            paramsLeft.leftMargin = paramsLeft.leftMargin + 40;
            //paramsLeft.topMargin = paramsLeft.topMargin + 100;



            canvasParams.height = canvasParams.height + 200;
            canvas.setLayoutParams(canvasParams);

            PaletteF paletteAtual = (PaletteF) fm.findFragmentById(R.id.palette_retrato);
            //brush_color = paletteAtual.colorButton;

            ft.remove(fm.findFragmentById(R.id.palette_retrato));



        }

        ft.commit();


    }



    public int getBrush_Color() {
        return brush_color;
    }


    public void changeBackground(int color){
        background = color;
    }


    public void assignBackgroundColor (Integer color) {
        /*atual = findViewById(R.id.main_view);
        atual.setBackgroundColor(background);*/

       /* CanvasF canvasAtual = (CanvasF) getSupportFragmentManager().findFragmentById(R.id.canvasF);
        canvasAtual.getView().setBackgroundColor(background);*/

        background = color;
        canvas.changeBackground(color);

    }



    public void pickBrushOpen(){
        NumberPicker n = findViewById(R.id.numberpicker1);
        n.setMaxValue(100);
        n.setMinValue(0);
        n.setValue(canvas.getBrushSize());
        n.setVisibility(View.VISIBLE);
    }

    public void pickBrushClose(){
        NumberPicker n = findViewById(R.id.numberpicker1);
        int size = n.getValue();
        n.setVisibility(View.GONE);
        canvas.changeBrushSize(size);
    }





    /*
    Permite carregar o menu
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_about:

                Intent intent = new Intent(this, Team.class);
                startActivity(intent);

                return true;
            case R.id.item_settings:

                // cor atual do background
                atual = findViewById(R.id.main_view);


                Intent i = new Intent(this, SettingsActivity.class);

                i.putExtra("color",
                        String.valueOf(background));

                i.putExtra("actualMode",
                        mode);

                //startActivityForResult(i, REQUEST_CODE);
                startActivityForResult(i, 0);
//                View view = findViewById(R.id.main_view);
//                view.setBackgroundColor(Color.BLUE);

                return true;

            case R.id.map:

                Intent map = new Intent(this, MapDraw.class);
                startActivity(map);
                return true;

            case R.id.save:

                /*if(canvas.view.paths == null){
                    Log.d("OOIIII", "onOptionsItemSelected: ");
                }
                else {
                    Log.d("iiiooooo", "test");
                }

                Saving teste = new Saving(canvas.view.paths);

                Intent save = new Intent(this, teste.getClass());


                startActivity(save);*/
                askDrawName(true);
                //SaveCanvas();
                return true;

            case R.id.load:
                //loadCanvas();
                
                Intent load = new Intent(this, Saving.class);

                /*load.putExtra("color",
                        String.valueOf(background));*/
                startActivityForResult(load, 0);

                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void askDrawName(boolean show) {

        ConstraintLayout popUp = findViewById(R.id.namePopUp);

        if (show)
            popUp.setVisibility(View.VISIBLE);

        else
            popUp.setVisibility(View.GONE);


    }


    public void loadCanvas(String nameDrawToLoad){

        CollectionReference db = FirebaseFirestore.getInstance().collection("savedCanvas");

        DocumentReference docRef = db.document(nameDrawToLoad);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        //Map<String, Object> dados = document.getData();



                        CanvasDraw cd = document.toObject(CanvasDraw.class);

                        //System.out.println("BK"+cd.getBackground());

                        canvas.setPaths(cd.getPathsPoints());
                        canvas.setPaints(cd.getPaintsOptions());

                        int newBackground = cd.getBackground();
                        canvas.changeBackground(newBackground);
                        canvas.updateDraw();

                        changeBackground(newBackground);
                        if (showingPalette) {
                            FragmentManager fm = getSupportFragmentManager();
                            PaletteF paletteAtual = (PaletteF) fm.findFragmentById(R.id.palette_retrato);
                            paletteAtual.changeColorIconBackground(cd.getBackground());

                        }




                        /*Map<String, Pair<Integer,Float>> pa = (Map<String, Pair<Integer,Float>>) dados.get("paints");

                        ArrayList<Paint> paints = new ArrayList<>();
                        Paint pp = new Paint();

                        for (Map.Entry<String, Pair<Integer, Float>> entry : pa.entrySet()) {

                            //esta o par em getValue
                            System.out.println(entry.getValue().toString());
                            Pair<Integer, Float> brush = entry.getValue();
                            int brushColor = brush.first;
                            float  brushSize = brush.second;

                            canvas.view.initPaint(pp, brushColor, brushSize );

                            int indexPaint = Integer.valueOf(entry.getKey());

                            paints.add(indexPaint, pp);
                        }*/

                        /*Path p = new Path();
                        //p.

                        System.out.println(p);
                        loading.loadPaths(p);
                        //loading.loadPaints(paints);

                        System.out.println(dados.get("bc"));
                        loading.changeBackground((Integer) dados.get("bc"));*/
                        //canvas = loading;
                    }
                }
            }
        });


    }



    public void SaveCanvas(View view) {

        EditText name = findViewById(R.id.nameContainer);
        String drawName = name.getText().toString();


        CollectionReference savedCanvas = FirebaseFirestore.getInstance().collection("savedCanvas");

        DocumentReference dr = savedCanvas.document(drawName);

        //Main t = (Main) getParent();
        //Map<String, Object> teste = new HashMap<>();



/*        Map<String, Pair<Integer,Float>> paints = new HashMap<>();

        int i = 0;

        for (Paint p : canvas.view.paints ){

            Pair<Integer, Float> paintStuff = new Pair<>(p.getColor(), p.getStrokeWidth());

            paints.put(String.valueOf(i), paintStuff);
            i++;
        }

        for (Path p : canvas.view.paths) {
            PathMeasure p2 = new PathMeasure(p, false);
            Matrix m =  new Matrix();
            p2.getMatrix(p2.getLength(),m,PathMeasure.POSITION_MATRIX_FLAG);
            System.out.println(m);

        }*/

        CanvasDraw cd = new CanvasDraw();
        cd.setBackground(canvas.getBackgroundColor());
        cd.setPathsPoints(canvas.getPathsPoints());
        cd.setPaintsOptions(canvas.getPaintsOptions());

        dr.set(cd);

        askDrawName(false);



        //teste.put("p", );
      /*  teste.put("paints", paints);
        teste.put("bc", Integer.valueOf(canvas.view.backGroundColor) );

        dr.set(teste);*/



        //savedCanvas.add();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data.hasExtra("newColor")) {
                background = Integer.valueOf(data.getExtras().getString("newColor"));
                assignBackgroundColor(background);

            }
            if (data.hasExtra("mode")) {
                mode = data.getExtras().getString("mode");

            }

            if (data.hasExtra("NameDrawToLoad")) {
                //System.out.println(data.getExtras().getString("NameDrawToLoad"));
                loadCanvas(data.getExtras().getString("NameDrawToLoad"));

            }

            if (data.hasExtra("NameDrawToDelete")) {
                //System.out.println(data.getExtras().getString("NameDrawToDelete"));

                deleteCanvas(data.getExtras().getString("NameDrawToDelete"));

            }

        }
    }

    private void deleteCanvas(String nameDrawToDelete) {

        CollectionReference db = FirebaseFirestore.getInstance().collection("savedCanvas");

        DocumentReference docRef = db.document(nameDrawToDelete);

        docRef.delete();
    }


    // ------------------------- Sensor -------------------------//

    private static final int SHAKE_THRESHOLD = 1000;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;

    float[] inclineGravity = new float[3];
    float[] mGravity;
    float[] mGeomagnetic;
    float orientation[] = new float[3];
    float pitch;
    float roll;



    @Override
    public void onSensorChanged(SensorEvent e) {

        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            mGravity = e.values;
/*
            //Get x,y and z values
            float x,y,z;
            x = e.values[0];
            y = e.values[1];
            z = e.values[2];


            if (!init) {
                x1 = x;
                x2 = y;
                x3 = z;
                init = true;
            } else {

                float diffX = Math.abs(x1 - x);
                float diffY = Math.abs(x2 - y);
                float diffZ = Math.abs(x3 - z);

                //Handling ACCELEROMETER Noise
                if (diffX < ERROR) {

                    diffX = (float) 0.0;
                }
                if (diffY < ERROR) {
                    diffY = (float) 0.0;
                }
                if (diffZ < ERROR) {

                    diffZ = (float) 0.0;
                }


                x1 = x;
                x2 = y;
                x3 = z;*/

                // TA A MUDAR SE TIVER ALINHADO
                //Horizontal Shake Detected!
                /*if (diffX > diffY) {

                    Toast.makeText(Main.this, "Shake Detected!", Toast.LENGTH_SHORT).show();
                    canvas.eraseCanvas();
                }*/
   /*             else if (x1 <= 0.01 && x3 <= 0.01 && x2 >= 1) {
                    canvas.changeBackground();
//                    mAccelerometer.wai

                }

                else if (x1 <= 0.01 && x2 <= 0.01  && x3 >= 1) {
                    canvas.changeBackground();

                }
                else if (x2 <= 0.01 && x3 <= 0.01  && x1 >= 1) {
                    canvas.changeBackground();

                }*/




            //}



            //----------------- tentaiva 2 --------------------//



            float x;
            float y;
            float z;

            long curTime = System.currentTimeMillis();

            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = e.values[0];
                y = e.values[1];
                z = e.values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(this, "Totally erase canvas detected", Toast.LENGTH_SHORT).show();

                    canvas.eraseAllCanvas();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }


        }
        else if (e.sensor.getType() == Sensor.TYPE_LIGHT) {

            //float nao entra em switch
            float lux = e.values[0];
            float brightness = getWindow().getAttributes().screenBrightness;

            if (lux <= 3.4) {
                //30 para se notar
                brightness = 150 / (float)255;
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.screenBrightness = brightness;
                getWindow().setAttributes(lp);
            }

            /*else if (lux >= 70){
                brightness = 30/ (float)255;
            }*/
            else if (lux >= 10000){
                brightness = 120 / (float)255;
            }

            else {
                try {
                    brightness = Settings.System.getInt(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
                } catch (Settings.SettingNotFoundException settingNotFoundException) {
                    settingNotFoundException.printStackTrace();
                }
            }




            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = brightness;
            getWindow().setAttributes(lp);

        }

        else if (e.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            mGeomagnetic = e.values;

            final float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrix(rotationMatrix, null,
                    mGravity, mGeomagnetic);

// Express the updated rotation matrix as three orientation angles.
            final float[] orientationAngles = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            Random r = new Random();
            int randomBC = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));

            //azimuth
            float x = Math.abs(orientationAngles[0]) ;
            //Log.d("x", String.valueOf(orientationAngles[0]));

            //pitch
            float y = Math.abs(orientationAngles[1]) ;
            //Log.d("y", String.valueOf(orientationAngles[1]));

            //roll
            float z = Math.abs(orientationAngles[2]) ;
            //Log.d("z", String.valueOf(orientationAngles[2]));

            //Handling ACCELEROMETER Noise
            if (x <= 0.0005 ) {

                x = (float) 0.0;
            }
            if (y <= 0.0005 ) {
                y = (float) 0.0;
            }
            // z é problemático
            if (z <= 0.0005 ) {
                z = (float) 0.0;
            }

            if (x == 0.0)  {
                background = randomBC;
                canvas.changeBackground(randomBC);
            }
            else if (y  == 0.0 ) {
                background = randomBC;
                canvas.changeBackground(randomBC);
            }
            else if (z  == 0.0 ) {
                background = randomBC;
                canvas.changeBackground(randomBC);
            }


        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void changeBrushColor(int color) {
        brush_color = color;
    }
}