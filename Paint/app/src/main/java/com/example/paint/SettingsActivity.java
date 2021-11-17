package com.example.paint;

import static com.example.paint.R.layout.activity_settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity  {

    Color backgroundButtonColor;

    // k para teste será o do botao
    ConstraintLayout backgroundView;
    int defaultColor;
    Button colorB;
    private SwitchCompat themeS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_Paint);
        setContentView(activity_settings);

        // para o switch estar ah escuta
        /*themeS = findViewById(R.id.theme_switch);
        themeS.setOnCheckedChangeListener(this);*/

        loadButtonBackground();
//      loadModeSwitch();
    }

    private void loadButtonBackground() {

        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("color");

        if (value1 != null) {
            defaultColor = Integer.parseInt(value1);
        } else {
            defaultColor = Color.WHITE;
        }

        colorB = (Button) findViewById(R.id.colorB);
        colorB.setBackgroundColor(defaultColor);

    }



    //precisa de ser public para o botao reconhecer a funcao
    public void chooseColor(View view) {
        openColorPicker();


        //finish();
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                colorB.setBackgroundColor(color);

                Intent data = getIntent();
                data.putExtra("newColor",
                        String.valueOf(defaultColor));

                setResult(RESULT_OK, data);
                //finish();

            }
        });
        colorPicker.show();
    }



/*    private void loadModeSwitch() {
        Bundle extras = getIntent().getExtras();
        String actualMode = extras.getString("actualMode");


        if (actualMode.equals("night")) {
            themeS.setChecked(true);
        }

    }*/

/*    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        Intent data = getIntent();
        String mode = "";


        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(android.R.style.Theme_Black);
            mode = "night";


        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(android.R.style.Theme);
            mode = "light";
        }


        data.putExtra("mode",
                mode);

        setResult(RESULT_OK, data);


    }*/



    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}