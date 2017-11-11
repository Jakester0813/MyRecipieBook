package com.eleven.group.myrecipiebook.interfaces;

import com.eleven.group.myrecipiebook.model.RecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jake on 11/9/2017.
 */

public interface SearchRecipeInterface {
    @GET("/v1/api/recipe/{id}")
    Call<RecipeResponse> getRecipe(@Path("id") String id, @Query("_app_id") String appId, @Query("_app_key") String appKey);
}
