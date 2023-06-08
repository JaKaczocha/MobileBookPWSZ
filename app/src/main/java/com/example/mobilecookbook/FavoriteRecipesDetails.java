package com.example.mobilecookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipesDetails extends AppCompatActivity {
    private String categoryName;
    private String recipeName;
    private DatabaseHelper dbHelper;
    private TextView edtvMYNAME;
    private ImageView imageMyRecipe;
    private boolean isFavorite = false;
    private MenuItem favoriteMenuItem;
    private MenuItem bucket;
    private MenuItem edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes_details);

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
            if(recipe.getFat() != 0)
            {
                edtvMYNAME.setText(recipe.getIngredients() + "\n\n" + recipe.getRecipe() + "\n\nKCAL: " +
                        recipe.getKcal() + " \nPROTEIN: " + recipe.getProtein() + "\nSUGAR: " +
                        recipe.getSugar() + "\nFAT: " + recipe.getFat() + '\n');
            }
            else {
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
        //prepare the menu, if the action bar exists, we add items to it
        getMenuInflater().inflate(R.menu.menu_my_recipes_details,menu); // arg1 - resource file, arg2 - object representing the action bar
        DatabaseHelper db = new DatabaseHelper(this);
        isFavorite = db.getRecipeByCategoryAndNameBoolean("favorite",recipeName);

        favoriteMenuItem = menu.findItem(R.id.action_favorite_recipe);

        bucket = menu.findItem(R.id.action_delete_recipe);
        bucket.setVisible(false);
        edit = menu.findItem(R.id.action_edit_recipe);
        edit.setVisible(false);
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
            // Przechodź do MyRecipesActivity po kliknięciu przycisku wstecz
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("categoryName", categoryName);

            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_delete_recipe) {
            // Wywołaj funkcję usuwania przepisu po kategorii i nazwie
            dbHelper.deleteRecipeByCategoryAndName(categoryName, recipeName);
            Toast.makeText(this, "Przepis został usunięty", Toast.LENGTH_SHORT).show();
            finish(); // Zakończ aktywność po usunięciu przepisu
            return true;
        } else if (itemId == R.id.action_edit_recipe) {
            // Przechodź do EditMyRecipeDetailsActivity po kliknięciu przycisku edycji
            Intent intent = new Intent(this, EditMyRecipeDetailsActivity.class);
            intent.putExtra("categoryName", categoryName);
            intent.putExtra("recipeName", recipeName);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_favorite_recipe) {
            // Obsługa kliknięcia przycisku Ulubione
            isFavorite = !isFavorite;
            setFavoriteIcon();

            if (isFavorite) {
                Toast.makeText(this, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                isFavorite = true;
                DatabaseHelper db = new DatabaseHelper(this);
                Recipe recipeTrue = db.getRecipeByCategoryAndName(categoryName,recipeName);
                recipeTrue.setCategory("favorite");
                db.addRecipe(recipeTrue);

            } else {
                Toast.makeText(this, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                isFavorite = false;
                DatabaseHelper db = new DatabaseHelper(this);
                db.deleteRecipeByCategoryAndName("favorite",recipeName);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Zamknij połączenie z bazą danych w onDestroy
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    // Obsługa wyniku z EditMyRecipeDetailsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Sprawdź, czy żądanie i wynik mają odpowiednie kody
            if (data != null) {
                categoryName = data.getStringExtra("categoryName");
                recipeName = data.getStringExtra("recipeName");
                // Wykonaj odpowiednie działania na podstawie przekazanych danych
            }
        }
    }
}