package com.global.demogps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.java_websocket.client.WebSocketClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    static GoogleMap mMap;
    public static FloatingActionButton btnFloating;
    private Boolean ubicacion = false;
    private static final String TAG = "MapsActivity";
    public static BottomSheetBehavior sheetBehavior;
    public static LinearLayout btnSheetLayout;
    public static Button btnGps15, btnGps30, btnGps60, btnGps24hrs;
    public CountDownTimer countDownTimer;
    public static WebSocketClient webSocketClient;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.content);
        views();
        PostRequest.postRequest(this);
        socketConnect();

        MapsActivity.sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        statusUbicacionUser();
        sharedPreferences = getSharedPreferences("DatosApp",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:

                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(ubicacion.equals(sharedPreferences.getBoolean("Estado",false))){
                    ubicacionUsuario();
                    editor.putBoolean("Estado",true);
                    editor.commit();

                }else{
                    Log.e(TAG,"El usuario ya inicio su ubicacion ");
                    Toast.makeText(MapsActivity.this, "CARGA MARCADORES ", Toast.LENGTH_SHORT).show();
                }*/
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

        btnFloating = (FloatingActionButton)findViewById(R.id.btnFloating);
        btnFloating.setBackgroundTintList(getResources().getColorStateList(R.color.floating));
        btnSheetLayout = (LinearLayout)findViewById(R.id.btnSheetLayout);
        sheetBehavior = BottomSheetBehavior.from(btnSheetLayout);

        btnGps15 = (Button)findViewById(R.id.btn15);
        btnGps30 = (Button)findViewById(R.id.btn30);
        btnGps60 = (Button)findViewById(R.id.btn60);
        btnGps24hrs = (Button)findViewById(R.id.btn24hrs);

        btnGps15.setOnClickListener(this);
        btnGps30.setOnClickListener(this);
        btnGps60.setOnClickListener(this);
        btnGps24hrs.setOnClickListener(this);
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

    public void fullScreen(){
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn15:

                /*btnGps15.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction()==MotionEvent.ACTION_DOWN)
                        {

                            btnGps15.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                            String mensaje = "El gps será publico por 15 minutos";
                            mensaje(mensaje,Timer.fifteenMinutes);
                        }

                        if(event.getAction()==MotionEvent.ACTION_UP)
                        {
                            btnGps15.setBackgroundColor(getResources().getColor(R.color.backgroundButton));
                        }
                        return false;
                    }
                });*/

                btnGps15.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                //btnGps15.setTextColor(Color.parseColor("#FFF44336"));
                btnGps15.setClickable(true);
                btnGps30.setClickable(false);
                btnGps30.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps60.setEnabled(false);
                btnGps60.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps24hrs.setEnabled(false);
                btnGps24hrs.setTextColor(Color.parseColor("#FFFFFF"));
                Log.e(TAG,"Click 15");

                //Timer.timer(this,ConstantsTimer.fifteenMinutes);

                break;
            case R.id.btn30:

                /*btnGps30.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if(event.getAction()==MotionEvent.ACTION_DOWN)
                        {
                            String mensaje = "El gps será publico por 30 minutos";
                            btnGps30.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                            mensaje(mensaje,Timer.thirtyMinutes);
                        }

                        if(event.getAction()==MotionEvent.ACTION_UP)
                        {
                            btnGps30.setBackgroundColor(getResources().getColor(R.color.backgroundButton));
                        }
                        return false;
                    }
                });*/

                btnGps30.setClickable(true);
                btnGps30.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                //btnGps30.setTextColor(Color.parseColor("#FFF44336"));
                btnGps15.setClickable(false);
                btnGps15.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps60.setEnabled(false);
                btnGps60.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps24hrs.setEnabled(false);
                btnGps24hrs.setTextColor(Color.parseColor("#FFFFFF"));

                //Timer.timer(this,ConstantsTimer.thirtyMinutes);


                Log.e(TAG,"Click 30");
                break;
            case R.id.btn60:
                btnGps60.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                //btnGps60.setTextColor(Color.parseColor("#FFF44336"));
                btnGps60.setEnabled(true);
                btnGps15.setEnabled(false);
                btnGps15.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps30.setEnabled(false);
                btnGps30.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps24hrs.setEnabled(false);
                btnGps24hrs.setTextColor(Color.parseColor("#FFFFFF"));

                //Timer.timer(this,ConstantsTimer.sixtyMinutes);

                Log.e(TAG,"Click 60");
                break;
            case R.id.btn24hrs:
                btnGps24hrs.setBackgroundColor(getResources().getColor(R.color.changeBackgroundButton));
                //btnGps24hrs.setTextColor(Color.parseColor("#FFF44336"));
                btnGps24hrs.setEnabled(true);
                btnGps15.setEnabled(false);
                btnGps15.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps60.setEnabled(false);
                btnGps60.setTextColor(Color.parseColor("#FFFFFF"));
                btnGps30.setEnabled(false);
                btnGps30.setTextColor(Color.parseColor("#FFFFFF"));

                //Timer.timer(this,ConstantsTimer.day);

                Log.e(TAG,"Click 1 day");
                break;
        }
    }

    public void socketConnect(){
        CreateJSon.inicializar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destruyendo activity", Toast.LENGTH_SHORT).show();
        webSocketClient.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webSocketClient.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webSocketClient.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "Lanzando activity", Toast.LENGTH_SHORT).show();
        socketConnect();
        Log.e(TAG,webSocketClient.getReadyState().toString());
    }

    public void mensaje(String mensajePrueba , long tiempo){
        Toast.makeText(this, mensajePrueba, Toast.LENGTH_SHORT).show();
        Log.e(TAG,"Tiempo en ms "+tiempo);

    }


}


