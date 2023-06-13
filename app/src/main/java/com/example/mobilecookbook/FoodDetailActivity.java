package com.example.mobilecookbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

public class FoodDetailActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private ImageView imageView;
    private MenuItem favoriteMenuItem;
    private String foodName;
    private String foodImage;
    private String text;
    public static final String EXTRA_FOODNO = "foodNo";
    private static int foodNo;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodNo = getIntent().getIntExtra(EXTRA_FOODNO, 0);
        foodName = FindRecipesTask.recipes.get(foodNo).nazwa;
        foodImage = FindRecipesTask.recipes.get(foodNo).pictureLink;

        text = getTextfromApi(foodNo);

        TextView textView = findViewById(R.id.food_text);
        textView.setText(text);

        imageView = findViewById(R.id.food_image);
        imageView.setContentDescription(foodName);

        Glide.with(this)
                .asBitmap()
                .load(foodImage)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        imageBytes = bitmapToByteArray(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });

        getSupportActionBar().setTitle(foodName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        DatabaseHelper db = new DatabaseHelper(this);
        isFavorite = db.getRecipeByCategoryAndNameBoolean("favorite", foodName);

        favoriteMenuItem = menu.findItem(R.id.action_favorite_recipe);

        setFavoriteIcon();

        return true;
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
        switch (item.getItemId()) {
            case R.id.action_open_my_book:
                Intent intent = new Intent(this, MyBookActivity.class);
                startActivity(intent);
                break;

            case R.id.action_favorite_recipe:
                // Obsługa kliknięcia przycisku Ulubione
                isFavorite = !isFavorite;
                setFavoriteIcon();

                if (isFavorite) {
                    Toast.makeText(this, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                    DatabaseHelper db = new DatabaseHelper(this);
                    Recipe recipeTrue = new Recipe("favorite", foodName, text, 0, 0, 0, 0, imageBytes, "");
                    db.addRecipe(recipeTrue);
                } else {
                    Toast.makeText(this, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                    DatabaseHelper db = new DatabaseHelper(this);
                    db.deleteRecipeByCategoryAndName("favorite", foodName);
                }
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public static String getTextfromApi(int index) {
        String Text = "INGREDIENTS:\n";
        Text += FindRecipesTask.recipes.get(foodNo).ingredients;
        Text += "\n\nINSTRUCTION:\n";
        Text += FindRecipesTask.recipes.get(foodNo).instruction;
        Text += "\n\n";

        if (!FindRecipesTask.recipes.get(foodNo).calories.equals("not given")) {
            Text += "KCAL: " + FindRecipesTask.recipes.get(foodNo).calories + "\n";
            Text += "PROTEIN: " + FindRecipesTask.recipes.get(foodNo).protein + "\n";
            Text += "SUGAR: " + FindRecipesTask.recipes.get(foodNo).sugar + "\n";
            Text += "FAT: " + FindRecipesTask.recipes.get(foodNo).fat + "\n";
        }

        System.out.println(Text);
        return Text;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}