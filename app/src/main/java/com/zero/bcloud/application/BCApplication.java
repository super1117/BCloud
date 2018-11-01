package com.zero.bcloud.application;

import android.app.Application;

import com.zero.library.Library;

public class BCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.init(this.getApplicationContext());
    }
}
