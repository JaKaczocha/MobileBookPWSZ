package com.example.mobilecookbook;

public class Pasta {
    private String name; // information about each of the pizzas is known by name and resource ID
    private int imageResourceId;

   public static final Food[] pasta = {
            new Food("Spaghetti bolognese", R.drawable.spag_boli),
            new Food("Lasagne", R.drawable.lasagne),
            new Food( "Spaghetti", R.drawable.lasagne),
            new Food( "bog Lasagne", R.drawable.spag_boli),
            new Food( "Spaghetti bolognese", R.drawable.spag_boli),
            new Food( "Lasagne", R.drawable.lasagne)
    };
    public Pasta(String name, int imageResourceId) { // constructor
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
