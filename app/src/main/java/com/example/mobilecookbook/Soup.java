package com.example.mobilecookbook;

public class Soup {
    private String name; // information about each of the pizzas is known by name and resource ID
    private int imageResourceId;

    public static final Food[] soup = {
            new Food("grzybowa", R.drawable.ramen),
            new Food("zupa krem", R.drawable.cream_soup),
            new Food( "zupa", R.drawable.ramen),
            new Food( "bog Lasagne", R.drawable.clearchicken),
            new Food( "Spaghetti bolognese", R.drawable.cream_soup),
            new Food( "clear chicken", R.drawable.clearchicken)
    };
    public Soup(String name, int imageResourceId) { // constructor
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
