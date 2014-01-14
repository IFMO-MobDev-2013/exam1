package com.ctd.CarWash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexei on 14.01.14.
 */
public class OrdersDataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String _ID = "_id";
    public static final String DATABASE_NAME = "orders";
    public static final String CAR_NAME = "name";
    public static final String CAR_COLOR = "color";
    public static final String CAR_NUMBER = "number";
    public static final String PHONE = "phone";
    public static final String TIME = "time";
    public static final String BOX = "box";


    public OrdersDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabase());
    }

    String createDatabase() {
        String temp = "CREATE TABLE " + DATABASE_NAME
                + " (" + CAR_NAME + " TEXT," + CAR_COLOR + " TEXT," + CAR_NUMBER + " TEXT," + PHONE + " TEXT," + TIME + " TEXT," + BOX + " TEXT);";
        return temp;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(dropDataBase());
            onCreate(db);
        }
    }

    String dropDataBase() {
        return "DROP TABLE IF EXISTS " + DATABASE_NAME;
    }

}
