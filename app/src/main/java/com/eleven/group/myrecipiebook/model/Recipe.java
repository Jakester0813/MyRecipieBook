package com.eleven.group.myrecipiebook.model;

import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Recipe {
    private String detailRecipeName;
    private String detailRecipeImage;
    private String totalTime;
    private int numberOfServings;
    private double rating;
    private ArrayList<String> ingredients;
    private ArrayList<Nutrition> nutritionEstimates;

    public ArrayList<Nutrition> getNutritionEstimates() {
        return nutritionEstimates;
    }

    public void setNutritionEstimates(ArrayList<Nutrition> nutritionEstimates) {
        this.nutritionEstimates = nutritionEstimates;
    }

    public String getDetailRecipeName() {
        return detailRecipeName;
    }

    public void setDetailRecipeName(String detailRecipeName) {
        this.detailRecipeName = detailRecipeName;
    }

    public String getDetailRecipeImage() {
        return detailRecipeImage;
    }

    public void setDetailRecipeImage(String detailRecipeImage) {
        this.detailRecipeImage = detailRecipeImage;
    }

    public int getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }


    public Recipe(){}

    public Recipe(JSONObject jsonObject){
        try {
            this.detailRecipeName = jsonObject.getString("name");
            this.detailRecipeImage = jsonObject.getJSONArray("images").getJSONObject(0).getString("hostedLargeUrl");
            this.numberOfServings = jsonObject.getInt("numberOfServings");
            this.totalTime = jsonObject.getString("totalTime");
            this.rating = jsonObject.getDouble("rating");

            this.ingredients = new ArrayList<String>();
            try {
                JSONArray arr = jsonObject.getJSONArray("ingredientLines");
                Log.d("Recipe Ingredients: ", arr.toString());
                for (int i = 0; i < arr.length(); i++) {
                    this.ingredients.add(arr.getString(i));
                }
            } catch (JSONException e) {
                // Log.d("Exceptiop", "");
            }

            this.nutritionEstimates = new ArrayList<Nutrition>();
            try {
                JSONArray arr = jsonObject.getJSONArray("nutritionEstimates");
                Log.d("Recipe nutritions: ", arr.toString());
                for (int i = 0; i < arr.length(); i++) {
                    this.nutritionEstimates.add(new Nutrition(arr.getJSONObject(i)));
                }
            } catch (JSONException e) {
                // Log.d("Exceptiop", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Recipe fromJSONObject(JSONObject object){
        return new Recipe(object);
    }

}
