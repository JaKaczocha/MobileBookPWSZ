package com.example.mobilecookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodMaterialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodMaterialFragment extends Fragment implements CaptionedImagesAdapter.ItemClickListener, FindRecipesTask.Listener {

    private RecyclerView foodRecycler;
    private String mParam1;
    private String mParam2;
    private TextView tv;

    public FoodMaterialFragment() {
        // Required empty public constructor
    }

    public static FoodMaterialFragment newInstance(String param1, String param2) {
        FoodMaterialFragment fragment = new FoodMaterialFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        foodRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_food_material, container, false);

        String size = "30";
        if (FindRecipesTask.type.equals("Pizza")) {
            searchRecipes(FindRecipesTask.type, size);

        } else if (FindRecipesTask.type.equals("Pasta")) {
            searchRecipes(FindRecipesTask.type, size);
        } else if (FindRecipesTask.type.equals("Soup")) {
            searchRecipes(FindRecipesTask.type, size);
        } else if (FindRecipesTask.type.equals("Home")) {
            searchRecipes(FindRecipesTask.type, size);
        } else {
            searchRecipes(FindRecipesTask.type, size);
            System.out.println("65 fragment works: " + FindRecipesTask.type + " " + size);
        }

        return foodRecycler;
    }

    private void searchRecipes(String query, String size) {
        FindRecipesTask findRecipesTask = new FindRecipesTask(this);
        findRecipesTask.execute(query, size);
    }

    @Override
    public void onRecipesFound(List<DataFromApi> recipes) {
        ArrayList<String> foodNames = new ArrayList<String>();
        ArrayList<String> foodImages = new ArrayList<String>();

        for (DataFromApi recipe : recipes) {
            foodNames.add(recipe.nazwa);
            foodImages.add(recipe.pictureLink);
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(foodImages, foodNames);
        adapter.setItemClickListener(this);

        foodRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        foodRecycler.setLayoutManager(layoutManager);

        showItemCountToast(recipes.size()); // Wywołanie metody showItemCountToast z przekazaną liczbą elementów
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOODNO, position);
        startActivity(intent);
    }

    private void showItemCountToast(int count) {

        if(count == 0) {
            String message = "NO RECIPES!";
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }

    }
}