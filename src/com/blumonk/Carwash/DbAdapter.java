package com.blumonk.Carwash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blumonk on 1/14/14.
 */
public class DbAdapter {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ordersdb";

    private static final String TABLE_ORDERS = "orders";
    public static final String KEY_ID = "_id";
    public static final String KEY_BOX_NUMBER = "box";
    public static final String KEY_CAR_MODEL = "model";
    public static final String KEY_CAR_COLOUR = "colour";
    public static final String KEY_CAR_NUMBER = "number";
    public static final String KEY_TELEPHONE = "phone";
    public static final String KEY_ORDER_TIME = "time";

    public static final String TABLE_REGISTRATION = "register";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BOXES_NUMBER = "boxesnumber";

    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BOX_NUMBER + " INTEGER NOT NULL, " +
            KEY_CAR_MODEL + " TEXT NOT NULL," + KEY_CAR_COLOUR + " TEXT NOT NULL, " +
            KEY_CAR_NUMBER + " TEXT NOT NULL, " + KEY_TELEPHONE + " TEXT NOT NULL, " +
            KEY_ORDER_TIME + " TEXT NOT NULL)";

    private static final String CREATE_REGISTER = "CREATE TABLE " + TABLE_REGISTRATION + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT NOT NULL, " +
            KEY_BOXES_NUMBER + " INTEGER NOT NULL)";

    public DbAdapter(Context context) {
        this.context = context;
    }

    private static class DbHelper extends SQLiteOpenHelper {
        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CONTACTS_TABLE);
            db.execSQL(CREATE_REGISTER);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
            onCreate(db);
        }
    }

    public DbAdapter open() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public void addOrder(int boxNumber, String carModel, String carColour,
                         String carNumber, String telephone, String orderTime) {
        ContentValues values = new ContentValues();
        values.put(KEY_BOX_NUMBER, boxNumber);
        values.put(KEY_CAR_MODEL, carModel);
        values.put(KEY_CAR_COLOUR, carColour);
        values.put(KEY_CAR_NUMBER, carNumber);
        values.put(KEY_TELEPHONE, telephone);
        values.put(KEY_ORDER_TIME, orderTime);
        db.insert(TABLE_ORDERS, null, values);
    }

    public Cursor getAllOrders() {
        return db.query(TABLE_ORDERS, new String[] {KEY_ID, KEY_BOX_NUMBER, KEY_CAR_MODEL,
                KEY_CAR_COLOUR, KEY_CAR_NUMBER, KEY_TELEPHONE, KEY_ORDER_TIME},
                null, null, null, null/*KEY_ORDER_TIME + " ASC"*/, null);
    }

    public void deleteOrder(int id) {
        db.delete(TABLE_ORDERS, KEY_ID + " = " + id, null);
    }

    public void signUp(String name, int boxes) {
        ContentValues content = new ContentValues();
        content.put(KEY_TITLE, name);
        content.put(KEY_BOXES_NUMBER, boxes);
        db.insert(TABLE_REGISTRATION, null, content);
    }

    public Cursor getRegisterData() {
        return db.query(TABLE_REGISTRATION, new String[] {KEY_ID, KEY_TITLE, KEY_BOXES_NUMBER},
                null, null, null, null, null);
    }


}
