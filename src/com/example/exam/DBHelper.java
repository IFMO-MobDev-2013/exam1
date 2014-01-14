package com.example.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String _ID = "_id";
    public static final String DATABASE_NAME = "emax1_db";
    public static final String NAME = "name";
    public static final String WIN = "win";
    public static final String MARK = "mark";
    public static final String COLOR = "color";
    public static final String TIME = "time";
    public static final String WINDOW = "window";
    public static final String NUMBER = "number";
    public static final String PHONE = "phone";


    public static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " + WIN + " TEXT, " + MARK + " TEXT, " + COLOR + " TEXT, " + TIME +
            " TEXT, " + WINDOW + " TEXT, " + NUMBER + " TEXT, " + PHONE + " TEXT" + ");";

    public static final String DROP_DATABASE = "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        if (old_v != new_v) {
            db.execSQL(DROP_DATABASE);
            onCreate(db);
        }
    }
}
