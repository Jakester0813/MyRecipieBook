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
    private String title;
    private String imageUrl;
    private String publisher;
    private int socialRank;
    private long recipeId;
    private ArrayList<String> ingredients;

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(int socialRank) {
        this.socialRank = socialRank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Recipe(){}

    public Recipe(JSONObject jsonObject){
        try {
            this.title = jsonObject.getString("title");
            this.recipeId = jsonObject.getLong("recipe_id");
            this.imageUrl = jsonObject.getString("image_url");
            this.publisher = jsonObject.getString("publisher");
            this.socialRank = jsonObject.getInt("social_rank");

            this.ingredients = new ArrayList<String>();
            try {
                JSONArray arr = jsonObject.getJSONArray("ingredients");
                Log.d("Recipe Ingredients: ", arr.toString());
                for (int i = 0; i < arr.length(); i++) {
                    this.ingredients.add(arr.getString(i));
                }
            } catch (JSONException e) {
                // Log.d("Exceptiop", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Recipe> fromJSONArray(JSONArray array){
        ArrayList<Recipe> results = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try{
                results.add(new Recipe(array.getJSONObject(i)));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

    public static Recipe fromJSONObject(JSONObject object){
        return new Recipe(object);
    }

}
