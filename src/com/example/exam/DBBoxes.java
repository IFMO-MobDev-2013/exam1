package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBBoxes {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "box";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "boxes_table";
    public static final String BOX = "box";
    public static final String TIME = "time";
    public static final String COUNT = "count";

    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + TIME + " text not null, "
            + BOX + " integer not null, "
            + COUNT + " integer not null ); ";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBBoxes(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }


    public void insert(String[] boxes, String count) {
        int c = Integer.parseInt(count);
        ContentValues cv;
        for (int i = 1; i <= c; i++) {
            for (int j = 0; j < boxes.length; j++) {
                cv = new ContentValues();
                cv.put(BOX, i); cv.put(TIME, boxes[j]); cv.put(COUNT, 0);
                db.insert(TABLE_NAME, null, cv);
            }
        }
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public int getBox(String time) {
        Cursor cursor = db.query(TABLE_NAME, null, TIME + "= ?", new String[]{time}, null, null, null);
        String bb = "1";
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(BOX)) == 0) {
                bb = Integer.toString(cursor.getInt(cursor.getColumnIndex(BOX)));
                ContentValues cv = new ContentValues();
                cv.put(TIME, time); cv.put(BOX, cursor.getInt(cursor.getColumnIndex(BOX))); cv.put(COUNT, 1);
                db.update(TABLE_NAME,cv,TIME + " = ? and " + BOX + " = ?", new String[]{time, bb});
                break;
            }
        }
        return Integer.parseInt(bb);
    }

    public String[] getTimes(String[] times) {
        Cursor cursor;
        String[] res = new String[44]; int size = 0;
        for (int i = 0; i < times.length; i++) {
            cursor = db.query(TABLE_NAME, null, TIME + " = ?", new String[]{times[i]}, null ,null, null);
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(COUNT)) == 0) {
                    size++; res[size] = times[i];
                    break;
                }
            }
        }
        res[0] = Integer.toString(size);
        return res;
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
