package com.example.wido.tpservice;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.hardware.Sensor.TYPE_ACCELEROMETER;

public class Flash extends Service implements SensorEventListener {

    Camera camera;
    SensorManager sensorManager;
    Sensor accelerometre;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometre = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);

        }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notifIntent, 0);
        PendingIntent pendIntent =
                PendingIntent.getBroadcast(this, 0, new Intent("PlayPause"),FLAG_UPDATE_CURRENT);

        Notification notification =
                new Notification.Builder(this)
                        .setContentTitle("Service is on")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .addAction(R.drawable.ic_launcher_background, "start| stop", pendIntent)
                        .setContentIntent(pendingIntent)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .build();

        startForeground(200, notification);

        sensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gx = x / SensorManager.GRAVITY_EARTH;
            float gy = y / SensorManager.GRAVITY_EARTH;
            float gz = z / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(gx*gx + gy*gy + gz*gz);
            Toast.makeText(getApplicationContext(), "flash is on", Toast.LENGTH_SHORT).show();

            if (gForce > 2.25){
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}