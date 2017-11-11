package com.eleven.group.myrecipiebook.util;

import com.eleven.group.myrecipiebook.interfaces.SearchRecipeInterface;
import com.eleven.group.myrecipiebook.interfaces.SearchRecipesInterface;
import com.eleven.group.myrecipiebook.network.YummlyClient;

/**
 * Created by Jake on 11/10/2017.
 */

public class RecipeUtility {

    public static SearchRecipeInterface getRecipeService() {
        return YummlyClient.getClient().create(SearchRecipeInterface.class);
    }

    public static SearchRecipesInterface getRecipesService() {
        return YummlyClient.getClient().create(SearchRecipesInterface.class);
    }
}
