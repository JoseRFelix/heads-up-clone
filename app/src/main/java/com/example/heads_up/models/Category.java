package com.example.heads_up.models;

public class Category {

    private int id;
    private String categoryName;
    private String[] categoryContent;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
