package com.example.mobilecookbook;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FindRecipesTask extends AsyncTask<String, Void, List<DataFromApi>> {

    private static final String TAG = FindRecipesTask.class.getSimpleName();
    //%%%%%%%%%%%%%%%%
    public static List<DataFromApi> recipes = new ArrayList<>();

    public static String type = "Home";
//%%%%%%%%%%%%%%%%%%%%%%%%%%%
    private final String API_KEY = "c5b8c72d76msh3b793a701570d03p128808jsn60f7408665d7";
    private final String API_HOST = "tasty.p.rapidapi.com";
    private final String API_URL = "https://tasty.p.rapidapi.com/recipes/list";

    private Listener listener;

    public FindRecipesTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected List<DataFromApi> doInBackground(String... params) {
        String nazwaWyszukiwana = params[0];
        String size = params[1];


        recipes.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + "?from=0&size=" + size + "&q=" + nazwaWyszukiwana)
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();

        try {
            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyString = responseBody.string();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(responseBodyString);

                int count = responseJson.get("count").asInt();
                Log.d(TAG, "Count: " + count);

                JsonNode results = responseJson.get("results");
                for (JsonNode result : results) {
                    if (result.size() == 28) {
                        continue; // Pomiń obiekt result, jeśli nie ma dokładnie 28 kluczy
                    }

                    String name = result.get("name").asText();
                    String pictureLink = result.get("thumbnail_url").asText();

                    JsonNode instructions = result.get("instructions");
                    StringBuilder instruction = new StringBuilder();
                    for (JsonNode instructionNode : instructions) {
                        String displayText = instructionNode.get("display_text").asText();
                        instruction.append(displayText).append("\n\n");
                    }

                    JsonNode sections = result.get("sections");
                    StringBuilder ingredients = new StringBuilder();
                    for (JsonNode section : sections) {
                        JsonNode components = section.get("components");
                        for (JsonNode component : components) {
                            String rawText = component.get("raw_text").asText();
                            ingredients.append(" - ").append(rawText).append("\n\n");//
                        }
                    }

                    String fat = "not given", calories = "not given", sugar = "not given", carbohydrates = "not given",
                            protein = "not given";

                    JsonNode nutrition = result.get("nutrition");
                    if (nutrition != null && nutrition.has("fat")) {
                        fat = nutrition.get("fat").asText();
                    }
                    if (nutrition != null && nutrition.has("calories")) {
                        calories = nutrition.get("calories").asText();
                    }
                    if (nutrition != null && nutrition.has("sugar")) {
                        sugar = nutrition.get("sugar").asText();
                    }
                    if (nutrition != null && nutrition.has("carbohydrates")) {
                        carbohydrates = nutrition.get("carbohydrates").asText();
                    }
                    if (nutrition != null && nutrition.has("protein")) {
                        protein = nutrition.get("protein").asText();
                    }

                    DataFromApi tmp = new DataFromApi(name,pictureLink, instruction.toString(), ingredients.toString(),
                            protein, fat, sugar, calories, carbohydrates);
                    recipes.add(tmp);
                }

                Log.d(TAG, "Recipes count: " + recipes.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    @Override
    protected void onPostExecute(List<DataFromApi> recipes) {
        super.onPostExecute(recipes);
        if (listener != null) {
            listener.onRecipesFound(recipes);
        }
    }

    public interface Listener {
        void onRecipesFound(List<DataFromApi> recipes);
    }
}