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
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_RECIPE = "recipe";
    private static final String COLUMN_KCAL = "kcal";
    private static final String COLUMN_SALT = "salt";
    private static final String COLUMN_SUGAR = "sugar";
    private static final String COLUMN_FAT = "fat";
    private static final String COLUMN_IMAGE = "image";

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
                COLUMN_SALT + " INTEGER," +
                COLUMN_SUGAR + " INTEGER," +
                COLUMN_FAT + " INTEGER," +
                COLUMN_IMAGE + " BLOB" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Jeśli potrzebujesz zaktualizować strukturę bazy danych, wykonaj odpowiednie operacje tutaj.
        // Ta metoda zostanie wywołana, gdy zmienisz wartość DATABASE_VERSION.
        // Na potrzeby tego przykładu, można po prostu usunąć starą tabelę i utworzyć nową.
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }


    public long addRecipe(Recipe recipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, recipe.getCategory());
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_RECIPE, recipe.getRecipe());
        values.put(COLUMN_KCAL, recipe.getKcal());
        values.put(COLUMN_SALT, recipe.getSalt());
        values.put(COLUMN_SUGAR, recipe.getSugar());
        values.put(COLUMN_FAT, recipe.getFat());
        values.put(COLUMN_IMAGE, recipe.getImage());

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
                COLUMN_SALT,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE
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

        while (((Cursor) cursor).moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            recipe.setRecipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE)));
            recipe.setKcal(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KCAL)));
            recipe.setSalt(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SALT)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));

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
                COLUMN_SALT,
                COLUMN_SUGAR,
                COLUMN_FAT,
                COLUMN_IMAGE
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
            recipe.setSalt(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SALT)));
            recipe.setSugar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUGAR)));
            recipe.setFat(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT)));
            recipe.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
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
}