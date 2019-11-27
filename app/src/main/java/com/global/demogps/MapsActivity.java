package com.global.demogps;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton btnFloating;
    private Boolean ubicacion = false;
    private static final String TAG = "MapsActivity";
    public static BottomSheetBehavior sheetBehavior;
    public static LinearLayout btnSheetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        views();
        statusUbicacionUser();
        final SharedPreferences sharedPreferences = getSharedPreferences("Status",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //expandableListView.setVisibility(View.VISIBLE);
                    }
                    break;

                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //expandableListView.setVisibility(View.GONE);
                    }
                    break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }

            }
            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        /*btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ubicacion.equals(sharedPreferences.getBoolean("Estado",false))){
                    ubicacionUsuario();
                    editor.putBoolean("Estado",true);
                    editor.commit();

                }else{
                    Log.e(TAG,"El usuario ya inicio su ubicacion ");
                    Toast.makeText(MapsActivity.this, "CARGA MARCADORES ", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mexico = new LatLng(23.772512, -101.565819);
        mMap.addMarker(new MarkerOptions().position(mexico).title("México").visible(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexico,5));
    }

    public void views(){
        //btnFloating = (FloatingActionButton)findViewById(R.id.btnFloating);
        //btnFloating.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
        btnSheetLayout = (LinearLayout)findViewById(R.id.btnSheetLayout);
        sheetBehavior = BottomSheetBehavior.from(btnSheetLayout);
    }

    public boolean ubicacionUsuario(){
        LatLng mexico = new LatLng(19.44, -99.20);
        mMap.addMarker(new MarkerOptions().position(mexico).title("México"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexico,15));
        return true;
    }

    public void statusUbicacionUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("Status",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Estado",false);
        editor.commit();
    }
}


