package com.example.notesapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SharedPrefs {
    private static SharedPreferences sharedPrefs =null;
    private static String SHARED_PREFS_NAME;                      /*xml file name*/
    private static SharedPreferences.Editor prefsEditor;

    public SharedPrefs(Context context, String sharedPrefsName){
        this.SHARED_PREFS_NAME=sharedPrefsName;
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
    }

    //Overloaden Reading Methods
    public String readMap(String key, String defValue)          { return sharedPrefs.getString(key, defValue); }
    public boolean readMap(String key, boolean defValue)        { return sharedPrefs.getBoolean(key, defValue); }
    public Integer readMap(String key, int defValue)            { return sharedPrefs.getInt(key, defValue); }
    public Float readMap(String key, float defValue)             { return sharedPrefs.getFloat(key,defValue); }
    public Long readMap(String key, Long defValue)               { return sharedPrefs.getLong(key,defValue); }
    public Set<String> readMap(String key, Set<String> defValue){ return sharedPrefs.getStringSet(key,defValue); }
    public static Map readMap()                                        { return sharedPrefs.getAll(); }
    public ArrayList readValues()                               { Collection<String> vals= readMap().values(); return new ArrayList<String>(vals);}
    public ArrayList readKeys()                                 { Collection<String> keys=readMap().keySet(); return new ArrayList<String>(keys); }

    //Overloaden Writing Methods
    public static void write(String key, String value)      { prefsEditor = sharedPrefs.edit(); prefsEditor.putString(key, value).commit();}
    public void write(String key, boolean value)         { prefsEditor = sharedPrefs.edit(); prefsEditor.putBoolean(key, value).commit();}
    public void write(String key, Integer value)         { prefsEditor = sharedPrefs.edit(); prefsEditor.putInt(key, value).commit();}
    public void write(String key, Set<String> value)     { prefsEditor = sharedPrefs.edit(); prefsEditor.putStringSet(key, value).commit();}
    public void write(String key, Float value)           { prefsEditor = sharedPrefs.edit(); prefsEditor.putFloat(key, value).commit();}
    public void write(String key, Long value)            { prefsEditor = sharedPrefs.edit(); prefsEditor.putLong(key, value).commit();}

    //Removing Value
    public String remove(String key){
        String removedVal="";
        if(getSize()>0){
            removedVal=(String) sharedPrefs.getString(key,"Not Found");
            prefsEditor= sharedPrefs.edit(); prefsEditor.remove(key).commit();
        }
        return removedVal;
    }

    public void clear(){ write("",""); prefsEditor.clear().commit();}        //Clear Preferences
    public int getSize(){return readMap().size();}      //Return Preferences size
}
