package com.example.mobilecookbook;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        

        //display the up button on the activity bar
        // deleted because the application was crashing but
        // WORKS WITHOUT IT
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

    }


}