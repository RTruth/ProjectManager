package com.edu.prathm.mybim.extra;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Prathm on 8/16/2015.
 */
public class FileOperator {

    public static void addEntryToSharedPreference(Context context, String key, String value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getEntryOfSharedPreference(Context context, String key) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(key, null);
        return ans;
    }
}
