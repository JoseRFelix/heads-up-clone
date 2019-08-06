package com.example.heads_up.activities.Activity_Juego;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heads_up.R;
import com.example.heads_up.activities.Activity_Main.Activity_Main;
import com.example.heads_up.activities.Activity_Main.CategoryListViewAdapter;

public class Activity_Juego extends AppCompatActivity {
    private TextView countdownText;

    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 60000; //1 min
    private boolean timerRunning;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor == null) {
            Toast.makeText(this, "The service has no gyroscope", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //AQUI SE AGREGA LA CONDICION PARA EL GIROSCOPIO
                if (sensorEvent.values[1] > 0.5f){
                    //SE CAMBIA EL TEXTO
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };




        countdownText = findViewById(R.id.timer);


        startTimer();

        updateTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }



    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftinMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftinMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
            countdownText.setText("¡Se acabó el tiempo!");
            }
        }.start();

        timerRunning = true;
    }


    public void updateTimer () {
        int minutes = (int) timeLeftinMilliseconds/60000;
        int seconds = (int) timeLeftinMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10) timeLeftText +="0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

}
