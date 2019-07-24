package com.example.heads_up.models;

public class Category {

    private int id;
    private String categoryName;
    private String[] categoryContent;

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
