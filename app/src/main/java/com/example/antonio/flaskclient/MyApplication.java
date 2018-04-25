package com.example.antonio.flaskclient;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by antonio on 23/04/2018.
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    private static final String API_KEY = "XXXX";

    private static String BASE_URL = "http://172.16.110.1:5000/";
    private static String JWT_TOKEN = "";

    private static Context context;

    //private Menu nav_menu = null;

    public static final String TAG = "MyAppList";

    public static String getBaseUrl() {
        return BASE_URL;
    }


    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        // Инициализация AppMetrica SDK
        //YandexMetrica.activate(getApplicationContext(), API_KEY);
        // Отслеживание активности пользователей
        //YandexMetrica.enableActivityAutoTracking(this);
    }

    public static MyApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyApplication();
        }
        return INSTANCE;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static String getJwtToken() {
        return JWT_TOKEN;
    }

    public static void setJwtToken(String jwtToken) {
        JWT_TOKEN = jwtToken;
    }


    public static void backgroundThreadShortToast(final String msg) {

        if (MyApplication.context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}