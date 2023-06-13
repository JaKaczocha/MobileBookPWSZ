package com.example.mobilecookbook;

import java.util.ArrayList;

public class Food {

    private String name; // information about each of the pizzas is known by name and resource ID
    private int imageResourceId;

    public static ArrayList<Food> food = new ArrayList<Food>();
    private static String type = "home";

    public Food(String name, int imageResourceId,String recipe) { // constructor
        this.name = name + "\n\n" + recipe;
        this.imageResourceId = imageResourceId;
    }
    public static void setType(String type1 ) {
        type = type1;
    }
    public static String getType() {
        return  type;
    }
    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
