package com.example.mobilecookbook;

public class Pizza {
    private String name; // information about each of the pizzas is known by name and resource ID
    private int imageResourceId;

    public static final Food[] pizzas = {
            new Food("Diavolo", R.drawable.diavolo, "abcabc chleba chece"),
            new Food("Funghi", R.drawable.funghi,""),
            new Food( "Margarita", R.drawable.pizza2,""),
            new Food( "ateńska", R.drawable.funghi,""),
            new Food( "ULALA", R.drawable.pizza1,""),
            new Food( "owoce morza", R.drawable.pizza3,"")

    };
    private Pizza(String name, int imageResourceId) { // constructor
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
