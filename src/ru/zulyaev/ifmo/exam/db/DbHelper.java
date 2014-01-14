package ru.zulyaev.ifmo.exam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by seidhe on 1/14/14.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "car_wash";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        OrderTable.init(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        OrderTable.drop(db);
        OrderTable.init(db);
    }
}
