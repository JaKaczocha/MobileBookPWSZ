package com.example.mobilecookbook;

public class Recipe {
    private String category;
    private String name;
    private String recipe;
    private int kcal;
    private int salt;
    private int sugar;
    private int fat;
    private byte[] image;

    public Recipe() {
    }

    public Recipe(String category, String name, String recipe, int kcal, int salt, int sugar, int fat, byte[] image) {
        this.category = category;
        this.name = name;
        this.recipe = recipe;
        this.kcal = kcal;
        this.salt = salt;
        this.sugar = sugar;
        this.fat = fat;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
