package ru.zulyaev.ifmo.exam;

import android.content.Context;
import android.content.SharedPreferences;

public class DefaultSettings {
    private static final String PREFS_NAME = DefaultSettings.class.toString();
    private static final int PREFS_MODE = Context.MODE_PRIVATE;

    private static final String INITIALIZED = "INITIALIZED";
    private static final String CAR_WASH_NAME = "CAR_WASH_NAME";
    private static final String BOX_NUMBER = "BOX_NUMBER";

    private final SharedPreferences preferences;

    public DefaultSettings(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
    }

    public boolean isInitialized() {
        return preferences.getBoolean(INITIALIZED, false);
    }

    public String getCarWashName() {
        return preferences.getString(CAR_WASH_NAME, null);
    }

    public int getBoxNumber() {
        return preferences.getInt(BOX_NUMBER, -1);
    }

    public void init(String carWashName, int boxNumber) {
        preferences.edit()
                .putString(CAR_WASH_NAME, carWashName)
                .putInt(BOX_NUMBER, boxNumber)
                .putBoolean(INITIALIZED, true)
                .apply();
    }
}
