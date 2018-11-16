package com.atv.anhnd.mydictionaryapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Global {

    //Save default state
    public static void saveState(Activity activity, String key, String value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Get default state
    public static String getState(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}
