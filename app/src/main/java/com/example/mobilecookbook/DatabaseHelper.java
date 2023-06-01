package com.example.mobilecookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_RECIPE = "recipe";
    private static final String COLUMN_KCAL = "kcal";
    private static final String COLUMN_PROTEIN = "protein";
    private static final String COLUMN_SUGAR = "sugar";
    private static final String COLUMN_FAT = "fat";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_INGREDIENTS = "ingredients"; // Nowa kolumna

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_CATEGORY + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_RECIPE + " TEXT," +
                COLUMN_KCAL + " INTEGER," +
                COLUMN_PROTEIN + " INTEGER," +
                COLUMN_SUGAR + " INTEGER," +
                COLUMN_FAT + " INTEGER," +
                COLUMN_IMAGE + " BLOB," +
                COLUMN_INGREDIENTS + " TEXT" + // Nowa kolumna
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Dodaj kolumnę "PROTEIN"
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_PROTEIN + " INTEGER");
        }
    }

    public long addRecipe(Recipe recipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, recipe.getCategory());
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_RECIPE, recipe.getRecipe());
        values.put(COLUMN_KCAL, recipe.getKcal());
        values.put(COLUMN_PROTEIN, recipe.getProtein());
        values.put(COLUMN_SUGAR, recipe.getSugar());
        values.put(COLUMN_FAT, recipe.getFat());
        values.put(COLUMN_IMAGE, recipe.getImage());
        values.put(COLUMN_INGREDIENTS, recipe.getIngredients());

        long newRowId = db.insert(TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public List<Recipe> getAllRecipes() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_CATEGORY,
                COLUMN_NAME,
                COLUMN_RECIPE,
                COLUMN_KCAL,
                COLUMN_PROTEIN,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE,
                COLUMN_INGREDIENTS
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Recipe> recipes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            recipe.setRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE)));
            recipe.setKcal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KCAL)));
            recipe.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)));

            recipes.add(recipe);
        }

        cursor.close();
        db.close();

        return recipes;
    }

    public Recipe getRecipeByName(String name) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_CATEGORY,
                COLUMN_NAME,
                COLUMN_RECIPE,
                COLUMN_KCAL,
                COLUMN_PROTEIN,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE,
                COLUMN_INGREDIENTS
        };

        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Recipe recipe = null;

        if (cursor.moveToFirst()) {
            recipe = new Recipe();
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            recipe.setRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE)));
            recipe.setKcal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KCAL)));
            recipe.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)));
        }

        cursor.close();
        db.close();

        return recipe;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_CATEGORY,
                COLUMN_IMAGE
        };

        Cursor cursor = db.query(
                true,
                TABLE_NAME,
                projection,
                null,
                null,
                COLUMN_CATEGORY,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

            Category categoryItem = new Category(category, image);
            categories.add(categoryItem);
        }

        cursor.close();
        db.close();

        return categories;
    }

    public List<Recipe> getRecipesByCategory(String categoryName) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_CATEGORY,
                COLUMN_NAME,
                COLUMN_RECIPE,
                COLUMN_KCAL,
                COLUMN_PROTEIN,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE,
                COLUMN_INGREDIENTS
        };

        String selection = COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = { categoryName };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<Recipe> recipes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            recipe.setRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE)));
            recipe.setKcal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KCAL)));
            recipe.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)));

            recipes.add(recipe);
        }

        cursor.close();
        db.close();

        return recipes;
    }
    public Recipe getRecipeByCategoryAndName(String category, String name) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_CATEGORY,
                COLUMN_NAME,
                COLUMN_RECIPE,
                COLUMN_KCAL,
                COLUMN_PROTEIN,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE,
                COLUMN_INGREDIENTS
        };

        String selection = COLUMN_CATEGORY + " = ? AND " + COLUMN_NAME + " = ?";
        String[] selectionArgs = { category, name };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Recipe recipe = null;

        if (cursor.moveToFirst()) {
            recipe = new Recipe();
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            recipe.setRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE)));
            recipe.setKcal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KCAL)));
            recipe.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS)));
        }

        cursor.close();
        db.close();

        return recipe;
    }

    public void deleteRecipeByCategoryAndName(String category, String name) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = COLUMN_CATEGORY + " = ? AND " + COLUMN_NAME + " = ?";
        String[] selectionArgs = { category, name };

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void editRecipeByCategoryAndName(String category, String name, Recipe updatedRecipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, updatedRecipe.getCategory());
        values.put(COLUMN_NAME, updatedRecipe.getName());
        values.put(COLUMN_RECIPE, updatedRecipe.getRecipe());
        values.put(COLUMN_KCAL, updatedRecipe.getKcal());
        values.put(COLUMN_PROTEIN, updatedRecipe.getProtein());
        values.put(COLUMN_SUGAR, updatedRecipe.getSugar());
        values.put(COLUMN_FAT, updatedRecipe.getFat());
        values.put(COLUMN_IMAGE, updatedRecipe.getImage());
        values.put(COLUMN_INGREDIENTS, updatedRecipe.getIngredients());

        String selection = COLUMN_CATEGORY + " = ? AND " + COLUMN_NAME + " = ?";
        String[] selectionArgs = { category, name };

        db.update(TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }
}