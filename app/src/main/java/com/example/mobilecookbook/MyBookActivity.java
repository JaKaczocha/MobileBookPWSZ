package com.example.mobilecookbook;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MyBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        getSupportActionBar().setTitle("MyBook");

        recyclerView = findViewById(R.id.recyclerView);

        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(categoryAdapter);

        // Pobierz kategorie z bazy danych
        getCategoriesFromDatabaseWithoutFavorite();

        // Obsługa zdarzenia kliknięcia w CardView
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Category clickedCategory = categories.get(position);

                Log.d("MyBookActivity", "Clicked category: " + clickedCategory.getName()); // Dodaj ten log

                // Przekierowanie do MyRecipesActivity z przekazaną nazwą kategorii
                Intent intent = new Intent(MyBookActivity.this, MyRecipesActivity.class);
                System.out.println(clickedCategory.getName() + ")))))))))))))))))))))))))))))");
                intent.putExtra("categoryName", clickedCategory.getName()); // Przekazanie nazwy kategorii

                Log.d("MyBookActivity", "Starting MyRecipesActivity with category: " + clickedCategory.getName()); // Dodaj ten log

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //prepare the menu, if the action bar exists, we add items to it
        getMenuInflater().inflate(R.menu.menu_my_book,menu); // arg1 - resource file, arg2 - object representing the action bar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch( item.getItemId()) {
            case R.id.action_add_recipe:
                // code after clicking the place order button (we start the OrderActivity)
                Intent intent = new Intent(this, AddRecipeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //code after executing setting item
                return true; // true tells the system that clicking on an action bar item has been handled
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getCategoriesFromDatabase() {
        // Otwórz bazę danych (DatabaseHelper to klasa pomocnicza do obsługi bazy danych)
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        categories = dbHelper.getAllCategories(); // Przypisz pobrane kategorie do pola 'categories' w klasie

        // Przygotuj RecyclerView i adapter
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Ustawienie siatki 2-kolumnowej
        categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setAdapter(categoryAdapter);
    }

    private void getCategoriesFromDatabaseWithoutFavorite() {
        // Otwórz bazę danych (DatabaseHelper to klasa pomocnicza do obsługi bazy danych)
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        categories = dbHelper.getCategoriesFromDatabaseWithoutFavorite(); // Przypisz pobrane kategorie do pola 'categories' w klasie

        // Przygotuj RecyclerView i adapter
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Ustawienie siatki 2-kolumnowej
        categoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setAdapter(categoryAdapter);
    }


}