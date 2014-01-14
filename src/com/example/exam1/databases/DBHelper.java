package com.example.exam1.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 31.10.13
 * Time: 4:33
 * To change this template use File | Settings | File Templates.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String dbName, Object o, int dbVersion) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) o, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CarWashInfo.DB_CREATE);
        sqLiteDatabase.execSQL(CarsSchedule.DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}