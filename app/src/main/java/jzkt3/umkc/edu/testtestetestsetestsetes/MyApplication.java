package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.app.Application;
import android.content.Context;

/**
 * Created by jzz on 3/1/2015.
 */
public class MyApplication extends Application {

    public static final String API_KEY = "c0176da4dc914579900662e7d829a161";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static MyApplication getInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
