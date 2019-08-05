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
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class CategoryListViewAdapter extends ArrayAdapter<Category>{
    private Context context;
    private ArrayList<Category> categories;

    CategoryListViewAdapter (Context c, ArrayList<Category> categoryArray) {
        super(c, R.layout.row);
        this.context = c;
        this.categories = categoryArray;
    }

    @Override
    public int getCount() {
        return categories.toArray().length;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.row, null);

        ImageView images = view.findViewById(R.id.image);

        String imageURL = categories.get(position).getImageURL();

        final int radius = 30;
        final int margin = 25;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);

        Picasso.get().load(imageURL).resize(800,400).transform(transformation).placeholder(R.drawable.skeleton_category).into(images);

        return view;
    }
}
