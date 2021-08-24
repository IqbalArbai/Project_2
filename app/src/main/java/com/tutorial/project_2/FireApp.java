package com.tutorial.project_2;

import android.app.Application;

import com.firebase.client.Firebase;

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

    }
}
