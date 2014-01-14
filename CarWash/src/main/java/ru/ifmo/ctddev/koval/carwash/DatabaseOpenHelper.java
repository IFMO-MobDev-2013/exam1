package ru.ifmo.ctddev.koval.carwash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String ID_COLUMN = "_id";
    public static final String TIME_COLUMN = "time";
    public static final String BOX_COLUMN = "box";
    public static final String CAR_ID_COLUMN = "card_ids";
    public static final String MAKE_COLUMN = "_make";
    public static final String COLOR_COLUMN = "color";
    public static final String NUMBER_COLUMN = "number";
    public static final String PHONE_COLUMN = "phone";
    //
    public static final String ORDERS_TABLE_NAME = "orders";
    public static final String CARS_TABLE_NAME = "cards";
    public static final String DB_NAME = "car_wash";
    public static final int VERSION = 1;
    //
    public static final String ORDERS_CREATE_SCRIPT = "CREATE TABLE " + ORDERS_TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TIME_COLUMN + " TEXT, "
            + BOX_COLUMN + " INT, "
            + CAR_ID_COLUMN + " INT )";
    public static final String CARS_CREATE_SCRIPT = "CREATE TABLE " + CARS_TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MAKE_COLUMN + " TEXT, "
            + COLOR_COLUMN + " TEXT, "
            + NUMBER_COLUMN + " TEXT, "
            + PHONE_COLUMN + " TEXT )";

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ORDERS_CREATE_SCRIPT);
        sqLiteDatabase.execSQL(CARS_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
