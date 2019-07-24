package com.example.heads_up.services;

import com.example.heads_up.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    String API_ROUTE = "/Jfelix61/heads-up-json-server/categories";

    @GET(API_ROUTE)
    Call<List<Category>> getCategories();
}
