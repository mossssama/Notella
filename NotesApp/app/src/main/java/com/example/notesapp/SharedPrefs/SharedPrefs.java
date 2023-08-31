package com.example.notesapp.sharedPrefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static SharedPreferences sharedPrefs =null;
    private static String SHARED_PREFS_NAME;                      /*xml file name*/
    private static SharedPreferences.Editor prefsEditor;

    public SharedPrefs(Context context, String sharedPrefsName){
        this.SHARED_PREFS_NAME=sharedPrefsName;
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
    }

    public boolean readMap(String key, boolean defValue)        { return sharedPrefs.getBoolean(key, defValue); }

    public void write(String key, boolean value)         { prefsEditor = sharedPrefs.edit(); prefsEditor.putBoolean(key, value).commit();}

}
