package com.ifmomd.igushkin.carwashing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sergey on 1/14/14.
 */
public class CarWashingDatabaseHelper {
    //Database
    private static final int    DATABASE_VERSION = 20;
    private static final String DATABASE_NAME    = "washing_data.db";
    private static final String LOG_TAG          = "WashingDBAdapter";

    //Common columns
    static final         String KEY_COMMON_ID      = "_id";
    private static final String KEY_COMMON_CREATED = "created_at";

    //Cars table
    public static final  String TABLE_NAME_CARS   /**/       = "cars";
    public static final  String KEY_CARS_MODEL         /* */ = "model";
    public static final  String KEY_CARS_COLOR               = "color";
    public static final  String KEY_CARS_NUMBER              = "number";
    public static final  String KEY_CARS_PHONE               = "phone";
    public static final  String KEY_CARS_TIME                = "time";
    public static final  String KEY_CARS_BOX                 = "box";
    private static final String CREATE_TABLE_CARS            = "CREATE TABLE " + TABLE_NAME_CARS + " (" +
                                                               KEY_COMMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                               KEY_COMMON_CREATED + " INTEGER NOT NULL DEFAULT 0, " + //v18
                                                               KEY_CARS_MODEL + " TEXT, " +
                                                               KEY_CARS_COLOR + " TEXT, " +
                                                               KEY_CARS_NUMBER + " TEXT, "+
                                                               KEY_CARS_PHONE + " TEXT, " +
                                                               KEY_CARS_TIME + " INTEGER, " +
                                                               KEY_CARS_BOX + " INTEGER)";

    private final Context        mContext;
    private       DatabaseHelper mDbHelper;
    private       SQLiteDatabase mDb;

    public CarWashingDatabaseHelper(Context context) {
        mContext = context;
    }

    public CarWashingDatabaseHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    Cursor getCarsByTime(int time) {
        return mDb.query(TABLE_NAME_CARS,
                         new String[]{KEY_COMMON_ID, KEY_CARS_MODEL, KEY_CARS_COLOR, KEY_CARS_NUMBER, KEY_CARS_PHONE, KEY_CARS_TIME, KEY_CARS_BOX},
                         KEY_CARS_TIME + "=" + time, null, null, null, KEY_CARS_TIME, null);
    }

    Cursor fetchCars() {
        return mDb.query(TABLE_NAME_CARS,
                         new String[]{KEY_COMMON_ID, KEY_CARS_MODEL, KEY_CARS_COLOR, KEY_CARS_NUMBER, KEY_CARS_PHONE, KEY_CARS_TIME, KEY_CARS_BOX},
                         null, null, null, null, KEY_CARS_TIME, null);
    }

    Cursor getCarById(long id) {
        return mDb.query(TABLE_NAME_CARS,
                         new String[]{KEY_COMMON_ID, KEY_CARS_MODEL, KEY_CARS_COLOR, KEY_CARS_NUMBER, KEY_CARS_PHONE, KEY_CARS_TIME, KEY_CARS_BOX},
                         KEY_COMMON_ID+"="+id, null, null, null, KEY_CARS_TIME, null);
    }

    boolean[] getBoxesByTime(int time, int maxBoxes) {
        Cursor c = getCarsByTime(time);
        boolean[] boxTaken = new boolean[maxBoxes];
        int boxColumn = c.getColumnIndex(KEY_CARS_BOX);
        if (c.moveToFirst())
            do {
                boxTaken[c.getInt(boxColumn)] = true;
            } while (c.moveToNext());
        return boxTaken;
    }

    boolean putCar(String model, String color, String number, String phone, int time, int maxBoxes) {
        boolean[] boxes = getBoxesByTime(time, maxBoxes);
        int box = 0;
        for (; box < maxBoxes; ++box)
            if (!boxes[box])
                break;

        ContentValues values = new ContentValues();
        values.put(KEY_CARS_MODEL, model);
        values.put(KEY_CARS_COLOR, color);
        values.put(KEY_CARS_PHONE, phone);
        values.put(KEY_CARS_TIME, time);
        values.put(KEY_CARS_BOX, box);

        return mDb.insert(TABLE_NAME_CARS, null, values) == 1;
    }

    boolean editCar(long id, String model, String color, String number, String phone, int time, int maxBoxes) {
        Cursor c = getCarById(id);
        c.moveToFirst();
        int old_time = c.getInt(c.getColumnIndex(KEY_CARS_TIME));
        int old_box = c.getInt(c.getColumnIndex(KEY_CARS_BOX));

        boolean[] boxes = getBoxesByTime(time, maxBoxes);
        int box = 0;
        for (; box < maxBoxes; ++box)
            if (!boxes[box])
                break;

        ContentValues values = new ContentValues();
        values.put(KEY_CARS_MODEL, model);
        values.put(KEY_CARS_COLOR, color);
        values.put(KEY_CARS_PHONE, phone);
        values.put(KEY_CARS_TIME, time);
        values.put(KEY_CARS_BOX, time == old_time ? old_box : box);

        return mDb.update(TABLE_NAME_CARS, values, KEY_COMMON_ID+"="+id, null) == 1;
    }

    public void batchDelete(ArrayList<Long> idsToDelete) {
        for (Long l : idsToDelete)
        {
            mDb.delete(TABLE_NAME_CARS, KEY_COMMON_ID+"="+l, null);
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        Context mCtx;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mCtx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CARS);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                           + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CARS);
            onCreate(db);
        }
    }

}
