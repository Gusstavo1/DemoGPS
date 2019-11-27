package com.global.demogps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private AlertDialog dialogBuilder;
    private View dialogView;
    private  RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        views();
        statusUbicacionUser();
        final SharedPreferences sharedPreferences = getSharedPreferences("Status",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //customBoton();
                if(ubicacion.equals(sharedPreferences.getBoolean("Estado",false))){
                    ubicacionUsuario();
                    editor.putBoolean("Estado",true);
                    editor.commit();

                }else{
                    Log.e(TAG,"El usuario ya inicio su ubicacion ");
                    Toast.makeText(MapsActivity.this, "CARGA MARCADORES ", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        btnFloating.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
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


    /*
    public void customBoton(){

        dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.gps_options, null);
        radioGroup = (RadioGroup)dialogView.findViewById(R.id.RadioGroupminutos);
        final RadioButton radioButton15 = (RadioButton)dialogView.findViewById(R.id.radio15);
        final RadioButton radioButton30 = (RadioButton)dialogView.findViewById(R.id.radio30);
        final RadioButton radioButton60 = (RadioButton)dialogView.findViewById(R.id.radio60);
        Button btnAceptar = (Button)dialogView.findViewById(R.id.btnAceptar);
        Button btnCancel = (Button)dialogView.findViewById(R.id.btnCancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Click", Toast.LENGTH_SHORT).show();
                if(radioButton15.isChecked()){
                    Log.e(TAG,"RB ");
                    Toast.makeText(getApplicationContext(), "Tu ubicación será pública por 15 minutos", Toast.LENGTH_SHORT).show();
                }else if(radioButton30.isChecked()){

                    Toast.makeText(getApplicationContext(), "Tu ubicación será pública por 30 minutos", Toast.LENGTH_SHORT).show();
                }else if(radioButton60.isChecked()){
                    Toast.makeText(getApplicationContext(), "Tu ubicación será pública por 60 minutos", Toast.LENGTH_SHORT).show();
                }
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }*/


    public void rbMethod(View v){
        //int radioButtonId = dialogView.
    }

}


