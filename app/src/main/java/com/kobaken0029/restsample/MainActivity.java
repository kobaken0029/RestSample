package com.kobaken0029.restsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.kobaken0029.restsample.databinding.ActivityMainBinding;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://weather.livedoor.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        retrofit.create(WeatherHacksApi.class)
                .get("070030")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            // 処理結果が引き数に入ってくる
                            Log.d(TAG, "onNext()");
                            if (response != null) {
                                binding.result.setText(response.getForecasts().get(0).getTelop());
                            }
                        },
                        throwable -> Log.d(TAG, "onError() Error: " + throwable.toString()),
                        () -> Log.d(TAG, "onCompleted()")
                );
    }
}
