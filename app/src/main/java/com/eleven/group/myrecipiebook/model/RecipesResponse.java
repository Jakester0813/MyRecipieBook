package com.eleven.group.myrecipiebook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jake on 11/9/2017.
 */

public class RecipesResponse {
    @SerializedName("matches")
    ArrayList<Recipes> recipes;

    public ArrayList<Recipes> getRecipes(){
        return recipes;
    }
}
