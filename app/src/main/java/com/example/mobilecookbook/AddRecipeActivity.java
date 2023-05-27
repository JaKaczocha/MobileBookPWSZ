package com.example.mobilecookbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddRecipeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageRecipe;
    private EditText edtvNAME,edtvRECIPE,edtvKCAL,edtvSALT,edtvSUGAR,edtvFAT,edtvCATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getSupportActionBar().setTitle("Add recipe");

        mImageRecipe = findViewById(R.id.imageRecipe);
        edtvNAME = findViewById(R.id.edtvNAME);
        edtvRECIPE = findViewById(R.id.edtvRECIPE);
        edtvKCAL = findViewById(R.id.edtvKCAL);
        edtvSALT = findViewById(R.id.edtvSALT);
        edtvSUGAR = findViewById(R.id.edtvSUGAR);
        edtvFAT = findViewById(R.id.edtvFAT);
        edtvCATEGORY = findViewById(R.id.edtvCATEGORY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //prepare the menu, if the action bar exists, we add items to it
        getMenuInflater().inflate(R.menu.menu_add_recipe,menu); // arg1 - resource file, arg2 - object representing the action bar
        return super.onCreateOptionsMenu(menu);
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mImageRecipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveRecipe(View view) {
        String category = edtvCATEGORY.getText().toString();
        String name = edtvNAME.getText().toString();
        String recipe = edtvRECIPE.getText().toString();
        int kcal = Integer.parseInt(edtvKCAL.getText().toString());
        int salt = Integer.parseInt(edtvSALT.getText().toString());
        int sugar = Integer.parseInt(edtvSUGAR.getText().toString());
        int fat = Integer.parseInt(edtvFAT.getText().toString());

        BitmapDrawable drawable = (BitmapDrawable) mImageRecipe.getDrawable();
        Bitmap image = drawable.getBitmap();
        byte[] imageBytes = getBytesFromBitmap(image);

        // Tworzenie obiektu przepisu
        Recipe newRecipe = new Recipe(category, name, recipe, kcal, salt, sugar, fat, imageBytes);

        // Dodawanie przepisu do bazy danych
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long newRowId = dbHelper.addRecipe(newRecipe);

        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Recipe successfully added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to add recipe!", Toast.LENGTH_SHORT).show();
        }

        // Czyszczenie pól i obrazka
        edtvCATEGORY.setText("");
        edtvSALT.setText("");
        edtvSUGAR.setText("");
        edtvNAME.setText("");
        edtvKCAL.setText("");
        edtvRECIPE.setText("");
        edtvFAT.setText("");
        mImageRecipe.setImageBitmap(null);
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


}