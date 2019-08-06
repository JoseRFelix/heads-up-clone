package com.example.heads_up.activities.Activity_Main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.heads_up.R;
import com.example.heads_up.activities.Activity_Juego.Activity_Juego;
import com.example.heads_up.models.Category;
import com.example.heads_up.services.CategoryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Main extends AppCompatActivity {
    final long TIMER_TIME = 5000;  //5 seg

    ListView list;
    ArrayList<Category> categoryArray = new ArrayList<>();
    Category selectedCategory;
    CategoryListViewAdapter adapter;

    private MediaPlayer mpCountdownTick;
    private MediaPlayer mpGo;

    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = TIMER_TIME;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownText = findViewById(R.id.countdownText);

        list = findViewById(R.id.category_list);
        list.setDivider(null);

        adapter = new CategoryListViewAdapter(Activity_Main.this, categoryArray);

         mpCountdownTick = MediaPlayer.create(this, R.raw.countdowntick);
         mpGo = MediaPlayer.create(this, R.raw.go);

        list.setAdapter(adapter);

        getCategories();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryArray.get(position);
                startTimer();
                updateTimer();
            }
        });
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
                countdownText.setText("Start!");

                mpGo.seekTo(0);
                mpGo.start();

                timeLeftinMilliseconds = TIMER_TIME;

                Intent intentJuego = new Intent(Activity_Main.this, Activity_Juego.class);
                intentJuego.putExtra("Category", selectedCategory);
                startActivity(intentJuego);

                countdownText.setText("");
            }
        }.start();

        timerRunning = true;
    }

    public void updateTimer () {
        int seconds = (int) timeLeftinMilliseconds % 5000 / 1000;

        mpCountdownTick.seekTo(0);
        mpCountdownTick.start();


        String timeLeftText;
        if(seconds == 0){
            timeLeftText = "Start!";
        } else {
           timeLeftText = "" + seconds;
        }

        countdownText.setText(timeLeftText);
    }

    private void getCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoryService categoryService = retrofit.create(CategoryService.class);
        Call<List<Category>> call = categoryService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                for(Category category : response.body()) {
                    categoryArray.add(category);
                }

               adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
    }
}
