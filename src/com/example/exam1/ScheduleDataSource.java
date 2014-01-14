package com.example.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleDataSource {

    // Database fields
    private int NUMBER_OF_BOXES;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_BRAND_COLOR,
            MySQLiteHelper.COLUMN_TIME,
            MySQLiteHelper.COLUMN_BOX,
            MySQLiteHelper.COLUMN_PHONE_NUMBER,
            MySQLiteHelper.COLUMN_CAR_NUMBER};
    private String[] columnTime = {
            MySQLiteHelper.COLUMN_TIME
    };
    public ScheduleDataSource(Context context) {
        SharedPreferences settings = context.getSharedPreferences(CreateWashing.MY_PREFS, 0);
        NUMBER_OF_BOXES = settings.getInt("Boxes", 0);
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Entry createEntry(String brand_color, int time, int box, String car_number, String phone_number) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_BRAND_COLOR, brand_color);
        values.put(MySQLiteHelper.COLUMN_TIME, time);
        values.put(MySQLiteHelper.COLUMN_BOX, box);
        values.put(MySQLiteHelper.COLUMN_CAR_NUMBER, car_number);
        values.put(MySQLiteHelper.COLUMN_PHONE_NUMBER, phone_number);
        long insertId = database.insert(MySQLiteHelper.TABLE_ENTRIES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Entry newEntry = cursorToEntry(cursor);
        cursor.close();
        return newEntry;
    }

    public void deleteEntry(Entry entry) {
        int id = entry.id;
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ENTRIES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Entry> getAllEntries() {
        List<Entry> comments = new ArrayList<Entry>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_TIME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Entry entry = cursorToEntry(cursor);
            comments.add(entry);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
    public List<MyTime> getAvailableEntries() {
        List<MyTime> times = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES, columnTime
                , null, null, null, null, MySQLiteHelper.COLUMN_TIME);
        cursor.moveToFirst();

        for (int i = 480; i <= 1320; i+= 30) {
            MyTime t;
            int time = 0;
            if (!cursor.isAfterLast()) {
                time = cursor.getInt(0);
            }
            int cnt = 0;
            while (!cursor.isAfterLast() && (cursor.getInt(0) == i)) {
                cnt++;
                cursor.moveToNext();
            }
            if (cnt == 0) {
                times.add(new MyTime(i));
            } else  if (cnt < NUMBER_OF_BOXES) {
                times.add(new MyTime(time));
            }


        }
        return times;

    }

    private Entry cursorToEntry(Cursor cursor) {
        int id = cursor.getInt(0);
        String brand_number = cursor.getString(1);
        int time = cursor.getInt(2);
        int box = cursor.getInt(3);
        String phone_nubmer = cursor.getString(4);
        String car_number = cursor.getString(5);
        Entry entry = new Entry(id, brand_number, time, box, phone_nubmer, car_number);
        return entry;
    }
}