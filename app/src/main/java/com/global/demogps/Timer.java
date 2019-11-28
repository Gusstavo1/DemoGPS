package com.global.demogps;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import static com.global.demogps.MapsActivity.btnFloating;

public class Timer {

    public static int counter;
    public static String TAG = "Timer";

    public static void timer(final Context context, long time){

        new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Entro timer "+counter++,Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onFinish() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnFloating.setBackgroundColor(context.getResources().getColor(R.color.floating));
                    }
                });
            }
        }.start();
    }
}
