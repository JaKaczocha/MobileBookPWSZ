package com.example.mobilecookbook;

public class Category {
    private String name;
    private byte[] image;

    public Category(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }
}