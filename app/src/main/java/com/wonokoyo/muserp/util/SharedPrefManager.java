package com.wonokoyo.muserp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_RPA_APP = "spRpaApp";
    public static final String SP_ID_USER = "spIdUser";
    public static final String SP_NAMA_USER = "spNamaUser";
    public static final String SP_LOGIN = "spLogin";

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

    public void saveSPBoolean(String key, Boolean value) {
        spEditor.putBoolean(key, value);
        spEditor.commit();
    }

    public String getSpIdUser() {
        return sp.getString(SP_ID_USER, "");
    }

    public String getSpNamaUser() {
        return sp.getString(SP_NAMA_USER, "");
    }

    public Boolean getSpLogin() {
        return sp.getBoolean(SP_LOGIN, false);
    }

    public void clearLogin() {
        saveSPString(SP_ID_USER, "");
        saveSPBoolean(SP_LOGIN, false);
    }
}
