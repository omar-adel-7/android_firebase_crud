package com.example.firebase_crud;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Net15 on 13/12/2016.
 */
public class MyApplication extends Application {

    public MyApplication() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        // for data persistence
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


}
