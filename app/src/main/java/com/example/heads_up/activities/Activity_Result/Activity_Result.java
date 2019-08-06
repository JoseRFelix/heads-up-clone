package com.example.heads_up.activities.Activity_Result;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        final TextView correctAnswersText = (TextView) findViewById(R.id.number_of_correct_strings);
        final TextView incorrectAnswersText = (TextView) findViewById(R.id.number_of_skipped_strings);

        Log.d("aaa", "onCreate: " + correctAnswers);

        correctAnswersText.setText(correctAnswers + "");
        incorrectAnswersText.setText(Math.abs(correctAnswers - categoryLength) + "");
    }
}
