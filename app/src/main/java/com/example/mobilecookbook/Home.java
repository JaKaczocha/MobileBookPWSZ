package com.example.mobilecookbook;

public class Home {
    private String name; // information about each of the pizzas is known by name and resource ID
    private int imageResourceId;

    public static final Food[] home = {
            new Food("Spaghetti bolognese", R.drawable.spag_boli,""),
            new Food("zupa krem", R.drawable.cream_soup,""),
            new Food("Diavolo", R.drawable.diavolo, "abcabc chleba chece"),
            new Food("Funghi", R.drawable.funghi,""),
            new Food( "Spaghetti bolognese", R.drawable.spag_boli,""),
            new Food( "Lasagne", R.drawable.lasagne,"")
    };
    public Home(String name, int imageResourceId) { // constructor
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
