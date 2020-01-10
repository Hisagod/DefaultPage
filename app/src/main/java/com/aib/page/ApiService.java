package com.aib.page;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("wether")
    Observable<WeatherBean> getData(@Query("city") String city);
}
