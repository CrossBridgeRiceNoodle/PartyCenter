package com.crossbridgericenoodle.partycenter;

import android.app.Application;
import android.content.Context;

/**
 * APP全局入口,定义了一个可以方便获取上下文的方法
 */
public class App extends Application {

    public static Context getContext() {
        return context;
    }

    private static Context context;//全局的上下文获取

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


}
