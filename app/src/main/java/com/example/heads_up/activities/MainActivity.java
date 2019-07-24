package com.example.heads_up.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.heads_up.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
