package com.eleven.group.myrecipiebook.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class SearchRecipe {
    private String recipeName;
    private String recipeImage;
    private String recipeId;


    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public SearchRecipe(){}

    public SearchRecipe(JSONObject jsonObject){
        try {
            this.recipeName = jsonObject.getString("recipeName");
            this.recipeImage = jsonObject.getJSONArray("smallImageUrls").getString(0);
            this.recipeId = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<SearchRecipe> fromJSONArray(JSONArray array){
        ArrayList<SearchRecipe> results = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try{
                results.add(new SearchRecipe(array.getJSONObject(i)));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

}
