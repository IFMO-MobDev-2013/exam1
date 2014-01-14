package com.example.CarCleaning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CarCleaning";
    private static final String DATABASE_TABLE_CC = "CleaningCenter";
    private static final String KEY_CNAME = "cname";
    private static final String KEY_BOXN = "boxn";
    private static final String DATABASE_TABLE = "Cars";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COLOR = "color";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_TELEPHONE = "telephone";
    private static final String KEY_TIME = "time";
    private static final String KEY_BOX = "box";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CreateCarsTable = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " STRING," + KEY_COLOR + " STRING," + KEY_NUMBER + " STRING," + KEY_TELEPHONE + " STRING," + KEY_TIME + " INTEGER," + KEY_BOX + " INTEGER" + ")";
        database.execSQL(CreateCarsTable);
        String CreateCCTable = "CREATE TABLE " + DATABASE_TABLE_CC + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CNAME + " STRING," + KEY_BOXN + " INTEGER" + ")";
        database.execSQL(CreateCCTable);
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, "Default");
        values.put(KEY_BOXN, 0);
        database.insert(DATABASE_TABLE_CC, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CC);
        onCreate(database);
    }

    public String getCName() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE_CC, new String[]{KEY_ID, KEY_CNAME, KEY_BOXN}, KEY_ID + "=?", new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(1);
    }

    public int getBoxNum() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE_CC, new String[]{KEY_ID, KEY_CNAME, KEY_BOXN}, KEY_ID + "=?", new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(2);
    }

    public int setCC(String name, int box) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, name);
        values.put(KEY_BOXN, box);
        return database.update(DATABASE_TABLE_CC, values, KEY_ID + " = ?", new String[]{String.valueOf(1)});
    }

    public void addCar(Car car) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, car.name);
        values.put(KEY_COLOR, car.color);
        values.put(KEY_NUMBER, car.number);
        values.put(KEY_TELEPHONE, car.telephone);
        values.put(KEY_TIME, car.time);
        values.put(KEY_BOX, car.box);
        database.insert(DATABASE_TABLE, null, values);
        database.close();
    }

    public Car getCar(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_COLOR, KEY_NUMBER, KEY_TELEPHONE, KEY_TIME, KEY_BOX}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new Car(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
    }

    public List<Car> getCars() {
        List<Car> statsList = new ArrayList<Car>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Car car = new Car(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
                statsList.add(car);
            } while (cursor.moveToNext());
        }
        return statsList;
    }

    public int updateCar(int id, Car car) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, car.name);
        values.put(KEY_COLOR, car.color);
        values.put(KEY_NUMBER, car.number);
        values.put(KEY_TELEPHONE, car.telephone);
        values.put(KEY_TIME, car.time);
        values.put(KEY_BOX, car.box);
        return database.update(DATABASE_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
