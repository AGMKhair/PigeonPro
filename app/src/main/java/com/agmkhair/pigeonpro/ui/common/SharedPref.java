package com.agmkhair.pigeonpro.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private final String PREF_NAME = "training_app_preference";
    private SharedPreferences preferences;


    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

/*
    public void saveUser(LoginResponse userResponse) {
        Log.d("User_", "saveUser: called");
        preferences.edit().putInt("id", userResponse.getData().getId());
        preferences.edit().putString("emp_id", userResponse.getData().getEmpId());
        preferences.edit().putString("name", userResponse.getData().getName());
        preferences.edit().putString("designation", userResponse.getData().getDesignation());
        preferences.edit().putString("region", userResponse.getData().getRegion());
        preferences.edit().putString("area", userResponse.getData().getArea());
        preferences.edit().putString("territory", userResponse.getData().getTerritory());
        preferences.edit().putString("town", userResponse.getData().getTown());
        preferences.edit().apply();
    }

    public User getUser()
    {
        User user = new User();
        if (!TextUtils.isEmpty(preferences.getString("emp_id", ""))) {
            user.setId(preferences.getInt("id", -1));
            user.setEmp_id(preferences.getString("emp_id", ""));
            user.setName(preferences.getString("name", ""));
            user.setDesignation(preferences.getString("designation", ""));
            user.setRegion(preferences.getString("region", ""));
            user.setArea(preferences.getString("area", ""));
            user.setTerritory(preferences.getString("territory", ""));
            user.setTown(preferences.getString("town", ""));
            return user;
        }
        return null;

    }*/
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public void saveBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean isPresent(String key) {
        return preferences.contains(key);
    }

}
