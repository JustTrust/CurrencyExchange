package org.belichenko.a.utils;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * For getting context
 */
public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        // TODO: 18.02.2016 Comment this after debug
        Stetho.initializeWithDefaults(this);

        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}