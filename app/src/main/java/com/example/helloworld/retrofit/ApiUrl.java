package com.example.helloworld.retrofit;

import com.example.helloworld.constant.Constans;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUrl {

    @GET(Constans.retrofit)
     Observable<BaseResponse<String>> getData(@Body String name);

    @POST(Constans.retrofitList)
    Observable<BaseResponse<List<String>>> getList(@Query("city") String city, @Query("key") String key);
}
