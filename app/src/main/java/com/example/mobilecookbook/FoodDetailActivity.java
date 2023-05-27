package com.example.mobilecookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class FoodDetailActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_FOODNO = "foodNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //getActionBar().setDisplayHomeAsUpEnabled(true);//throws a nullPointerException enable the up button

        //we display detailed information
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
        String foodName = Food.food.get(foodNo).getName();
        TextView textView = (TextView) findViewById(R.id.food_text);
        textView.setText("");
        int foodImage = Food.food.get(foodNo).getImageResourceId();
        ImageView imageView = (ImageView) findViewById(R.id.food_image);
        imageView.setImageDrawable(getResources().getDrawable(foodImage));
        imageView.setContentDescription(foodName);

        getSupportActionBar().setTitle(Food.food.get(foodNo).getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu); // dodajemy elementu z tego pliku do paska aktywności
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_my_book:
                    Intent intent = new Intent(this, MyBookActivity.class); // uruchamiamy aktywność pod przyciskiem na pasku aktywności
                    startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}