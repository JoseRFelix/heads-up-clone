package com.example.heads_up.activities.Activity_Juego;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.heads_up.R;
import com.example.heads_up.activities.Activity_Main.Activity_Main;
import com.example.heads_up.activities.Activity_Main.CategoryListViewAdapter;

public class Activity_Juego extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton;

    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 60000; //1 min
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        countdownText = findViewById(R.id.timer);
        countdownButton = findViewById(R.id.countdownButton);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();
    }

    public void startStop() {
        if(timerRunning){
            stopTimer();
        } else {
            startTimer();
        }
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

        countdownButton.setText("PAUSAR");
        timerRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        countdownButton.setText("SEGUIR");
        timerRunning  = false;
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
