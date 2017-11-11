package com.eleven.group.myrecipiebook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jake on 11/10/2017.
 */

public class Recipes {

    @SerializedName("id")
    String id;

    @SerializedName("recipeName")
    String name;

    @SerializedName("smallImageUrls")
    ArrayList<String> response;

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImageUrl(){
        return response.get(0);
    }
}
