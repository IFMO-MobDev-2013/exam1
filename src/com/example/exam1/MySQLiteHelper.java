package com.example.exam1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ENTRIES = "entries";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BRAND_COLOR = "brand";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_BOX = "box";
    public static final String COLUMN_CAR_NUMBER = "carnumber";
    public static final String COLUMN_PHONE_NUMBER = "phonenumber";






    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ENTRIES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_BRAND_COLOR
            + " text not null, " + COLUMN_TIME
            + " integer, " + COLUMN_BOX
            + " integer, " + COLUMN_CAR_NUMBER
            + " text not null, " + COLUMN_PHONE_NUMBER
            + " text not null" +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }

}