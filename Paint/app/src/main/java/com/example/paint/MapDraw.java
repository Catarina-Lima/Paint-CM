package com.example.paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

public class MapDraw extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    MapsFragment mf;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationRequest locationRequest;

    Switch drawingSwitch;

    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        drawingSwitch = findViewById(R.id.drawing);
        drawingSwitch.setOnCheckedChangeListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MapDraw.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MapDraw.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {



            if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(MapDraw.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MapDraw.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                Log.d("1", "onSuccess: ");
                if (location != null) {
                    Log.d("2", "onSuccess: ");
                    FragmentManager fm = getSupportFragmentManager();

                    mf =  new MapsFragment();

                    Bundle bundle = new Bundle();

                    bundle.putDouble("latitude", location.getLatitude());
                    //Log.d("latitude", String.valueOf(location.getLatitude()));

                    bundle.putDouble("longitude", location.getLongitude());
                    //Log.d("longitude", String.valueOf(location.getLongitude()));
                    mf.setArguments(bundle);


                    FragmentTransaction ftt = fm.beginTransaction();


                    ftt.add(R.id.mapFragment, mf);
                    ftt.commit();
                }
            }
        });

        /*fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(this, new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                Log.d("1", "onSuccess: ");
                if (location != null) {
                    Log.d("2", "onSuccess: ");
                    FragmentManager fm = getSupportFragmentManager();


                     mf =  new MapsFragment();

                    Bundle bundle = new Bundle();

                    bundle.putDouble("latitude", location.getLatitude());
                    Log.d("latitude", String.valueOf(location.getLatitude()));

                    bundle.putDouble("longitude", location.getLongitude());
                    Log.d("longitude", String.valueOf(location.getLongitude()));
                    mf.setArguments(bundle);


                    FragmentTransaction ftt = fm.beginTransaction();


                    ftt.add(R.id.mapFragment, mf);
                    ftt.commit();
                }
            }
        });*/



        createLocationRequest();


/*
        try {
            TimeUnit.SECONDS.sleep(5);

            LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mf.teste(location.getLatitude(), location.getLongitude());
                }
            }
        };

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/




        /*CountDownTimer cd = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {


                LocationCallback locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            mf.teste(location.getLatitude(), location.getLongitude());
                        }
                    }
                };


                if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                    if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }else{
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }


                if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {



                    if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)){
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }else{
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }

                fusedLocationClient.requestLocationUpdates(locationRequest,
                        locationCallback,
                        Looper.getMainLooper());

            }
        }.start();*/


        /*LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mf.teste(location.getLatitude(), location.getLongitude());
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());*/









/*
        FragmentManager fm = getSupportFragmentManager();


        MapsFragment m=  new MapsFragment();

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", 38);
        bundle.putDouble("longitude", 9);
        m.setArguments(bundle);


        FragmentTransaction ftt = fm.beginTransaction();


        ftt.add(R.id.mapFragment, m);
        ftt.commit();
*/







    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MapDraw.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();








                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }






    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        Intent data = getIntent();
        String mode = "";


        if (isChecked) {

            drawingSwitch.setText("Stop Drawing");

            if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(MapDraw.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    ActivityCompat.requestPermissions(MapDraw.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }


            if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)){
                    ActivityCompat.requestPermissions(MapDraw.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }else{
                    ActivityCompat.requestPermissions(MapDraw.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }





        /*    fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }
            }).addOnSuccessListener(this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    Log.d("1", "onSuccess: ");
                    if (location != null) {

                        mf.NewStartLocation(location.getLatitude(), location.getLongitude());

                    }
                }
            });*/



            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    Log.d("1", "onSuccess: ");
                    if (location != null) {

                        mf.NewStartLocation(location.getLatitude(), location.getLongitude());
                        Log.d("NOVA LOCALIZACAO", "onSuccess: ");

                    }
                }
            });




            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    /*for (Location location : locationResult.getLocations()) {
                        Log.d("NOVO PONTO", "onLocationResult: ");
                        mf.teste(location.getLatitude(), location.getLongitude());
                    }*/

                        Location location = locationResult.getLastLocation();
                        mf.teste(location.getLatitude(), location.getLongitude());


                       /* Log.d("Locations", currentLocation.getLatitude() +"," +currentLocation.getLongitude());
                        //ToDO Publish Location*/




                    return;
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());


            /*CountDownTimer cd = new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {



                if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {


                    if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }else{
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }


                if (ActivityCompat.checkSelfPermission(MapDraw.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {



                    if (ActivityCompat.shouldShowRequestPermissionRationale(MapDraw.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)){
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }else{
                        ActivityCompat.requestPermissions(MapDraw.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }

                fusedLocationClient.requestLocationUpdates(locationRequest,
                        locationCallback,
                        Looper.getMainLooper());

            }
        }.start();



*/

        }
        else {

            drawingSwitch.setText("Start Drawing");

            fusedLocationClient.removeLocationUpdates(locationCallback);
        }




    }



}