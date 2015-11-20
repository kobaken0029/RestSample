package com.kobaken0029.restsample;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherHacksApi {
    @GET("/forecast/webservice/json/v1")
    Observable<ResponseObject> get(@Query("city") String city);
}
