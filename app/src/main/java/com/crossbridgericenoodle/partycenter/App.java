package com.crossbridgericenoodle.partycenter;

import android.app.Application;
import android.content.Context;

/**
 * Created by qianzise on 2016/10/15 0015.
 */
public class App extends Application {

    public static Context getContext() {
        return context;
    }

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }


}
