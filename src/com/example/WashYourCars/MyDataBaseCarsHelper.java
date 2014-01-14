package com.example.WashYourCars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseCarsHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String _ID = "_id";
    public static final String DATABASE_NAME = "washingcarsdb";
    public static final String MODEL = "model";
    public static final String COLOR = "color";
    public static final String TIME = "time";
    public static final String BOX = "box";
    public static final String PHONE = "phone";
    public static final String SIGN = "sign";

    public static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_NAME
            + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MODEL + " TEXT," + COLOR + " TEXT," + TIME + " TEXT," + BOX + " TEXT," + PHONE + " TEXT);";

    public static final String DROP_DATABASE = "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public MyDataBaseCarsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL(DROP_DATABASE);
            onCreate(db);
        }
    }
}