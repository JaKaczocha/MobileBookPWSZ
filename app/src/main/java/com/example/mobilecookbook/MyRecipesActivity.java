package com.example.mobilecookbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;
    private DatabaseHelper databaseHelper;

    private String categoryName; // Dodaj pole do przechowywania nazwy kategorii

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        recyclerView = findViewById(R.id.recyclerView);

        recipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipes);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recipeAdapter);

        databaseHelper = new DatabaseHelper(this);

        // Odbierz przekazaną nazwę kategorii z Intent
        categoryName = getIntent().getStringExtra("categoryName");

        Log.d("MyRecipesActivity", "Received categoryName: " + categoryName); // Dodaj ten log

        // Pobierz przepisy z bazy danych dla danej kategorii
        if (categoryName != null && !categoryName.isEmpty()) {
            getSupportActionBar().setTitle(categoryName);
            loadRecipesFromDatabase();
        }

        // Ustaw listener onItemClickListener dla adaptera
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Recipe clickedRecipe = recipes.get(position);
                // Przejdź do MyRecipesDetailsActivity i przekaż informacje o kategorii i nazwie przepisu
                Intent intent = new Intent(MyRecipesActivity.this, MyRecipesDetails.class);
                intent.putExtra("categoryName", categoryName);
                intent.putExtra("recipeName", clickedRecipe.getName());
                startActivity(intent);
            }
        });
    }


    private void loadRecipesFromDatabase() {
        recipes.clear();
        recipes.addAll(databaseHelper.getRecipesByCategory(categoryName)); // Pobierz przepisy dla danej kategorii
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipesFromDatabase();
    }

}