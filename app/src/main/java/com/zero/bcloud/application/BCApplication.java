package com.zero.bcloud.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.zero.library.Library;

public class BCApplication extends Application {

    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        Library.init(context);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public static Context getContext() {
        return context;
    }
}
