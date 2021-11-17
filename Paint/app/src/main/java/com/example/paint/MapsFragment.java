package com.example.paint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.api.LogDescriptor;

public class MapsFragment extends Fragment {

    public GoogleMap gm;

    private double latitude;
    private double longitude;

    LatLng currentLocation;
    LatLng initialLocation;
    LatLng pastLocation;


    Boolean firstTime;

    SupportMapFragment mapFragment;





    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        GoogleMap teste;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            gm = googleMap;
            //currentLocation = new LatLng(latitude, longitude);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,20));

            if (firstTime){
                googleMap.addMarker(new MarkerOptions().position(currentLocation).title("My initial Location"));
                firstTime = false;
                pastLocation = currentLocation;
            }
            else {

                //Toast.makeText(MapsFragment.this.getActivity(), "LatitudeVelha", Toast.LENGTH_SHORT).show();
                /*Toast.makeText(MapsFragment.this.getActivity(), "LatitudeVelha" + String.valueOf(pastLocation.latitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsFragment.this.getActivity(), "longitudeVelha" + String.valueOf(pastLocation.longitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsFragment.this.getActivity(), "LatitudeNova" + String.valueOf(currentLocation.latitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsFragment.this.getActivity(), "LongitudeNova" + String.valueOf(currentLocation.longitude), Toast.LENGTH_SHORT).show();
*/

                googleMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                               pastLocation, currentLocation));

                pastLocation = currentLocation;


            }

        }


    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        firstTime = true;
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        latitude = getArguments().getDouble("latitude");
        Log.d("latitudeMAPA", String.valueOf(latitude));



        longitude = getArguments().getDouble("longitude");
        Log.d("longitudeMAPA", String.valueOf(longitude));

        initialLocation = new LatLng(latitude, longitude);
        currentLocation = initialLocation;



        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);


        }


    }


    public void teste (double latitude, double longitude){
        Log.d("NOVo ponto", "NewStartLocation: ");

        Log.d("Velha pastLocation", pastLocation.toString());

        currentLocation = new  LatLng(latitude, longitude);

        //currentLocation = new  LatLng(40, 10);

        mapFragment.getMapAsync(callback);


        Log.d("Nova currentLocation", currentLocation.toString());

    }



    public void NewStartLocation(double latitude, double longitude) {

        Log.d("NOVA LOCA", "NewStartLocation: ");
        Log.d("Velha pastLocation", pastLocation.toString());

        pastLocation = new LatLng(latitude, longitude);

        Log.d("NovapastLocation/inicio", pastLocation.toString());




    }
}