package com.wonokoyo.pakan.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_RPA_APP = "spPakanApp";
    public static final String SP_ID_USER = "spIdUser";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_RPA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String key, String value) {
        spEditor.putString(key, value);
        spEditor.commit();
    }

    public String getSpIdUser() {
        return sp.getString(SP_ID_USER, "");
    }
}
