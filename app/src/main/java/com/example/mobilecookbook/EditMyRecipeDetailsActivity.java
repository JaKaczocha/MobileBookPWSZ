package com.example.mobilecookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMyRecipeDetailsActivity extends AppCompatActivity {

    String categoryName, recipeName;
    EditText editRecipe, editIngredients, editKCLA, editSUGAR, editFAT, editPROTEIN;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryName = getIntent().getStringExtra("categoryName");
        recipeName = getIntent().getStringExtra("recipeName");
        editFAT = findViewById(R.id.editFAT);
        editKCLA = findViewById(R.id.editKCAL);
        editPROTEIN = findViewById(R.id.editPROTEIN);
        editSUGAR = findViewById(R.id.editSUGAR);
        editRecipe = findViewById(R.id.editRECIPE);
        editIngredients = findViewById(R.id.editINGREDIENTS);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        recipe = dbHelper.getRecipeByCategoryAndName(categoryName, recipeName);

        editIngredients.setText(recipe.getIngredients().toString());
        editRecipe.setText(recipe.getRecipe().toString());
        editKCLA.setText(String.valueOf(recipe.getKcal()));
        editFAT.setText(String.valueOf(recipe.getFat()));
        editSUGAR.setText(String.valueOf(recipe.getSugar()));
        editPROTEIN.setText(String.valueOf(recipe.getProtein()));
    }

    public void editRecipe(View view ) {
        // Utwórz obiekt Recipe z zaktualizowanymi danymi
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setCategory(categoryName);
        updatedRecipe.setName(recipeName);
        updatedRecipe.setIngredients(editIngredients.getText().toString());
        updatedRecipe.setRecipe(editRecipe.getText().toString());
        updatedRecipe.setKcal(Integer.parseInt(editKCLA.getText().toString()));
        updatedRecipe.setFat(Integer.parseInt(editFAT.getText().toString()));
        updatedRecipe.setSugar(Integer.parseInt(editSUGAR.getText().toString()));
        updatedRecipe.setProtein(Integer.parseInt(editPROTEIN.getText().toString()));
        updatedRecipe.setImage(recipe.getImage());
        // Aktualizuj przepis w bazie danych
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.editRecipeByCategoryAndName(categoryName, recipeName, updatedRecipe);

        // Powiadomienie o sukcesie
        Toast.makeText(this, "Recipe updated successfully.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Przekazanie nazwy i kategorii przepisu do poprzedniej aktywności
            Intent intent = new Intent();
            intent.putExtra("categoryName", categoryName);
            intent.putExtra("recipeName", recipeName);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}