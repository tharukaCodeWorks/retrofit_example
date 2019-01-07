package com.edusoft.retrofitsample.util;

import com.edusoft.retrofitsample.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("posts")
    Call<List<PostModel>> getPost();
}
