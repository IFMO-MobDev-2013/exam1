package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "auto";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "auto_table";
    public static final String CARMARK = "carmark";
    public static final String CARCOLOR = "carcolor";
    public static final String TIME = "time";
    public static final String BOX = "box";
    public static final String CARNUMBER = "carnumber";
    public static final String PHONE = "phone";

    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + CARMARK + " text not null, "
            + CARCOLOR + " text not null, "
            + TIME + " text not null, "
            + BOX + " integer not null, "
            + CARNUMBER + " text not null, "
            + PHONE + " text not null ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }


    public void insert(String carmark, String carcolor, String time, int box, String carnumber, String phone) {
        ContentValues cv =  new ContentValues();
        cv.put(CARMARK, carmark); cv.put(CARCOLOR, carcolor);
        cv.put(TIME, time); cv.put(BOX, box);
        cv.put(CARNUMBER, carnumber); cv.put(PHONE, phone);
        db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
    public Car[] getAllCars() {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int size = -1;
        Car[] res = null;
        while (cursor.moveToNext()) {
            if (size == -1) res = new Car[cursor.getCount()];
            String numb = cursor.getString(cursor.getColumnIndex(CARNUMBER));
            if (numb == null) continue;
            String time = cursor.getString(cursor.getColumnIndex(TIME));
            String color = cursor.getString(cursor.getColumnIndex(CARCOLOR));
            String phone = cursor.getString(cursor.getColumnIndex(PHONE));
            String mark = cursor.getString(cursor.getColumnIndex(CARMARK));
            int box = cursor.getInt(cursor.getColumnIndex(BOX));
            size++;
            res[size] = new Car(mark, numb, color, time, box, phone);
        }
        return res;
    }
    public void deleteCar(String carnumber, String time) {
        db.delete(TABLE_NAME, CARNUMBER + " = ? and " + TIME + " = ?", new String[]{carnumber, time});
    }
    public void create() {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public Car getCar(String nnumb) {
        Cursor cursor = db.query(TABLE_NAME, null, CARNUMBER + " = ?", new String[]{nnumb}, null, null, null);
        cursor.moveToFirst();

        String numb = cursor.getString(cursor.getColumnIndex(CARNUMBER));
        String time = cursor.getString(cursor.getColumnIndex(TIME));
        String color = cursor.getString(cursor.getColumnIndex(CARCOLOR));
        String phone = cursor.getString(cursor.getColumnIndex(PHONE));
        String mark = cursor.getString(cursor.getColumnIndex(CARMARK));
        int box = cursor.getInt(cursor.getColumnIndex(BOX));
        return new Car(mark, numb, color, time, box, phone);
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
