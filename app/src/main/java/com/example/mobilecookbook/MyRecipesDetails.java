package com.example.mobilecookbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyRecipesDetails extends AppCompatActivity {
    private String categoryName;
    private String recipeName;
    private DatabaseHelper dbHelper;

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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //prepare the menu, if the action bar exists, we add items to it
        getMenuInflater().inflate(R.menu.menu_my_recipes_details,menu); // arg1 - resource file, arg2 - object representing the action bar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            // Przechodź do MyRecipesActivity po kliknięciu przycisku wstecz
            Intent intent = new Intent(this, MyRecipesActivity.class);
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