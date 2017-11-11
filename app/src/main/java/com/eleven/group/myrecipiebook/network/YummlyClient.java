package com.eleven.group.myrecipiebook.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jake on 11/9/2017.
 */

public class YummlyClient {

    public static String baseUrl = "http://api.yummly.com";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }
}
