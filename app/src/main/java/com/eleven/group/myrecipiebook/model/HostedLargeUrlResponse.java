package com.eleven.group.myrecipiebook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jake on 11/9/2017.
 */

public class HostedLargeUrlResponse {
    @SerializedName("hostedLargeUrl")
    String url;

    public String returnUrl(){
        return url;
    }
}
