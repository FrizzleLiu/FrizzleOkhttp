package com.frizzle.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.frizzle.okhttp.frizzleok.FrizzleCall;
import com.frizzle.okhttp.frizzleok.FrizzleCallback;
import com.frizzle.okhttp.frizzleok.FrizzleOkHttpClient;
import com.frizzle.okhttp.frizzleok.FrizzleRequest;
import com.frizzle.okhttp.frizzleok.FrizzleResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String PATH = "http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_original).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalRequest();
            }
        });
        findViewById(R.id.btn_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfRequest();
            }
        });
    }

    /**
     * Okhttp
     */
    private void originalRequest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(PATH).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OriginalRequest","请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("OriginalRequest",response.body().toString());
            }
        });
    }

    /**
     * 自己的
     */
    private void selfRequest() {
        FrizzleOkHttpClient frizzleOkHttpClient = new FrizzleOkHttpClient.Builder().build();
        FrizzleRequest frizzleRequest = new FrizzleRequest.Builder().url(PATH).build();
        FrizzleCall frizzleCall = frizzleOkHttpClient.newCall(frizzleRequest);
        frizzleCall.enqueue(new FrizzleCallback() {
            @Override
            public void onFailure(FrizzleCall call, IOException e) {
                Log.e("Frizzle","请求失败");
            }

            @Override
            public void onResponse(FrizzleCall call, FrizzleResponse response) throws IOException {
                Log.e("Frizzle",response.body());
            }
        });
    }
}