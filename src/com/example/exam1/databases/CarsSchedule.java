package com.example.exam1.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.exam1.Cars;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class CarsSchedule {
    private static final String DB_NAME = "carwash1";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "schedule1";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CAR = "carmark";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WINDOW = "window";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_CAR + " text, " +
                    COLUMN_COLOR + " text, " +
                    COLUMN_TIME + " text, " +
                    COLUMN_WINDOW + " text" +
                    ");";

    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public CarsSchedule(Context context) {
        this.context = context;
    }

    public void open() {
        mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, COLUMN_TIME);
    }

    public void addChannel(Cars car) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CAR, car.getCarMark());
        cv.put(COLUMN_COLOR, car.getColor());
        cv.put(COLUMN_TIME, car.getTime());
        cv.put(COLUMN_WINDOW, car.getWindow());
        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public ArrayList<Cars> getAll() {
        Cursor cursor = getAllData();
        ArrayList<Cars> result = new ArrayList<Cars>();
        while (cursor.moveToNext()) {
            Cars current = new Cars();
            current.setCarMark(cursor.getString(cursor.getColumnIndex(COLUMN_CAR)));
            current.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR)));
            current.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
            current.setWindow(cursor.getString(cursor.getColumnIndex(COLUMN_WINDOW)));
            result.add(current);
        }
        return result;
    }

    public Cars selectCars(String name) {
        ArrayList<Cars> carses = getAll();
        for (int i = 0; i < carses.size(); i++)
            if (carses.get(i).getCarMark().equals(name))
                return carses.get(i);
        return null;
    }
}
