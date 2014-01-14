package com.example.Template;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Александр
 * Date: 14.01.14
 * Time: 16:52
 */
public class CarWashDataBase {

    private SQLiteDatabase myDataBase;
    private DataBaseHelper dataBaseHelper;
    private Context context;

    public CarWashDataBase(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
        myDataBase = dataBaseHelper.getWritableDatabase();
        dataBaseHelper.close();
        myDataBase.close();
    }

    public void addOrder(Order order) {
        dataBaseHelper = new DataBaseHelper(context);
        myDataBase = dataBaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("time", order.getTime());
        contentValues.put("modelcolor", order.getModelColor());
        contentValues.put("number_window", order.getNumberWindow());
        contentValues.put("number_car", order.getNumberCar());
        contentValues.put("number_phone", order.getPhone());

        myDataBase.insert("carwashtable", null, contentValues);


        dataBaseHelper.close();
        myDataBase.close();
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> items = new ArrayList<Order>();

        dataBaseHelper = new DataBaseHelper(context);
        myDataBase = dataBaseHelper.getWritableDatabase();

        Order order;

        Cursor cursor = myDataBase.query("carwashtable", new String[] {
                "time", "modelcolor", "number_window", "number_car", "number_phone"},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {

            order = new Order(cursor.getString(cursor.getColumnIndex("time")), cursor.getString(cursor.getColumnIndex("modelcolor")), cursor.getString(cursor.getColumnIndex("number_window")), cursor.getString(cursor.getColumnIndex("number_car")), cursor.getString(cursor.getColumnIndex("number_phone")));
            items.add(order);
        }

        cursor.close();
        dataBaseHelper.close();
        myDataBase.close();

        return items;
    }

    public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

        private final String CREATE_TABLE = "CREATE TABLE "
                + "carwashtable" + " (" + DataBaseHelper._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "time" + " TEXT,"
                + "modelcolor" + " TEXT,"
                + "number_window" + " TEXT,"
                + "number_car" + " TEXT,"
                + "number_phone" + " TEXT);";

        //private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "
        //        + TABLE_NAME;

        public DataBaseHelper(Context context) {
            super(context, "carwash_db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        }
    }
}
