package com.example.heads_up.activities.Activity_Result;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heads_up.R;

public class Activity_Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final int correctAnswers = (int) getIntent().getSerializableExtra("correctas");
        final int categoryLength = (int) getIntent().getSerializableExtra("categoryLength");
        final MediaPlayer mpApplause = MediaPlayer.create(this, R.raw.applause);

        final TextView correctAnswersText = (TextView) findViewById(R.id.number_of_correct_strings);
        final TextView incorrectAnswersText = (TextView) findViewById(R.id.number_of_skipped_strings);
        final Button goBack = findViewById(R.id.activity_result_go_back);

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        correctAnswersText.setText(correctAnswers + "");
        incorrectAnswersText.setText(Math.abs(correctAnswers - categoryLength) + "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
