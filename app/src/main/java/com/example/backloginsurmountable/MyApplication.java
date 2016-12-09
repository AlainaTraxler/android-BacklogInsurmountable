package com.example.backloginsurmountable;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Guest on 12/9/16.
 */

public class MyApplication extends Application {
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        Log.v("//////", "Triggered");

        if(mSharedPreferences.getString("Remember", null) == null){
            FirebaseAuth.getInstance().signOut();
        }
    }
}
