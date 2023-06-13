package com.example.mobilecookbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyRecipesDetails extends AppCompatActivity {
    private String categoryName;
    private String recipeName;
    private DatabaseHelper dbHelper;
    private TextView edtvMYNAME;
    private ImageView imageMyRecipe;
    private boolean isFavorite = false;
    private MenuItem favoriteMenuItem;
    private static final int EDIT_RECIPE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes_details);

        categoryName = getIntent().getStringExtra("categoryName");
        recipeName = getIntent().getStringExtra("recipeName");

        getSupportActionBar().setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        // Wywołaj metodę getRecipeByCategoryAndName
        Recipe recipe = dbHelper.getRecipeByCategoryAndName(categoryName, recipeName);
        if (recipe != null) {
            // Znaleziono przepis
            // Wykonaj odpowiednie działania
            edtvMYNAME = findViewById(R.id.edtvMYNAME);
            if (recipe.getFat() != 0) {
                edtvMYNAME.setText(recipe.getIngredients() + "\n\n" + recipe.getRecipe() + "\n\nKCAL: " +
                        recipe.getKcal() + " \nPROTEIN: " + recipe.getProtein() + "\nSUGAR: " +
                        recipe.getSugar() + "\nFAT: " + recipe.getFat() + '\n');
            } else {
                edtvMYNAME.setText(recipe.getRecipe());
            }

            imageMyRecipe = findViewById(R.id.imageMyRecipe);

            Glide.with(this)
                    .load(recipe.getImage())
                    .into(imageMyRecipe);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_recipes_details, menu);
        isFavorite = dbHelper.getRecipeByCategoryAndNameBoolean("favorite", recipeName);
        favoriteMenuItem = menu.findItem(R.id.action_favorite_recipe);
        setFavoriteIcon();
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavoriteIcon() {
        if (isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.full_heart);
        } else {
            favoriteMenuItem.setIcon(R.drawable.empty_heart);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            Intent intent = new Intent(this, MyRecipesActivity.class);
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_delete_recipe) {
            dbHelper.deleteRecipeByCategoryAndName(categoryName, recipeName);
            Toast.makeText(this, "Przepis został usunięty", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else if (itemId == R.id.action_edit_recipe) {
            Intent intent = new Intent(this, EditMyRecipeDetailsActivity.class);
            intent.putExtra("categoryName", categoryName);
            intent.putExtra("recipeName", recipeName);
            startActivityForResult(intent, EDIT_RECIPE_REQUEST_CODE);
            return true;
        } else if (itemId == R.id.action_favorite_recipe) {
            isFavorite = !isFavorite;
            setFavoriteIcon();

            if (isFavorite) {
                Toast.makeText(this, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                isFavorite = true;
                Recipe recipeTrue = dbHelper.getRecipeByCategoryAndName(categoryName, recipeName);
                recipeTrue.setCategory("favorite");
                dbHelper.addRecipe(recipeTrue);
            } else {
                Toast.makeText(this, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                isFavorite = false;
                dbHelper.deleteRecipeByCategoryAndName("favorite", recipeName);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_RECIPE_REQUEST_CODE && resultCode == RESULT_OK) {
            updateDisplayedRecipe();
        }
    }

    private void updateDisplayedRecipe() {
        Recipe recipe = dbHelper.getRecipeByCategoryAndName(categoryName, recipeName);
        if (recipe != null) {
            edtvMYNAME.setText(recipe.getIngredients() + "\n\n" + recipe.getRecipe() + "\n\nKCAL: " +
                    recipe.getKcal() + " \nPROTEIN: " + recipe.getProtein() + "\nSUGAR: " +
                    recipe.getSugar() + "\nFAT: " + recipe.getFat() + '\n');
            // Update other UI elements as needed
        }
    }
}