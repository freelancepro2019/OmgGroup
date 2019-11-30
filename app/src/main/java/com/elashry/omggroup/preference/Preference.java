package com.elashry.omggroup.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class Preference {
    private static Preference instance = null;
    private Preference()
    {

    }

    public static synchronized Preference newInstance()
    {
        if (instance==null){
            instance = new Preference();
        }
        return instance;
    }

    public String getUserId(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        if (preferences.getString("id","").isEmpty())
        {
            saveUserId(context, UUID.randomUUID().toString());
        }
        return preferences.getString("id", UUID.randomUUID().toString());
    }

    public void saveUserId(Context context ,String id)
    {
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",id);
        editor.apply();

    }
}
