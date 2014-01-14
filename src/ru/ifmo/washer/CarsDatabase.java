package ru.ifmo.washer;

/**
 * Created by asus on 13.01.14.
 */

/**
 * Created by asus on 27.12.13.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;



/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 06.11.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created by asus on 27.12.13.
 */
public class CarsDatabase {

    private static final String TAG = "CarsDatabase";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    private String DATABASE_NAME;
    private String DATABASE_TABLE;

    private String DATABASE_CREATE;

    private static final int DATABASE_VERSION = 3;

    private final Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public CarsDatabase(Context ctx) {
        this.mCtx = ctx;
        DATABASE_NAME = "Cars";
        DATABASE_TABLE = "CarsDatabase";

        DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE + " (";
        for (int i = 0; i < Car.tags.length; i++){
            DATABASE_CREATE += Car.tags[i];
            if (i == 0) DATABASE_CREATE += " INTEGER PRIMARY KEY AUTOINCREMENT";
            if (i < Car.tags.length - 1) DATABASE_CREATE += ", ";
        }
        DATABASE_CREATE += ");";

    }

    public CarsDatabase open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long addItem(Car item) {
        ContentValues initialValues = new ContentValues();
        for (int i = 1; i < Car.tags.length; i++){
            initialValues.put(Car.tags[i], item.param[i]);
        }

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }


    public boolean deleteItem(Car item) {
        return mDb.delete(DATABASE_TABLE, Car.tags[0] + "=" + item.param[Car.ID], null) > 0;
    }


    public ArrayList<Car> getAllItems() {
        Cursor cursor = mDb.query(DATABASE_TABLE, null, null, null, null, null, Car.tags[Car.TIME]);
        ArrayList<Car> items = new ArrayList<Car>();
        Car curItem = new Car();
        while (cursor.moveToNext()){
            curItem.clear();
            for (int i = 0; i < Car.tags.length; i++) curItem.param[i] = cursor.getString(cursor.getColumnIndex(Car.tags[i]));
            items.add(curItem.makeCopy());
        }
        cursor.close();
        return items;
    }

    public Car getItem(int id) throws SQLException {

        Cursor cursor =
                mDb.query(true, DATABASE_TABLE, null, Car.tags[0] + "=" + id, null,
                        null, null, null, null);

        if (cursor == null) {
            Log.e(TAG, "Error getting item");
            return null;
        }

        cursor.moveToFirst();
        Car item = new Car();
        for (int i = 0; i < Car.tags.length; i++) item.param[i] = cursor.getString(cursor.getColumnIndex(Car.tags[i]));
        cursor.close();

        return item;
    }

    public boolean updateItem(Car item) {
        ContentValues args = new ContentValues();
        for (int i = 0; i < Car.tags.length; i++){
            args.put(Car.tags[i], item.param[i]);
        }

        return mDb.update(DATABASE_TABLE, args, Car.tags[0] + "=" + item.param[0], null) > 0;
    }


}

