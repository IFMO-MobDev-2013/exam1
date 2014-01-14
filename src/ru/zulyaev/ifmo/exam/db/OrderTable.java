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
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_TIME = "time";
    private static final String FIELD_BOX = "box";

    private static final String[] ALL_FIELDS = {
            TABLE_NAME,
            FIELD_ID,
            FIELD_MODEL,
            FIELD_COLOR,
            FIELD_PHONE,
            FIELD_TIME,
            FIELD_BOX
    };

    private static final String CREATE_TABLE_QUERY = String.format(
            "create table %s (" +
                "%s integer not null primary key autoincrement," +
                "%s text not null," +
                "%s text not null," +
                "%s text not null," +
                "%s integer not null," +
                "%s integer not null" +
            ")",
            ALL_FIELDS
    );

    private static final String DROP_TABLE_QUERY = "drop table if exists " + TABLE_NAME;

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
        calendar.set(Calendar.HOUR, 0);
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
                                c.getLong(4),
                                c.getLong(5)
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

    public long addOrder(String model, String color, String phone, long timeOffset, long box) {
        ContentValues values = new ContentValues(5);
        values.put(FIELD_MODEL, model);
        values.put(FIELD_COLOR, color);
        values.put(FIELD_PHONE, phone);
        values.put(FIELD_TIME, getDayStartTime() + timeOffset);
        values.put(FIELD_BOX, box);
        return db.insert(TABLE_NAME, null, values);
    }
}
