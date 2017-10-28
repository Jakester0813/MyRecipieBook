package com.eleven.group.myrecipiebook.cameraDB;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Siddhata on 10/27/17.
 */

public interface ApiService {

    @Multipart
    @POST("upload.php")
    Call<Result> uploadImage(@Part MultipartBody.Part file);

}
