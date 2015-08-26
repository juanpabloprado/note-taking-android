package com.juanpabloprado.android.notesandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Juan on 8/7/2015.
 */
public class NotesAndroidSettings {
    SharedPreferences mSharedPreferences;

    public NotesAndroidSettings(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getAccessTokenPreference() {
        return mSharedPreferences.getString("AccessToken", null);
    }

    public String getUsernamePreference() {
        return mSharedPreferences.getString("Username", null);
    }

    public void setAccessTokenPreference(String accessToken) {
        mSharedPreferences
                .edit()
                .putString("AccessToken", accessToken)
                .apply();
    }

    public void setUsernamePreference(String username) {
        mSharedPreferences
                .edit()
                .putString("Username", username)
                .apply();
    }
}
