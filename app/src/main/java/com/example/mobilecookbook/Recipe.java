package com.example.mobilecookbook;

public class Recipe {
    private String category;
    private String name;
    private String recipe;
    private int kcal;
    private int protein;
    private int sugar;
    private int fat;
    private byte[] image;
    private String ingredients;

    public Recipe() {
    }



    public Recipe(String category, String name, String recipe, int kcal, int protein, int sugar, int fat, byte[] image, String ingredients) {
        this.category = category;
        this.name = name;
        this.recipe = recipe;
        this.kcal = kcal;
        this.protein = protein;
        this.sugar = sugar;
        this.fat = fat;
        this.image = image;
        this.ingredients += ingredients;
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

    public int getProtein() {
        return protein;
    }

    public void setProtein(int salt) {
        this.protein = salt;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
