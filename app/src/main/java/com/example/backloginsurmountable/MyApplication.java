package com.example.backloginsurmountable;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

/**
 * Created by Guest on 12/9/16.
 */

public class MyApplication extends Application {
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;
    public ArrayList<String> mMasterNESList = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        if(mSharedPreferences.getString("Remember", null) == null){
            FirebaseAuth.getInstance().signOut();
        }
    }
}
