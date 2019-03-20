package com.bw.project_demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
