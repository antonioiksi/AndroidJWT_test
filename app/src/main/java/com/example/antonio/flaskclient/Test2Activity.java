package com.example.antonio.flaskclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test2Activity extends AppCompatActivity {
    private Activity activity;

    private TextView textView2;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        textView2 = (TextView)findViewById(R.id.textView2);

        //sPref = getPreferences(MODE_PRIVATE);
        //String access_token = // sPref.getString("access_token", "");
        textView2.setText( MyApplication.getJwtToken() );
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(MyApplication.getBaseUrl() + "protected")
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "JWT " + MyApplication.getJwtToken())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Toast.makeText(MainActivity.this,response.body().string(), Toast.LENGTH_LONG);
                final String response_body = response.body().string();
                //Log.d("login",response.body().string());
                //Toast.makeText(MainActivity.this, "Ok!",Toast.LENGTH_LONG).show();
                // Display the requested data on UI in main thread

                textView2.setText( response_body);

            }
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(activity, e.getMessage(),Toast.LENGTH_LONG).show();
                MyApplication.backgroundThreadShortToast(e.getMessage());
                Log.d( "login", e.getMessage());
            }

        });
    }

}
