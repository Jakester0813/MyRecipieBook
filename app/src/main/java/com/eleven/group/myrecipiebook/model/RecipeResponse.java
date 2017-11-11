package com.eleven.group.myrecipiebook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jake on 11/9/2017.
 */

public class RecipeResponse {
    @SerializedName("name")
    String name;

    @SerializedName("images")
    ArrayList<HostedLargeUrlResponse> images;

    @SerializedName("numberOfServings")
    int servings;

    @SerializedName("totalTime")
    String totalTime;

    @SerializedName("rating")
    double rating;

    @SerializedName("ingredientLines")
    ArrayList<String> ingredients;

    @SerializedName("nutritionEstimates")
    ArrayList<Nutrition> nutrition;

    public String getName(){
        return name;
    }

    public String getImage(){
        return images.get(0).returnUrl();
    }

    public int getServings(){
        return servings;
    }

    public String getTotalTime(){
        return totalTime;
    }

    public double getRating(){
        return rating;
    }

    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    public ArrayList<Nutrition> getNutrition(){
        return nutrition;
    }
}
