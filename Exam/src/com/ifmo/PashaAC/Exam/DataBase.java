package com.ifmo.PashaAC.Exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    public static final String DATABASE_NAME = "exam.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "auto_wash_table";
    public static final String KEY_ID = "_id";

    public static final String KEY_AUTO = "auto_mark";
    public static final String KEY_COLOUR = "auto_colour";
    public static final String KEY_AUTO_NUMBER = "auto_number";
    public static final String KEY_TIME = "auto_time";
    public static final String KEY_TELEPHONE_NUMBER = "telephone_number";
    public static final String KEY_BOX_NUMBER = "box_number";


    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_AUTO + " TEXT NOT NULL, "
            + KEY_COLOUR + " TEXT NOT NULL, "
            + KEY_AUTO_NUMBER + " TEXT NOT NULL, "
            + KEY_TIME + " TEXT NOT NULL, "
            + KEY_TELEPHONE_NUMBER + " TEXT NOT NULL, "
            + KEY_BOX_NUMBER + " TEXT NOT NULL);";

    public long insertAutoWashingName(String name, int box) {
        ContentValues values = new ContentValues();
        values.put(KEY_AUTO, name);
        values.put(KEY_COLOUR, "");
        values.put(KEY_AUTO_NUMBER, "");
        values.put(KEY_TIME, "");
        values.put(KEY_TELEPHONE_NUMBER, "");
        values.put(KEY_BOX_NUMBER, box);
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public DataBase(Context context) {
         this.context = context;
    }

    public DataBase open() throws SQLiteException {
         databaseHelper = new DatabaseHelper(context);
         sqLiteDatabase = databaseHelper.getWritableDatabase();
         return this;
    }

    public void close() {
         databaseHelper.close();
    }

    public boolean isEmpty() {
        newCursor();
        if (cursor.moveToNext()) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getAutoWashingName() {
        newCursor();
        cursor.moveToNext();
        return cursor.getString(cursor.getColumnIndex(KEY_AUTO));
    }

    public int getAutoWashingBox() {
        newCursor();
        cursor.moveToNext();
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_BOX_NUMBER)));
    }

    public Cursor getCursor() {
        newCursor();
        cursor.moveToNext();
        return cursor;
    }

    public long insertAuto(String mark, String colour, String auto_number, String time, String telephone, String box) {
        ContentValues values = new ContentValues();
        values.put(KEY_AUTO, mark);
        values.put(KEY_COLOUR, colour);
        values.put(KEY_AUTO_NUMBER, auto_number);
        values.put(KEY_TIME, time);
        values.put(KEY_TELEPHONE_NUMBER, telephone);
        values.put(KEY_BOX_NUMBER, box);
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public void newCursor() {
         cursor = sqLiteDatabase.query(TABLE_NAME, new String[] {
                KEY_ID, KEY_AUTO, KEY_COLOUR, KEY_AUTO_NUMBER, KEY_TIME, KEY_TELEPHONE_NUMBER, KEY_BOX_NUMBER},
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("TABLE OF CREATE", DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }
    }
}
