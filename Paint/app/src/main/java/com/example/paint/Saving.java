package com.example.paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Saving extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference savedCanvas;
    private ArrayList<Path> path;
    private String canvasToDelete;
    private int canvasIndexToDelete;


    public  Saving (){

    }

    public Saving(ArrayList<Path> p) {
        path = p;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);


        db = FirebaseFirestore.getInstance();

        //ele cria ou vai buscar automaticamente
        savedCanvas = db.collection("savedCanvas");


        loadListDraws();

    }




    public void loadListDraws() {

        ArrayList<String> names = new ArrayList<>();
        Context atual = this;
        ListView listV = findViewById(R.id.drawNames);


        savedCanvas.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        names.add(document.getId());
                        //System.out.println(document.getId());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(atual,
                            android.R.layout.simple_list_item_1, names);

                    listV.setAdapter(adapter);

                    listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String itemValue = (String) listV.getItemAtPosition(position);

                            Intent data = getIntent();
                            data.putExtra("NameDrawToLoad",
                                    itemValue);

                            setResult(RESULT_OK, data);
                            finish();

                        }


                    });

                    listV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                       int pos, long id) {
                            canvasToDelete = (String) listV.getItemAtPosition(pos);
                            canvasIndexToDelete = pos;
                            askToDelete(true);

                            return true;
                        }
                    });
                }
            }
        });
    }



    public void askToDelete(boolean show) {


        ConstraintLayout popUp = findViewById(R.id.deletePopUp);

        if (show)
            popUp.setVisibility(View.VISIBLE);


        else
            popUp.setVisibility(View.GONE);


    }

    public void deleteCanvas(View v) {

        //ListView listV = findViewById(R.id.drawNames);
        //listV.removeViewAt(canvasIndexToDelete);

        //Log.d("a apagar", canvasToDelete);


        Intent data = getIntent();
        data.putExtra("NameDrawToDelete",
                canvasToDelete);

        setResult(RESULT_OK, data);
        finish();

    }

    public void cancelDeleteCanvas(View v) {
        askToDelete(false);
    }


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
