package com.kobaken0029.restsample;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherHacksApi {
    @GET("/forecast/webservice/json/v1")
    Observable<ResponseObject> get(@Query("city") String city);
}
