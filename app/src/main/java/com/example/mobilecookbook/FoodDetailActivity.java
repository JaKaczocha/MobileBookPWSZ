package com.example.mobilecookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FoodDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOODNO = "foodNo";
    static int  foodNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodNo = getIntent().getIntExtra(EXTRA_FOODNO, 0);
        String foodName = FindRecipesTask.recipes.get(foodNo).nazwa;
        String foodImage = FindRecipesTask.recipes.get(foodNo).pictureLink;

        String text = getTextfromApi(foodNo);

        TextView textView = findViewById(R.id.food_text);
        textView.setText(text);

        ImageView imageView = findViewById(R.id.food_image);
        imageView.setContentDescription(foodName);

        Glide.with(this)
                .load(foodImage)
                .into(imageView);

        getSupportActionBar().setTitle(foodName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_my_book:
                Intent intent = new Intent(this, MyBookActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public static String getTextfromApi(int index) {

        String Text =
                "INGREDIENTS:\n";

        Text += FindRecipesTask.recipes.get(foodNo).ingredients;
        Text += "\n\nINSTRUCTION:\n";

        Text += FindRecipesTask.recipes.get(foodNo).instruction;
        Text += "\n\n";

        if(FindRecipesTask.recipes.get(foodNo).calories != "not given")
        {
            Text += "KCAL: " + FindRecipesTask.recipes.get(foodNo).calories + "\n";
            Text += "PROTEIN: " + FindRecipesTask.recipes.get(foodNo).protein + "\n";
            Text += "SUGAR: " + FindRecipesTask.recipes.get(foodNo).sugar + "\n";
            Text += "FAT: " + FindRecipesTask.recipes.get(foodNo).fat + "\n";
        }


        System.out.println(Text);
        return Text;
    }
}