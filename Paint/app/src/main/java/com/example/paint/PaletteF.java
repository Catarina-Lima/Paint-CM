package com.example.paint;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import yuku.ambilwarna.AmbilWarnaDialog;


public class PaletteF extends Fragment {


    private ConstraintLayout cl;
    public int colorButton;
    private Button b;

    private CanvasF canvas;
    private Button size;
    private boolean openBrush = false;

    private ImageButton background;
    public int colorButtonBackground;



    public PaletteF() {
        // Required empty public constructor
    }

    public PaletteF(CanvasF canvasF) {
        canvas = canvasF;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState !=  null){
            colorButton = savedInstanceState.getInt("colorB");
            colorButtonBackground = savedInstanceState.getInt("colorBackground");
        }






    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("colorB", colorButton);
        savedInstanceState.putInt("colorBackground", colorButtonBackground);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_palette, container, false);

        b = view.findViewById(R.id.cor_pincel);
        b.setOnClickListener(this::chooseColor);


        size = view.findViewById(R.id.button4);
        size.setOnClickListener(this::openBrush);

        background = view.findViewById(R.id.background);
        background.setOnClickListener(this::chooseColor);


        //MainActivity m =  getActivity();





        if (savedInstanceState != null) {
            colorButton = savedInstanceState.getInt("colorB");
            colorButtonBackground = savedInstanceState.getInt("colorBackground");
        }
        else {
            colorButton = Color.MAGENTA;
            colorButtonBackground = Color.RED;
        }

        colorButton = getArguments().getInt("colorB");
        b.setBackgroundColor(colorButton);
        colorButtonBackground = getArguments().getInt("colorBackground");
        background.setColorFilter(colorButtonBackground);

        return view;
    }


    private void openBrush(View view) {

        Main v = (Main) getActivity();

        if (!openBrush){
            openBrush = true;
            size.setText("Ok");
            v.pickBrushOpen();

        }
        else {
            openBrush = false;
            size.setText("Size");
            v.pickBrushClose();
        }




    }


    public void chooseColor(View view) {

        Log.d("view", String.valueOf(view.getId()));
        String funcao = "";
        int lateColor = 0;

        if (view.getId() == R.id.cor_pincel) {
            funcao = "brush";
            lateColor = colorButton;
        }
        else if (view.getId() == R.id.background){
            funcao = "background";
            lateColor = colorButtonBackground;
        }

        //if (view.getId())
        openColorPicker(funcao, lateColor);


        //finish();
    }

    private void openColorPicker(String f, int lateColor) {


        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this.getActivity(), lateColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Main m = (Main) getActivity();


                if (f.equals("brush")) {
                    colorButton = color;
                    b.setBackgroundColor(colorButton);
                    canvas.changeBrushColor(color);
                    m.changeBrushColor(color);
                }
                else if (f.equals("background")){

                    /*Drawable img = getContext().getDrawable(R.drawable.background);
                    img.setTint(color);
                    background.setBackground(img);*/
                    canvas.changeBackground(color);
                    background.setColorFilter(color);
                    colorButtonBackground = color;

                    //necessario pk o fragmento e criado e eliminado multiplas vezes
                    m.changeBackground(color);





                }



            }
        });

        colorPicker.show();

    }

    public void changeColorIconBackground(int color){

        background.setColorFilter(color);
        colorButtonBackground = color;

    }
}