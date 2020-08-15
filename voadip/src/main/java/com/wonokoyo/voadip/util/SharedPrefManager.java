package com.wonokoyo.voadip.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_VOADIP_APP = "spVoadipApp";
    public static final String SP_ID_USER = "spIdUser";
    public static final String SP_LAST_SYNC = "spSync";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_VOADIP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String key, String value) {
        spEditor.putString(key, value);
        spEditor.commit();
    }

    public String getSpLastSync() {
        return sp.getString(SP_LAST_SYNC, "");
    }

    public String getSpIdUser() {
        return sp.getString(SP_ID_USER, "");
    }
}
