package com.example.heads_up.models;

import java.io.Serializable;

public class Category implements Serializable {

    private int id;
    private String categoryName;
    private String[] categoryContent;
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String[] getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String[] categoryContent) {
        this.categoryContent = categoryContent;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
