package com.ipixel.qrscannerapp.data.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;



/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class AppPreference {

    // declare context
    private static Context mContext;

    // singleton
    private static AppPreference appPreference = null;

    // common
    private SharedPreferences sharedPreferences, settingsPreferences;
    private SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if(appPreference == null) {
            mContext = context;
            appPreference = new AppPreference();
        }
        return appPreference;
    }
    private AppPreference() {
        sharedPreferences = mContext.getSharedPreferences(PrefKey.APP_PREF_NAME, Context.MODE_PRIVATE);
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key , value);
        editor.commit();
    }
    public String getString(String key) {
        String string = sharedPreferences.getString(key, null);
        return string;
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void setStringArray(String key, ArrayList<String> values) {
        if (values != null && !values.isEmpty()) {
            String value = "";
            for (String str : values) {
                if(value.isEmpty()) {
                    value = str;
                } else {
                    value = value + "," + str;
                }
            }
            setString(key, value);
        }
    }

    public ArrayList<String> getStringArray(String key) {
        ArrayList<String> arrayList = new ArrayList<>();
        String value = getString(key);
        if (value != null) {
            arrayList = new ArrayList<>(Arrays.asList(value.split(",")));
        }
        return arrayList;
    }


}
