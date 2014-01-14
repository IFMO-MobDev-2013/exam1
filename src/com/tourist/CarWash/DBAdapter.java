package com.tourist.CarWash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CarWash";
    private static final int DATABASE_VERSION = 5;
    private static final String WASH_NAME_TABLE = "wash";
    private static final String CARS_TABLE = "cars";

    public static final String KEY_ID = "_id";
    public static final String KEY_WASH_NAME = "washName";
    public static final String KEY_BOX_COUNT = "boxCount";
    public static final String KEY_BRAND = "brand";
    public static final String KEY_COLOR = "color";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_TIME = "time";
    public static final String KEY_BOX = "box";

    private static final String INIT_WASH_NAME =
            "create table if not exists " + WASH_NAME_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_WASH_NAME + " text not null, "
                    + KEY_BOX_COUNT + " integer not null)";

    private static final String INIT_CARS =
            "create table if not exists " + CARS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_BRAND + " text not null, "
                    + KEY_COLOR + " text not null, "
                    + KEY_NUMBER + " text not null, "
                    + KEY_PHONE + " text not null, "
                    + KEY_TIME + " text not null, "
                    + KEY_BOX + " integer not null)";

    private static final String REMOVE_WASH_NAME =
            "drop table if exists " + WASH_NAME_TABLE;

    private static final String REMOVE_CARS =
            "drop table if exists " + CARS_TABLE;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(INIT_WASH_NAME);
            db.execSQL(INIT_CARS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(REMOVE_WASH_NAME);
            db.execSQL(REMOVE_CARS);
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDb.close();
        mDbHelper.close();
    }

    public long setWashName(String washName, int boxCount) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_WASH_NAME, washName);
        initialValues.put(KEY_BOX_COUNT, boxCount);
        return mDb.insert(WASH_NAME_TABLE, null, initialValues);
    }

    public Cursor getWashName() {
        return mDb.query(WASH_NAME_TABLE, new String[] {KEY_WASH_NAME, KEY_BOX_COUNT},
                null, null, null, null, null);
    }

    public long addCar(String brand, String color, String number, String phone, String time, int box) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BRAND, brand + "\n" + color);
        initialValues.put(KEY_COLOR, color);
        initialValues.put(KEY_NUMBER, number);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_TIME, time);
        initialValues.put(KEY_BOX, box);
        return mDb.insert(CARS_TABLE, null, initialValues);
    }

    public Cursor fetchCars() {
        return mDb.query(CARS_TABLE, new String[] {KEY_ID, KEY_BRAND, KEY_COLOR, KEY_NUMBER,
                KEY_PHONE, KEY_TIME, KEY_BOX}, null, null, null, null, KEY_TIME + ", " + KEY_BOX);
    }

}
