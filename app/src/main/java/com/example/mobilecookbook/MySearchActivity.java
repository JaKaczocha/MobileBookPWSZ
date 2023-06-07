package com.example.mobilecookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MySearchActivity extends AppCompatActivity {

    private EditText editTextSEARCH;
    private Button buttonSEARCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_search);

        editTextSEARCH = findViewById(R.id.editTextSEARCH);
        buttonSEARCH = findViewById(R.id.SEARCH);

        buttonSEARCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSEARCH.getText().toString();
                // Tutaj możesz wykonać odpowiednie operacje na wprowadzonym tekście
                performSearch(searchText);
            }
        });
    }

    private void performSearch(String searchText) {
        // Ta metoda zostanie wywołana po kliknięciu przycisku "SEARCH"
        // Tutaj możesz wykonać logikę związana z wyszukiwaniem na podstawie wprowadzonego tekstu
    }
}