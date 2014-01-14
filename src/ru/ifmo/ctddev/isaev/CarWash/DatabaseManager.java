package ru.ifmo.ctddev.isaev.CarWash;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

class DatabaseManager {
    private static volatile DatabaseManager instance;
    private DatabaseHelper helper;

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init(Context context) {
        if (helper == null)
            helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public void release() {
        if (helper != null)
            OpenHelperManager.releaseHelper();
    }

    public DatabaseHelper getHelper() {
        return helper;
    }
}