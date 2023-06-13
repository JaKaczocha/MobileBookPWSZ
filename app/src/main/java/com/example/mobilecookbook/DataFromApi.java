package com.example.mobilecookbook;

public class DataFromApi {

    DataFromApi(String nazwa, String pictureLink, String instruction, String ingredients,
                String protein, String fat, String sugar, String calories, String carbohydrates) {
        this.pictureLink = pictureLink;
        this.nazwa = nazwa;
        this.instruction = instruction;
        this.calories = calories;
        this.ingredients = ingredients;
        this.sugar = sugar;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;

    }

    String pictureLink;
    String nazwa;
    String instruction;
    String ingredients;
    String protein;
    String fat;
    String sugar;
    String calories;
    String carbohydrates;
}
