package com.ihandy.a2014011310;

import android.app.Application;

import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ytl on 2016/9/6.
 */
public class NewskaApplication extends Application{
    public static Context AppContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        Fresco.initialize(AppContext);
    }
}
