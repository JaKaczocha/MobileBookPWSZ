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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodMaterialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodMaterialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodMaterialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodMaterialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodMaterialFragment newInstance(String param1, String param2) {
        FoodMaterialFragment fragment = new FoodMaterialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView foodRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_food_material, container, false);
        Food.food.clear();
        if(Food.getType() == "Pizza" ) {
            Food.food = new ArrayList<Food>(Arrays.asList(Pizza.pizzas));
        }
        else if( Food.getType() == "Pasta") {
            Food.food = new ArrayList<Food>(Arrays.asList(Pasta.pasta));
        }
        else if( Food.getType() == "Soup") {
            Food.food = new ArrayList<Food>(Arrays.asList(Soup.soup));
        }
        else if( Food.getType() == "Home") {
            Food.food = new ArrayList<Food>(Arrays.asList(Home.home));
        }
        else {
            //
        }


        ArrayList<String> foodNames = new ArrayList<String>();

        for(int i = 0; i < Food.food.size();i++ ) {
            foodNames.add(Food.food.get(i).getName());
        }
        ArrayList<Integer> foodImages = new ArrayList<Integer>(0);
        for(int i = 0; i < Food.food.size();i++ ) {
            foodImages.add(Food.food.get(i).getImageResourceId());
        }

        String[] myStringArray = new String[foodNames.size()];

        int[] myIntArray = new int[foodImages.size()];
        for (int i = 0; i < foodImages.size(); i++) {
            myIntArray[i] = foodImages.get(i);
        }


        CaptionedImagesAdapter adapter =
                new CaptionedImagesAdapter(foodNames.toArray(myStringArray),myIntArray);
        foodRecycler.setAdapter(adapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); // display one column at a time
        foodRecycler.setLayoutManager(layoutManager);

        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2); // display items in a grid of 2 columns
        //foodRecycler.setLayoutManager(layoutManager);


        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOODNO, position);
                getActivity().startActivity(intent);
            }
        });
        return foodRecycler;



    }

}