package com.kobaken0029.restsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.result)
    TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://weather.livedoor.com")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("===NetWork==="))
                .build();

        adapter.create(WeatherHacksApi.class).get("070030")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onCompleted() {
                        // 処理完了時コールバック
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 通信エラー時コールバック
                        Log.d(TAG, "onError() Error: " + e.toString());
                    }

                    @Override
                    public void onNext(ResponseObject response) {
                        // 処理結果が引き数に入ってくる
                        Log.d(TAG, "onNext()");
                        if (response != null) {
                            mResultTextView.setText(response.getForecasts().get(0).getTelop());
                        }
                    }
                });

    }
}
