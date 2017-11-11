package com.eleven.group.myrecipiebook.interfaces;

import com.eleven.group.myrecipiebook.model.RecipeResponse;
import com.eleven.group.myrecipiebook.model.RecipesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jake on 11/9/2017.
 */

public interface SearchRecipesInterface {
    @GET("/v1/api/recipes")
    Call<RecipesResponse> getRecipes(@Query("_app_id") String appId, @Query("_app_key") String appKey, @Query("q") String q);
}
