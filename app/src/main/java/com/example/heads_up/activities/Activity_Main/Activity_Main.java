package com.example.heads_up.activities.Activity_Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.heads_up.R;
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
    ListView list;
    ArrayList<Category> categoryArray = new ArrayList<>();
    CategoryListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CategoryListViewAdapter(Activity_Main.this, categoryArray);
        list = findViewById(R.id.category_list);

        list.setAdapter(adapter);

        getCategories();
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
