package com.eleven.group.myrecipiebook.cameraDB;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Siddhata on 10/27/17.
 */

public class RetroClient {

    private static final String ROOT_URL = "http://192.168.0.19/Retrofit/";


    public RetroClient() {
    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetroClient().create(ApiService.class);
    }

}
