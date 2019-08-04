package com.example.heads_up.activities.Activity_Main;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;

import com.example.heads_up.R;
import com.example.heads_up.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryListViewAdapter extends ArrayAdapter<String>{
    private Context context;
    private ArrayList<Category> categories;

    CategoryListViewAdapter (Context c, ArrayList<Category> categoryArray) {
        super(c, R.layout.row);
        this.context = c;
        this.categories = categoryArray;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view = inflater.inflate(R.layout.row, null);

        ImageView images = view.findViewById(R.id.image);

        Picasso.get().load(categories.get(position).getImageUrl()).into(images);

        return view;
    }
}
