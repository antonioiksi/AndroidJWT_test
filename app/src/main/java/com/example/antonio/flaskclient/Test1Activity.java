package com.example.antonio.flaskclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test1Activity extends AppCompatActivity {

    SharedPreferences sPref;

    private Activity activity;
    private EditText username;
    private EditText password;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        activity = Test1Activity.this;
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        textView = (TextView)findViewById(R.id.textView);

    }

    public void firstApi(View view) {
        Log.d("firstApi", "Hello");

        Toast.makeText(this, "Ok!",Toast.LENGTH_LONG).show();
        OkHttpClient okHttpClient = new OkHttpClient();
        //Request request = new Request.Builder()
        //        .url("https://api.hh.ru/areas") // The Http2Server should be running here.
        //        .build();

        Request request = new Request.Builder()
                .url(MyApplication.getBaseUrl() + "free")
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjQ0NzQ0NTgsImlhdCI6MTUyNDQ3NDE1OCwibmJmIjoxNTI0NDc0MTU4LCJpZGVudGl0eSI6MX0.jt_E3bw30eix6LbnGGxdRpCKUG72R-27xzVcWQTbJ7A")
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Toast.makeText(MainActivity.this,response.body().string(), Toast.LENGTH_LONG);
                Log.d("firstApi",response.body().string());
                //Toast.makeText(MainActivity.this, "Ok!",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(MainActivity.this, "777",Toast.LENGTH_LONG).show();
                Log.d( "firstApi", e.getMessage());
            }
        });

    }


    public void login(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username.getText());
            jsonObject.put("password", password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final String url_auth = MyApplication.getBaseUrl() + "auth";
        Request request = new Request.Builder()
                .url(url_auth)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Toast.makeText(MainActivity.this,response.body().string(), Toast.LENGTH_LONG);
                final String response_body = response.body().string();
                //Log.d("login",response.body().string());
                //Toast.makeText(MainActivity.this, "Ok!",Toast.LENGTH_LONG).show();
                // Display the requested data on UI in main thread

                try {
                    JSONObject response_json = new JSONObject(response_body);

                    if (response_json.has("access_token")) {
                        String access_token = response_json.getString("access_token");
                        MyApplication.setJwtToken(access_token);
                        sPref = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("access_token", access_token);
                        ed.commit();

                        Log.d("login", sPref.getString("access_token",""));

                        Intent myIntent = new Intent(activity, Test2Activity.class);
                        startActivity(myIntent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /*
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Display requested url data as string into text view
                        textView.setText(resp);
                    }
                });*/
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
