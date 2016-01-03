package org.belichenko.a.utils;

import android.app.Application;
import android.content.Context;

/**
 * For getting context
 */
public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}