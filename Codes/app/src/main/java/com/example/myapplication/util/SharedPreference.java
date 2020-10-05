package com.example.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    Context c;
    SharedPreferences sharedPref;
    private final String SHARED_LIB = "DETAILS";
    private final String MOBILE = "Mobile";

    public SharedPreference(Context context) {
        c = context;
        sharedPref = c.getSharedPreferences("Preferences", Activity.MODE_PRIVATE);
    }

    public boolean setPrefValue(String tag, String tagValue) {
        try {
            SharedPreferences.Editor edit = sharedPref.edit();

            edit.putString(tag,tagValue);
            edit.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPrefValue(String tag) {
        return sharedPref.getString(tag, "");
    }


}
