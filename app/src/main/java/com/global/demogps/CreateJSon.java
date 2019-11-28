package com.global.demogps;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.global.demogps.MapsActivity.webSocketClient;

public class CreateJSon {
    public static String TAG = "CreateJson";

    public static void inicializar(Context context){

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("inicializar",true);
            jsonObject.put("url_plataforma","https://viirm.39zm.sedena360.ml/15rcm");
            jsonObject.put("idUsuario","5566163068");
            String dataJson = jsonObject.toString();
            SocketIP.connectWebSocket(context,context.getString(R.string.socketIp),dataJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void gpsCompartido(String idSocket){
        try {
            JSONObject jsonGPS = new JSONObject();
            jsonGPS.put("listar_usuarios",true);
            //jsonGPS.put("url_plataforma","https://viirm.39zm.sedena360.ml/15rcm");
            jsonGPS.put("url_plataforma","https://viict.guardianacional360.ml/cuartelgeneral");
            jsonGPS.put("idUsuario","5566163068");
            jsonGPS.put("idSocket",idSocket);
            webSocketClient.send( jsonGPS.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject jsonGpsVehicular(){
        JSONObject gpsVehicular = new JSONObject();
        try {
            gpsVehicular.put("idUsuarios_movil","5566163068");
            gpsVehicular.put("fecha","2019-11-28");
            gpsVehicular.put("hora","10:00");
            gpsVehicular.put("latitud","19.441777");
            gpsVehicular.put("longitud","-99.203789");
            gpsVehicular.put("gps_publico","true");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"JSON "+gpsVehicular.toString());
        return gpsVehicular;
    }
}
