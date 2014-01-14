package ru.zulyaev.ifmo.exam.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderTable {
    private static final String TABLE_NAME = "orders";

    private static final String FIELD_ID = "_id";
    private static final String FIELD_MODEL = "model";
    private static final String FIELD_COLOR = "color";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_TIME = "time";
    private static final String FIELD_BOX = "box";

    private static final String[] ALL_FIELDS = {
            FIELD_ID,
            FIELD_MODEL,
            FIELD_COLOR,
            FIELD_NUMBER,
            FIELD_PHONE,
            FIELD_TIME,
            FIELD_BOX
    };

    private static final String CREATE_TABLE_QUERY = String.format(
            "create table " + TABLE_NAME + " (" +
                "%s integer not null primary key autoincrement," +
                "%s text not null," +
                "%s text not null," +
                "%s text not null," +
                "%s text not null," +
                "%s integer not null," +
                "%s integer not null" +
            ")",
            ALL_FIELDS
    );

    private static final String DROP_TABLE_QUERY = "drop table if exists " + TABLE_NAME;
    private static final String NOT_FREE_TIME_QUERY = String.format(
            "select %s, count(0) as cnt from %s where %s >= ? and %s < ? group by %s order by %s asc",
            FIELD_TIME,
            TABLE_NAME,
            FIELD_TIME,
            FIELD_TIME,
            FIELD_TIME,
            FIELD_TIME
    );

    public static void init(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_QUERY);
    }

    private final SQLiteDatabase db;

    public OrderTable(SQLiteDatabase db) {
        this.db = db;
    }

    private static long getDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public List<Order> getTodayOrders() {
        long from = getDayStartTime();
        long to = from + 24 * 3600 * 1000;
        Cursor c = db.query(TABLE_NAME, ALL_FIELDS, FIELD_TIME + " >= ? and " + FIELD_TIME + " < ?", new String[] {Long.toString(from), Long.toString(to)}, null, null, FIELD_TIME);
        try {
            List<Order> result = new ArrayList<Order>();
            while (c.moveToNext()) {
                result.add(
                        new Order(
                                c.getLong(0),
                                c.getString(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4),
                                c.getLong(5),
                                c.getLong(6)
                        )
                );
            }
            return result;
        } finally {
            c.close();
        }
    }

    public boolean removeOrder(long id) {
        return db.delete(TABLE_NAME, FIELD_ID + " = ?", new String[] {Long.toString(id)}) != 0;
    }

    public long addOrder(String model, String color, String number, String phone, long timeOffset, long box) {
        ContentValues values = new ContentValues(6);
        values.put(FIELD_MODEL, model);
        values.put(FIELD_COLOR, color);
        values.put(FIELD_NUMBER, number);
        values.put(FIELD_PHONE, phone);
        values.put(FIELD_TIME, getDayStartTime() + timeOffset);
        values.put(FIELD_BOX, box);
        return db.insert(TABLE_NAME, null, values);
    }


    public List<Long> getNotFreeTime(long boxes) {
        long from = getDayStartTime();
        long to = from + 24 * 3600 * 1000;
        Cursor c = db.rawQuery(NOT_FREE_TIME_QUERY, new String[]{Long.toString(from), Long.toString(to)});
        try {
            List<Long> result = new ArrayList<Long>();
            while (c.moveToNext()) {
                if (c.getLong(1) == boxes) {
                    result.add(c.getLong(0));
                }
            }
            return result;
        } finally {
            c.close();
        }
    }

    public List<Long> getNotFreeBoxes(long timeOffset) {
        long time = getDayStartTime() + timeOffset;
        Cursor c = db.query(TABLE_NAME, new String[]{FIELD_BOX}, FIELD_TIME + " = ?", new String[]{Long.toString(time)}, null, null, null);
        try {
            List<Long> boxes = new ArrayList<Long>();
            while (c.moveToNext()) {
                boxes.add(c.getLong(0));
            }
            return boxes;
        } finally {
            c.close();
        }
    }
}
