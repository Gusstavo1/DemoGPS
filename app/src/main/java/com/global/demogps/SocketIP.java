package com.global.demogps;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

import static com.global.demogps.MapsActivity.mMap;
import static com.global.demogps.MapsActivity.webSocketClient;

public class SocketIP {

    public static String TAG = "SocketIP";
    public static String idSocket;
    public static void connectWebSocket(final Context context, String socketUrl, final String datos) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("DatosApp",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        URI uri;
        try {
            uri = new URI(socketUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.e(TAG, "EL SOCKET ESTA ABIERTO: "+webSocketClient.getReadyState().toString());
                webSocketClient.send(datos);
            }
            @Override
            public void onMessage(final String serverMessage) {
                Log.e(TAG,"WebSocket Server Response: "+serverMessage);

                if(serverMessage.startsWith("[{\"img\":")){

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Log.e(TAG,"PARSEANDO");
                                JSONArray jsonArray = new JSONArray(serverMessage);
                                JSONObject jsonData = null;
                                for(int i = 0; i<jsonArray.length(); i++){

                                    jsonData = jsonArray.getJSONObject(i);
                                    String lng = jsonData.getString("lng");
                                    String lat = jsonData.getString("lat");
                                    String name = jsonData.getString("nombre");
                                    float newLng = Float.parseFloat(lng);
                                    float newLat = Float.parseFloat(lat);

                                    Log.e(TAG,"Lng "+lng+" && Lat "+lat);
                                    Log.e(TAG,"Name marker "+name);
                                    LatLng userUbication = new LatLng(newLat, newLng);

                                    mMap.addMarker(new MarkerOptions().position(userUbication).title(name));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userUbication,5));
                                    //Toast.makeText(context,  webSocketClient.getReadyState().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }catch (Exception e){
                                Log.e(TAG,"Exception "+e);
                            }
                        }
                    });

                }else{
                    try {
                        JSONObject jsonResponse = new JSONObject(serverMessage);
                        for(int i = 0; i<jsonResponse.length(); i++){
                            idSocket = jsonResponse.getString("idSocket");
                            editor.putString("idSocket",idSocket);
                            editor.commit();
                        }
                        Log.e(TAG,"ID SOCKET "+idSocket);
                        CreateJSon.gpsCompartido(idSocket);
                        //gpsShared(idSocket);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e(TAG, "Websocket CERRADO: " + s);
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Websocket ERROR: " + e.getMessage());
            }
        };

        if (socketUrl.indexOf("wss") == 0)
        {
            try {
                SSLContext sslContext = SSLContext.getDefault();
                webSocketClient.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sslContext));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        webSocketClient.connect();
    }

}
