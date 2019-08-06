package com.example.heads_up.activities.Activity_Juego;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.heads_up.R;
import com.example.heads_up.activities.Activity_Main.Activity_Main;
import com.example.heads_up.activities.Activity_Result.Activity_Result;
import com.example.heads_up.models.Category;

import java.util.HashMap;
import java.util.Random;

public class Activity_Juego extends AppCompatActivity implements SensorEventListener{
    private TextView countdownText;

    Handler handler;

    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 60000; //1 min
    private boolean timerRunning;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private SensorEventListener eventListener;

    boolean eventFlag = false;
    int EVENT_DELAY = 2000;

    private HashMap<String, Integer> estado =  new HashMap<>();
    private String[] words;
    private TextView currentWord;

    private final Runnable processSensors = new Runnable() {
        @Override
        public void run() {
            eventFlag = true;

            handler.postDelayed(this, EVENT_DELAY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        handler = new Handler();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        final Category category = (Category) getIntent().getSerializableExtra("Category");
        final TextView categoryText = (TextView) findViewById(R.id.game_category_text);

        categoryText.setText(category.getCategoryName());

        estado.put("correctas", 0);
        estado.put("palabraActual", 0);

        words = RandomizeArray(category.getCategoryContent());

        countdownText = (TextView) findViewById(R.id.timer_juego);

        initListeners();

        currentWord = findViewById(R.id.current_category_word);
        currentWord.setText(words[estado.get("palabraActual")]);

        startTimer();
        updateTimer();
    }

    public void FinishGame() {
        Intent intentResult = new Intent(Activity_Juego.this, Activity_Result.class);
        intentResult.putExtra("correctas", estado.get("correctas"));
        intentResult.putExtra("categoryLength", words.length);
        startActivity(intentResult);

        finish();
    }

    public String[] RandomizeArray(String[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i< array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            String temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public void initListeners()
    {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);

        handler.post(processSensors);
    }

    @Override
    public void onDestroy()
    {
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        sensorManager.unregisterListener(this);
        super.onBackPressed();
    }

    @Override
    public void onResume()
    {
        initListeners();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    float[] inclineGravity = new float[3];
    float[] mGravity;
    float[] mGeomagnetic;
    float orientation[] = new float[3];
    float pitch;
    float roll;

    @Override
    public void onSensorChanged(SensorEvent event) {
        //If type is accelerometer only assign values to global property mGravity
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = event.values;
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            mGeomagnetic = event.values;
            if (isTiltDownward() && eventFlag)
            {
                eventFlag = false;
                Log.d("aaa", "onSensorChanged: " + estado.get("palabraActual"));
                if(estado.get("palabraActual") + 1 == words.length){
                    FinishGame();
                } else {
                    estado.put("palabraActual", estado.get("palabraActual") + 1);
                    estado.put("correctas", estado.get("correctas") + 1);

                    currentWord.setText(words[estado.get("palabraActual")]);
                }
            }
            else if (isTiltUpward() && eventFlag)
            {
                eventFlag = false;

                if(estado.get("palabraActual") + 1 == words.length){
                    FinishGame();
                } else {
                    estado.put("palabraActual", estado.get("palabraActual") + 1);

                    currentWord.setText(words[estado.get("palabraActual")]);
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    public boolean isTiltUpward()
    {
        if (mGravity != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on ground or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 30 && inclination < 40))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean isTiltDownward()
    {
        if (mGravity != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on groud or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 140 && inclination < 170))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
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
                FinishGame();
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
