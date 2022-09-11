package com.upperhand.findthelink.interfaces;

import com.upperhand.findthelink.objects.Score;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostData {

    @POST("score")
    Call<Score> postScore(@Body Score score);

}